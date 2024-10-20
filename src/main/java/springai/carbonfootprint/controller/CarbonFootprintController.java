package springai.carbonfootprint.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springai.carbonfootprint.model.CarbonFootprintRequest;
import springai.carbonfootprint.model.CarbonFootprintResponse;
import springai.carbonfootprint.service.OpenAICarbonFootprintService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carbon-footprint")
public class CarbonFootprintController {

    private final OpenAICarbonFootprintService carbonFootprintService;

    @Operation(summary = "The OpenAI model calculates the carbon footprint of a vehicle trip by using Basic Carbon Footprint Formula")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Carbon footprint calculated  in kilograms of CO2 successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CarbonFootprintResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content)
    })
    @PostMapping("/calculate")
    public ResponseEntity<CarbonFootprintResponse> calculateCarbonFootprint(@RequestBody CarbonFootprintRequest request) {
        CarbonFootprintResponse response = carbonFootprintService.calculateCarbonFootprint(request);
        return ResponseEntity.ok(response);
    }
}
