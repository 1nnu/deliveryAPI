package ee.exercise.delivery.weather;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class WeatherObservationsController {

  private final WeatherService weatherService;

  public WeatherObservationsController(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @Scheduled(initialDelay = 0)
  public void insertRequiredWeatherLocations() {
    weatherService.insertWeatherLocations();
  }

  @Scheduled(cron = "0 15 * * * *")
  public void insertWeatherData() {
    weatherService.fetchWeatherData();
  }

  @Scheduled(initialDelay = 1000)
  public void initialWeatherData() {
    weatherService.fetchWeatherData();
  }
}
