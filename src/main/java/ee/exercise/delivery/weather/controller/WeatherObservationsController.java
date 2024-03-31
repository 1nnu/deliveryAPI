package ee.exercise.delivery.weather.controller;

import ee.exercise.delivery.weather.service.WeatherService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class WeatherObservationsController {

  private final WeatherService weatherService;

  public WeatherObservationsController(WeatherService weatherService) {
    this.weatherService = weatherService;
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
