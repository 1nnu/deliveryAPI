package ee.exercise.delivery.rest;

import ee.exercise.delivery.rest.exceptions.BadWeatherException;
import ee.exercise.delivery.rest.exceptions.InvalidInputException;
import ee.exercise.delivery.rest.exceptions.ResourceNotFoundException;
import ee.exercise.delivery.weather.WeatherData;
import ee.exercise.delivery.weather.WeatherService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryController {

  private final WeatherService weatherService;

  private final DeliveryService deliveryService;

  public DeliveryController(WeatherService weatherService, DeliveryService deliveryService) {
    this.weatherService = weatherService;
    this.deliveryService = deliveryService;
  }

  @GetMapping("/data")
  public List<WeatherData> getWeatherDataList() {
    return weatherService.fetchWeatherDataList();
  }

  @PostMapping("/delivery/{city}/{vehicle}")
  public String calculateDeliveryFee(@PathVariable String city, @PathVariable String vehicle) {
    try {
      return deliveryService.calculateFee(city, vehicle);
    } catch (ResourceNotFoundException | BadWeatherException | InvalidInputException e) {
      return e.getMessage();
    }
  }
}
