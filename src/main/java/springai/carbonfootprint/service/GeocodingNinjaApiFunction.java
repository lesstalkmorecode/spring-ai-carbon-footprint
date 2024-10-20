package springai.carbonfootprint.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import springai.carbonfootprint.model.GeocodingRequest;
import springai.carbonfootprint.model.GeocodingResponse;

import java.util.List;
import java.util.function.Function;

public class GeocodingNinjaApiFunction implements Function<GeocodingRequest, List<GeocodingResponse>> {

    public static final String GEOCODING_NINJA_URL = "https://api.api-ninjas.com/v1/reversegeocoding";

    private final String apiNinjasKey;

    public GeocodingNinjaApiFunction(String apiNinjasKey) {
        this.apiNinjasKey = apiNinjasKey;
    }

    @Override
    public List<GeocodingResponse> apply(GeocodingRequest geocodingRequest) {
        RestClient restClient = RestClient.builder()
                .baseUrl(GEOCODING_NINJA_URL)
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.set("X-Api-Key", apiNinjasKey);
                    httpHeaders.set("Accept", "application/json");
                    httpHeaders.set("Content-Type", "application/json");
                }).build();

        return restClient.get().uri(uriBuilder -> {
            uriBuilder.queryParam("lat", geocodingRequest.lat());
            uriBuilder.queryParam("lon", geocodingRequest.lon());

            return uriBuilder.build();
        }).retrieve().body(new ParameterizedTypeReference<List<GeocodingResponse>>() {
        });
    }
}
