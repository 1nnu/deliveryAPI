package ee.exercise.delivery.weather;

import ee.exercise.delivery.weather.exceptions.NoResponseBodyFoundException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Repository
public class WeatherAgencyRepository {

  private final RestTemplate restTemplate;

  public WeatherAgencyRepository(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Get weather observation data from Estonian national weather agency.
   *
   * @return weather observations
   */
  public ObservationsResponse getWeatherObservations() {
    final String url = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    log.info("Fetching weather data from {}", url);
    ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

    String weatherObservations = response.getBody();

    if (weatherObservations == null) {
      throw new NoResponseBodyFoundException("No response body found!");
    }
    StringReader reader = new StringReader(weatherObservations);

    try {
      JAXBContext jaxbContext = JAXBContext.newInstance(ObservationsResponse.class);
      Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
      return (ObservationsResponse) unmarshaller.unmarshal(reader);
    } catch (JAXBException e) {
      log.error("XML parsing failed!");
      return null;
    }
  }
}
