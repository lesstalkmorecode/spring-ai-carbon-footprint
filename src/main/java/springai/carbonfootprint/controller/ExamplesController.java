package springai.carbonfootprint.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springai.carbonfootprint.model.ReverseGeocodingRequest;
import springai.carbonfootprint.service.OpenAIExampleChatService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/example")
public class ExamplesController {

    private final OpenAIExampleChatService openAIExampleChatService;

    @Operation(summary = "Generate Speech from Question in English",
            description = "Takes a text-based question or input as a request, converts the text into speech using " +
                    "an AI-based text-to-speech engine, and returns the speech as an audio file in MP3 format. " +
                    "This can be used to generate spoken audio for questions or phrases. " +
                    "The resulting audio is returned as a binary MP3 file, which can be downloaded.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Audio file generated successfully",
                    content = @Content(mediaType = "audio/mpeg", schema = @Schema(type = "string", format = "binary"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/talk", produces = "audio/mpeg")
    public ResponseEntity<byte[]> talkTalkDownloadURL(@Schema(example = "Create a professional map for a quick trip from Amsterdam to Paris like Google map") @RequestBody String question) {
        byte[] audioContent = openAIExampleChatService.getSpeech(question);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/mpeg"));
        headers.setContentDispositionFormData("attachment", "speech.mp3");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(audioContent);
    }

    @Operation(summary = "Describe The Picture.",
            description = "Allows users to upload an image, and the system will analyze the content of the image " +
                    "and provide a detailed textual description of what is depicted in the picture. " +
                    "The analysis leverages AI to interpret the objects, scenes, and other relevant features in the image. " +
                    "The result is returned as a JSON-formatted string that summarizes the contents of the image.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Described successfully",
                    content = @Content(schema = @Schema(type = "string", format = "text"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/vision", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> upload(
            @Validated @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name
    ) throws IOException {
        return ResponseEntity.ok(openAIExampleChatService.getDescription(file));
    }

    @Operation(summary = "GetImage",
            description = "Generates an image based on a given request. " +
                    "For example, you can provide a query like 'Create a map for a quick trip from Amsterdam to Paris' " +
                    "and the system will return a generated image in PNG format. " +
                    "The image generation uses AI to interpret the query and return a visual representation, " +
                    "such as a map, diagram, or other visual content relevant to the input question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image generated successfully",
                    content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@Schema(example = "Create a professional map for a quick trip from Amsterdam to Paris like Google map") @RequestBody String question) {
        return openAIExampleChatService.getImage(question);
    }

    @Operation(summary = "Reverse Geocoding with FunctionCallback",
            description = "Uses AI only as an agent to show function callback." +
                    "Calls 'Reverse Geocoding API' https://api-ninjas.com/api/reversegeocoding" +
                    ", and converts latitude/longitude coordinates to cities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Description generated successfully",
                    content = @Content(schema = @Schema(type = "string", format = "text"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/reversegeocodingCallback")
    public ResponseEntity<String> reverseGeocodingByFunctionCallBack(@RequestBody ReverseGeocodingRequest request) {
        return ResponseEntity.ok(openAIExampleChatService.reverseGeocodingByFunctionCallBack(request));
    }


    @Operation(summary = "Reverse Geocoding with RAG (Retrieval-Augmented Generation)",
            description = "Performs reverse geocoding by leveraging a combination of vector search " +
                    "and AI prompt generation. It takes a request with latitude and longitude coordinates," +
                    " retrieves relevant documents based on similarity search, and generates a geocoding response " +
                    "using an AI model. The response includes information such as the corresponding city and " +
                    "country based on the input coordinates.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Geocoding response generated successfully",
                    content = @Content(schema = @Schema(type = "string", format = "text"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/reversegeocodingRag")
    public ResponseEntity<String> reverseGeocodingRag(@RequestBody ReverseGeocodingRequest request) {
        return ResponseEntity.ok(openAIExampleChatService.reverseGeocodingRag(request));
    }


}
