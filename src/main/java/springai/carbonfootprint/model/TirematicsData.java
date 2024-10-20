package springai.carbonfootprint.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Tirematics data including position, pressure, temperature, and wear.")
public class TirematicsData {
    @Schema(description = "Position of the tire, e.g., FL (Front-Left), FR (Front-Right), RL (Rear-Left), RR (Rear-Right)", example = "FL")
    private String position;
    @Schema(description = "Tire pressure in PSI (pounds per square inch)", example = "32.5")
    private double pressure;
    @Schema(description = "Tire temperature in degrees Celsius", example = "45.3")
    private double temperature;
    @Schema(description = "Tire wear percentage, indicating how much of the tire tread has worn down", example = "12.7")
    private double wear;
    @Schema(description = "Latitude of the measured location", example = "52.379189")
    private double latitude;
    @Schema(description = "Longitude of the measured location", example = "4.899431")
    private double longitude;
    @Schema(description = "Timestamp indicating when the tire data was measured, in ISO 8601 format", example = "2024-10-18T15:30:00Z")
    private Instant measurementTimestamp;

}
