package springai.carbonfootprint.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record CarbonFootprintResponse(
        @JsonPropertyDescription("Carbon footprint in kilograms of CO2") double carbonFootprint,
        @JsonPropertyDescription("Used the input parameters") String usedInputParameters
) {
}
