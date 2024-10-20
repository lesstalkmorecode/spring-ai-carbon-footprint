package springai.carbonfootprint.service;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import springai.carbonfootprint.model.CarbonFootprintRequest;
import springai.carbonfootprint.model.CarbonFootprintResponse;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OpenAICarbonFootprintServiceImpl implements OpenAICarbonFootprintService {

    private final ChatModel chatModel;
    private final SimpleVectorStore vectorStore;

    @Value("classpath:templates/carbon-foot-print-calculation-template.st")
    private Resource carbonFootprintPrompt;

    @Override
    public CarbonFootprintResponse calculateCarbonFootprint(CarbonFootprintRequest request) {
        BeanOutputConverter<CarbonFootprintResponse> converter = new BeanOutputConverter<>(CarbonFootprintResponse.class);
        String format = converter.getFormat();

        // Convert the request object to a map
        Map<String, Object> modelMap = ObjectMapper.convertObjectToMap(request);
        modelMap.put("format", format);

        // Create the prompt using the model map
        PromptTemplate promptTemplate = new PromptTemplate(carbonFootprintPrompt);
        Prompt prompt = promptTemplate.create(modelMap);

        // Call the chat model and get the response
        ChatResponse response = chatModel.call(prompt);

        // Convert and return the response
        return converter.convert(response.getResult().getOutput().getContent());
    }
}
