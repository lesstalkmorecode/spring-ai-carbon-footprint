package springai.carbonfootprint.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("GeoCoding API request")
public record GeocodingRequest(
        @JsonProperty(required = true) @JsonPropertyDescription("Latitude coordinate.") double lat,
        @JsonProperty(required = true) @JsonPropertyDescription("Longitude coordinate.") double lon) {
}

