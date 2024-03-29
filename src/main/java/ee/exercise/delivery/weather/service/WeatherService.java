package ee.exercise.delivery.weather.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ee.exercise.delivery.weather.model.ObservationsResponse;
import ee.exercise.delivery.weather.repository.WeatherAgencyRepository;
import ee.exercise.delivery.weather.repository.WeatherRepository;
import ee.exercise.delivery.weather.model.WeatherData;
import ee.exercise.delivery.weather.model.WeatherStationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WeatherService {

  private final WeatherRepository weatherRepository;

  private final WeatherAgencyRepository weatherAgencyRepository;

  private final JdbcTemplate jdbcTemplate;

  public WeatherService(
      WeatherRepository weatherRepository,
      WeatherAgencyRepository weatherAgencyRepository,
      JdbcTemplate jdbcTemplate) {
    this.weatherRepository = weatherRepository;
    this.weatherAgencyRepository = weatherAgencyRepository;
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * Fetches all data stored in the WeatherData db
   *
   * @return list of WeatherData entries
   */
  public List<WeatherData> fetchWeatherDataList() {
    return (List<WeatherData>) weatherRepository.findAll();
  }

  /**
   * Gets observation data from the weather agency. For each weatherStation the application is
   * using, make an entry from the observation data.
   */
  public void fetchWeatherData() {
    ObservationsResponse observationsResponse = weatherAgencyRepository.getWeatherObservations();
    Set<String> weatherStations = findStationNames();

    List<WeatherStationResponse> weatherDataList =
        observationsResponse.getWeatherStationResponseList();

    for (WeatherStationResponse station : weatherDataList) {
      if (weatherStations.contains(station.getName())) {
        WeatherData weatherData =
            new WeatherData(
                station.getName(),
                Integer.parseInt(station.getWmocode()),
                Float.parseFloat(station.getAirtemperature()),
                Float.parseFloat(station.getWindspeed()),
                station.getPhenomenon(),
                observationsResponse.getTimestamp());
        weatherRepository.save(weatherData);
      }
    }
  }

  private Set<String> findStationNames() {
    String sql = "SELECT ws.STATION_NAME FROM WEATHER_STATION ws";
    return new HashSet<>(jdbcTemplate.queryForList(sql, String.class));
  }

  // Done manually due to UTF-8 encoding not passing when using data.sql statements
  public void insertWeatherLocations() {
    jdbcTemplate.update(
        "INSERT INTO WEATHER_STATION (ID, STATION_NAME) VALUES (1, 'Tallinn-Harku'), (2, 'Tartu-Tõravere'), (3, 'Pärnu');");
    log.info("Inserted info into WEATHER_STATION database");
  }
}
