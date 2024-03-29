package ee.exercise.delivery.rest;

import static org.junit.jupiter.api.Assertions.*;

import ee.exercise.delivery.rest.exceptions.InvalidInputException;
import ee.exercise.delivery.rest.service.DeliveryService;
import ee.exercise.delivery.weather.repository.WeatherRepository;
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
  void calculateFee_incorrectVehicle() {
    String city = "tallinn";
    String incorrectVehicle = "boat";
    assertThrows(
        InvalidInputException.class,
        () -> {
          deliveryService.calculateFee(city, incorrectVehicle);
        });
  }

  @Test
  void calculateFee_incorrectCity() {
    String incorrectCity = "New York";
    String vehicle = "car";
    assertThrows(
        InvalidInputException.class,
        () -> {
          deliveryService.calculateFee(incorrectCity, vehicle);
        });
  }

  @Test
  void calculateFee_inputIsNull() {
    assertThrows(
        InvalidInputException.class,
        () -> {
          deliveryService.calculateFee(null, null);
          ;
        });
  }
}
