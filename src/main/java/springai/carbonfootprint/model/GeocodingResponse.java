package springai.carbonfootprint.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("GeoCoding API request")
public record GeocodingResponse(
        @JsonPropertyDescription("City name") String name,
        @JsonPropertyDescription("Country name") String country) {
}
