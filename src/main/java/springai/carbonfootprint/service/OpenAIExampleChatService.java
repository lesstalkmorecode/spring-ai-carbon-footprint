package springai.carbonfootprint.service;

import org.springframework.web.multipart.MultipartFile;
import springai.carbonfootprint.model.ReverseGeocodingRequest;


public interface OpenAIExampleChatService {
    String reverseGeocodingByFunctionCallBack(ReverseGeocodingRequest request);

    String reverseGeocodingRag(ReverseGeocodingRequest request);

    byte[] getImage(String question);

    String getDescription(MultipartFile file);

    byte[] getSpeech(String question);
}
