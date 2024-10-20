package springai.carbonfootprint.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReverseGeocodingRequest {
    @Schema(description = "Latitude of the vehicle location", example = "51.509865")
    private double latitude;

    @Schema(description = "Longitude of the vehicle location", example = "-0.118092")
    private double longitude;

    @Override
    public String toString() {
        return "latitude: " + latitude + ", longitude: " + longitude;
    }
}
