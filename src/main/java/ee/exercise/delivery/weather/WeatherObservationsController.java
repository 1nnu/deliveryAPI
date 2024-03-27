package ee.exercise.delivery.weather;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class WeatherObservationsController {

  private final WeatherService weatherService;

  public WeatherObservationsController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  /** Inserts weather station locations for the application to look for */
  @Scheduled(initialDelay = 0)
  public void insertRequiredWeatherLocations() {
    weatherService.insertWeatherLocations();
  }

  /** Gets weather data from the weather agency on the 15th minute of every hour */
  @Scheduled(cron = "0 15 * * * *")
  public void insertWeatherData() {
    weatherService.fetchWeatherData();
  }

  /** Get weather data provided by the weather agency on application startup */
  @Scheduled(initialDelay = 1000)
  public void initialWeatherData() {
    weatherService.fetchWeatherData();
  }
}
