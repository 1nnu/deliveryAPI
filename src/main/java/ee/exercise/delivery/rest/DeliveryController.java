package ee.exercise.delivery.rest;

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
    float fee = 0.0F;
    vehicle = vehicle.toLowerCase();
    city = city.toLowerCase();
    String regionalBaseFee = deliveryService.calculateRegionalBaseFee(city, vehicle);
    if (regionalBaseFee.equals("No such city available")) {
      return regionalBaseFee;
    }
    fee += Float.parseFloat(regionalBaseFee);
    WeatherData weatherData = deliveryService.getLatestWeatherData(city);
    if (vehicle.equals("scooter") || vehicle.equals("bike")) {
      fee += deliveryService.calculateAirTemperatureFee(weatherData);
      String weatherPhenomenonFee = deliveryService.calculateWeatherPhenomenonFee(weatherData);
      if (weatherPhenomenonFee.equals("Usage of selected vehicle type is forbidden")) {
        return weatherPhenomenonFee;
      }
      fee += Float.parseFloat(weatherPhenomenonFee);
    }
    if (vehicle.equals("bike")) {
      String windSpeedFee = deliveryService.calculateWindSpeedFee(weatherData);
      if (windSpeedFee.equals("Usage of selected vehicle type is forbidden")) {
        return windSpeedFee;
      }
      fee += Float.parseFloat(windSpeedFee);
    }
    return String.valueOf(fee);
  }
}
