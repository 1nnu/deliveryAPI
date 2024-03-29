package ee.exercise.delivery.rest.controller;

import ee.exercise.delivery.rest.service.DeliveryService;
import ee.exercise.delivery.rest.exceptions.BadWeatherException;
import ee.exercise.delivery.rest.exceptions.InvalidInputException;
import ee.exercise.delivery.rest.exceptions.ResourceNotFoundException;
import ee.exercise.delivery.weather.model.WeatherData;
import ee.exercise.delivery.weather.service.WeatherService;
import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

  /**
   * Get endpoint for displaying current state of weatherdata in database
   *
   * @return List of weatherdata entries
   */
  @GetMapping("/data")
  public List<WeatherData> getWeatherDataList() {
    return weatherService.fetchWeatherDataList();
  }

  /**
   * API endpoint for users to input data Calculates fee based on city and vehicle type
   *
   * @return String representation of the total fee
   */
  @PostMapping("/delivery/{city}/{vehicle}")
  public ResponseEntity<String> calculateDeliveryFee(@PathVariable String city, @PathVariable String vehicle) {
    try {
      return new ResponseEntity<>(deliveryService.calculateFee(city,vehicle),HttpStatusCode.valueOf(200));
    } catch (ResourceNotFoundException | BadWeatherException | InvalidInputException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
    }
  }
}
