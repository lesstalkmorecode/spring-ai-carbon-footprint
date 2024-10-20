package springai.carbonfootprint.service;

import springai.carbonfootprint.model.CarbonFootprintRequest;
import springai.carbonfootprint.model.CarbonFootprintResponse;

public interface OpenAICarbonFootprintService {
    CarbonFootprintResponse calculateCarbonFootprint(CarbonFootprintRequest request);
}

