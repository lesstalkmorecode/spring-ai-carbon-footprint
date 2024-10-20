package springai.carbonfootprint.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to calculate carbon footprint with vehicle and tire data.")
public class CarbonFootprintRequest {

    @Schema(description = "Vehicle model name", example = "Volvo FMX")
    private String vehicleModel;

    @Schema(description = "Type of the vehicle", example = "Truck")
    private String vehicleType;

    @Schema(description = "Type of fuel used by the vehicle", example = "Diesel")
    private String fuelType;

    @Schema(description = "Load carried by the vehicle in kg", example = "3000")
    private double load;

    @Schema(description = "Number of braking events", example = "5")
    private int brakingEvents;

    @Schema(description = "Vehicle speed in km/h", example = "80")
    private double vehicleSpeed;

    @Schema(description = "List of tirematics data including position, pressure, temperature, and wear")
    private List<TirematicsData> tirematicsDataList;
}
