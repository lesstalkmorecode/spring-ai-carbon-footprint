package springai.carbonfootprint.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import springai.carbonfootprint.model.GeocodingResponse;
import springai.carbonfootprint.model.ReverseGeocodingRequest;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OpenAIExampleChatServiceImpl implements OpenAIExampleChatService {

    private final ChatModel chatModel;
    private final SimpleVectorStore vectorStore;

    private final ImageModel imageModel;
    private final OpenAiAudioSpeechModel speechModel;

    @Value("classpath:templates/geocoding-rag-prompt-template.st")
    private Resource geocodingPrompt;

    @Value("${springaiapp.apiNinjasKey}")
    private String apiNinjasKey;


    @Override
    public String reverseGeocodingByFunctionCallBack(ReverseGeocodingRequest request) {
        var promptOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(List.of(FunctionCallbackWrapper.builder(new GeocodingNinjaApiFunction(apiNinjasKey))
                        .withName("GetLocation")
                        .withDescription("Get the location for the given lat, long")
                        .withResponseConverter((response) -> {
                            String schema = ModelOptionsUtils.getJsonSchema(GeocodingResponse.class, false);
                            String json = ModelOptionsUtils.toJsonString(response);
                            return schema + "\n" + json;
                        })
                        .build()))
                .build();

        Message userMessage = new PromptTemplate(request.toString()).createMessage();
        Message systemMessage = new SystemPromptTemplate("You are an agent which returns back converted latitude/longitude coordinates as cities").createMessage();

        Prompt prompt = new Prompt(List.of(userMessage, systemMessage), promptOptions);

        var response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }

    @Override
    public String reverseGeocodingRag(ReverseGeocodingRequest request) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest
                .query(request.toString()).withTopK(5));
        List<String> contentList = documents.stream().map(Document::getContent).toList();

        PromptTemplate promptTemplate = new PromptTemplate(geocodingPrompt);
        Prompt prompt = promptTemplate.create(
                Map.of("latitude", request.getLatitude(),
                        "longitude", request.getLongitude(),
                        "documents", String.join("\n", contentList
                        )));

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().getContent();
    }

    @Override
    public byte[] getImage(String question) {
        var options = OpenAiImageOptions.builder()
                .withHeight(1024).withWidth(1024)
                .withResponseFormat("b64_json") // base 64 json
                .withModel("dall-e-3")
                .withQuality("hd") // default is standart and only dall-e3 support hd
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(question, options);

        var imageResponse = imageModel.call(imagePrompt);

        return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());
    }

    @Override
    public String getDescription(MultipartFile file) {
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel(OpenAiApi.ChatModel.GPT_4_O.getValue())
                .build();

        var userMessage = new UserMessage("Explain what do you see in this picture?",
                List.of(new Media(MimeTypeUtils.IMAGE_JPEG, file.getResource())));

        ChatResponse response = chatModel.call(new Prompt(List.of(userMessage), options));

        return response.getResult().getOutput().getContent();
    }

    @Override
    public byte[] getSpeech(String question) {
        OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .withSpeed(1.0f)
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withModel(OpenAiAudioApi.TtsModel.TTS_1.value)
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt(question, speechOptions);

        SpeechResponse response = speechModel.call(speechPrompt);

        return response.getResult().getOutput();
    }

}
