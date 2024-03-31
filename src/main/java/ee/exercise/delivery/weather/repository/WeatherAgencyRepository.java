package ee.exercise.delivery.weather.repository;

import ee.exercise.delivery.weather.exceptions.NoResponseBodyFoundException;
import ee.exercise.delivery.weather.model.ObservationsResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.StringReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Repository
public class WeatherAgencyRepository {

  private final RestTemplate restTemplate;

  private final String url;

  public WeatherAgencyRepository(
      RestTemplate restTemplate, @Value("${weather.agency.url}") String url) {
    this.restTemplate = restTemplate;
    this.url = url;
  }

  /**
   * Get weather observation data from Estonian national weather agency.
   *
   * @return weather observations
   */
  public ObservationsResponse getWeatherObservations() {

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
