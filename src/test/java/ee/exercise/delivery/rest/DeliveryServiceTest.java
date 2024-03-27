package ee.exercise.delivery.rest;

import static org.junit.jupiter.api.Assertions.*;

import ee.exercise.delivery.rest.exceptions.InvalidInputException;
import ee.exercise.delivery.weather.WeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class DeliveryServiceTest {

  @Mock WeatherRepository weatherRepository;

  DeliveryService deliveryService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    deliveryService = new DeliveryService(weatherRepository, null);
  }

  @Test
  void calculateRegionalBaseFee_incorrectVehicle() {
    String city = "tallinn";
    String incorrectVehicle = "boat";
    assertThrows(
        InvalidInputException.class,
        () -> {
          deliveryService.calculateFee(city, incorrectVehicle);
          ;
        });
  }

  @Test
  void calculateRegionalBaseFee_incorrectCity() {
    String incorrectCity = "New York";
    String vehicle = "car";
    assertThrows(
        InvalidInputException.class,
        () -> {
          deliveryService.calculateFee(incorrectCity, vehicle);
        });
  }
}
