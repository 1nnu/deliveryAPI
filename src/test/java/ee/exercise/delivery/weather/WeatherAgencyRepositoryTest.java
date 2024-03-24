package ee.exercise.delivery.weather;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.exercise.delivery.weather.exceptions.NoResponseBodyFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class WeatherAgencyRepositoryTest {

  private static final String WEATHER_AGENCY_URL =
      "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";
  private final RestTemplate restTemplate = mock(RestTemplate.class);
  private final WeatherAgencyRepository weatherAgencyRepository =
      new WeatherAgencyRepository(restTemplate);

  @Test
  void getWeatherObservations() {
    String mockResponse =
        """
    <?xml version="1.0" encoding="UTF-8"?>

    <observations timestamp="1711096886">
        <station>
            <name>Kuressaare linn</name>
            <wmocode></wmocode>
            <longitude>22.48944444411111</longitude>
            <latitude>58.26416666666667</latitude>
            <phenomenon></phenomenon>
            <visibility></visibility>
            <precipitations></precipitations>
            <airpressure></airpressure>
            <relativehumidity>98</relativehumidity>
            <airtemperature>2</airtemperature>
            <winddirection></winddirection>
            <windspeed></windspeed>
            <windspeedmax></windspeedmax>
            <waterlevel></waterlevel>
            <waterlevel_eh2000></waterlevel_eh2000>
            <watertemperature></watertemperature>
            <uvindex></uvindex>
            <sunshineduration></sunshineduration>
            <globalradiation></globalradiation>
        </station>
        <station>
            <name>Tallinn-Harku</name>
            <wmocode>26038</wmocode>
            <longitude>24.602891666624284</longitude>
            <latitude>59.398122222355134</latitude>
            <phenomenon>Light shower</phenomenon>
            <visibility>15.0</visibility>
            <precipitations>0</precipitations>
            <airpressure>1006.8</airpressure>
            <relativehumidity>97</relativehumidity>
            <airtemperature>2.3</airtemperature>
            <winddirection>202</winddirection>
            <windspeed>3.5</windspeed>
            <windspeedmax>5.6</windspeedmax>
            <waterlevel></waterlevel>
            <waterlevel_eh2000></waterlevel_eh2000>
            <watertemperature></watertemperature>
            <uvindex>1.0</uvindex>
            <sunshineduration>0</sunshineduration>
            <globalradiation>42</globalradiation>
        </station>
    </observations>
    """;

    ResponseEntity<String> mockResponseEntity = ResponseEntity.ok(mockResponse);
    when(restTemplate.getForEntity(
            "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php", String.class))
        .thenReturn(mockResponseEntity);

    ObservationsResponse weatherObservations = weatherAgencyRepository.getWeatherObservations();

    List<WeatherStationResponse> weatherStationResponseList =
        weatherObservations.getWeatherStationResponseList();
    assertFalse(weatherStationResponseList.isEmpty());
    assertEquals("Kuressaare linn", weatherStationResponseList.getFirst().getName());
    assertEquals("", weatherStationResponseList.getFirst().getWmocode());
    assertEquals("Tallinn-Harku", weatherStationResponseList.get(1).getName());
    assertEquals("26038", weatherStationResponseList.get(1).getWmocode());
  }

  @Test
  void getWeatherObservations_noBodyResponse() {
    ResponseEntity<String> noBodyResponse = ResponseEntity.ok().build();
    when(restTemplate.getForEntity(
            "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php", String.class))
        .thenReturn(noBodyResponse);

    NoResponseBodyFoundException runtimeException =
        assertThrows(
            NoResponseBodyFoundException.class, weatherAgencyRepository::getWeatherObservations);

    assertEquals("No response body found!", runtimeException.getMessage());
  }

  @Test
  void getWeatherObservations_invalidXML() {
    ResponseEntity<String> noBodyResponse =
        ResponseEntity.ok(
            """
    <?xml version="1.0" encoding="UTF-8"?>

    <observations timestamp="1711096886">
""");
    when(restTemplate.getForEntity(WEATHER_AGENCY_URL, String.class)).thenReturn(noBodyResponse);

    assertNull(weatherAgencyRepository.getWeatherObservations());
  }
}
