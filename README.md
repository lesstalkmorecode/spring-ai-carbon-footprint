# AI Based Basic Carbon Footprint Calculator

## Overview

AI Based Basic Carbon Footprint Calculator is a Java-based application that estimates the carbon footprint (CO₂
emissions)
of a vehicle based on its fuel consumption, distance traveled, and specific parameters such as vehicle type, fuel type,
and emission factors.

This project demonstrates how to calculate carbon emissions for vehicles using a straightforward formula that accounts
for fuel efficiency, distance traveled, and standard emission factors for various fuel types

## Basic Carbon Footprint Formula

The formula used to calculate the carbon footprint is:

```text
Carbon Footprint (kg CO₂) = Fuel Consumption (liters) × Emission Factor (kg CO₂/liter)
```

#### Where: ####

- **Fuel Consumption (liters)** is the total fuel used by the vehicle for a given distance.
- **Emission Factor (kg CO₂/liter)** is the standard CO₂ emissions per liter of fuel (e.g., 2.68 kg CO₂/liter for
  diesel).

#### Detailed Calculation Steps: #### 

1. **Fuel Consumption:**

   ```Fuel Consumption (liters) = Distance (km) × Fuel Efficiency (liters/km)```

2. **Carbon Emissions:**

   ```Carbon Emissions (kg CO₂) = Fuel Consumption (liters) × Emission Factor (kg CO₂/liter)```

#### Features: ####

- **Distance Calculation:** Calculate the fuel consumption based on the distance traveled by the vehicle and its fuel
  efficiency.
- **Fuel Type Support:** Supports different fuel types (e.g., Diesel, Gasoline) with corresponding emission factors.
- **Braking Events:** Optional adjustment for additional fuel consumption due to braking events.
- **Tire Condition Adjustment:** Adjust fuel consumption based on tire wear and pressure.

## Setup and Installation

### Prerequisites

- Java 8 or later
- Gradle
- OpenAI API Key {OPENAI_API_KEY}
- API Ninjas Key {API_NINJAS_KEY} (for external API access - only for example calls)

### Installation

1. Clone the repository:

```bash
git clone <repository-url>
cd spring-ai-carbon-footprint
```

2. Build and run the application:

```bash
./gradlew build
./gradlew bootRun
```

## API Endpoints

### Calculate Carbon Footprint

- Endpoint: /api/carbon-footprint/calculate
- Method: POST
- Request Body: CarbonFootprintRequest
- Swagger endpoint: ```http://<host>:<port>/swagger-ui/index.html```

```json
{
  "vehicleModel": "Volvo FMX",
  "vehicleType": "Truck",
  "fuelType": "Diesel",
  "load": 3000,
  "brakingEvents": 5,
  "vehicleSpeed": 50,
  "tirematicsDataList": [
    {
      "position": "FL",
      "pressure": 32.5,
      "temperature": 45.3,
      "wear": 12.7,
      "latitude": 52.379189,
      "longitude": 4.899431,
      "measurementTimestamp": "2024-10-19T15:00:00Z"
    },
    {
      "position": "FL",
      "pressure": 32.5,
      "temperature": 45.3,
      "wear": 12.7,
      "latitude": 52.090737,
      "longitude": 5.121420,
      "measurementTimestamp": "2024-10-19T15:40:00Z"
    },
    {
      "position": "FL",
      "pressure": 32.5,
      "temperature": 45.3,
      "wear": 12.7,
      "latitude": 52.070498,
      "longitude": 4.300700,
      "measurementTimestamp": "2024-10-19T16:25:00Z"
    }
  ]
}
```

- Response
    - 200: Successfully calculated carbon footprint.
        - Response Body: CarbonFootprintResponse
    - 400: Invalid input data.
    - 500: Internal server error.

## Acknowledgments

- Integration of OpenAI ChatGPT APIs and SpringAI within a Spring Boot application.
- This project includes several examples that illustrate the usage of OpenAI and Spring for building AI-powered
  applications:
    1. Generate Speech from Text: Demonstrates how a text input is converted to speech using AI and returned as an audio
       file (MP3 format).
    2. Image Description from Upload: An example of uploading an image and using AI to generate a textual description of
       the image content.
    3. AI-Generated Image: Shows how a query can be used to generate a relevant image (e.g., a map) using AI, and
       returns the image in PNG format.
    4. Reverse Geocoding API: Converts latitude/longitude coordinates into a city and country using AI with an external
       geocoding service.
    5. Reverse Geocoding with Retrieval-Augmented Generation (RAG): Demonstrates how AI and vector search are combined
       to retrieve relevant documents and generate geocoding responses based on coordinates.
