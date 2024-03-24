package ee.exercise.delivery.weather;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class WeatherObservationsController {
  private final WeatherAgencyRepository weatherAgencyRepository;
  private final WeatherRepository weatherRepository;
  private final JdbcTemplate jdbcTemplate;

  public WeatherObservationsController(
      WeatherAgencyRepository weatherAgencyRepository,
      WeatherRepository weatherRepository,
      JdbcTemplate jdbcTemplate) {
    this.weatherAgencyRepository = weatherAgencyRepository;
    this.weatherRepository = weatherRepository;
    this.jdbcTemplate = jdbcTemplate;
  }

  @Scheduled(cron = "0 15 * * * *")
  public void insertWeatherData() {
    fetchWeatherData();
  }

  @Scheduled(initialDelay = 1000)
  public void initialWeatherData() {
    fetchWeatherData();
  }

  private void fetchWeatherData() {
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
}
