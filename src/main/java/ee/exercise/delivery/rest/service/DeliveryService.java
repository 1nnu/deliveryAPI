package ee.exercise.delivery.rest.service;

import ee.exercise.delivery.rest.exceptions.BadWeatherException;
import ee.exercise.delivery.rest.exceptions.InvalidInputException;
import ee.exercise.delivery.rest.model.RegionalBaseFee;
import ee.exercise.delivery.rest.model.Vehicle;
import ee.exercise.delivery.weather.model.WeatherData;
import ee.exercise.delivery.weather.repository.WeatherRepository;
import java.util.HashMap;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveryService {

  private final WeatherRepository weatherRepository;

  private final JdbcTemplate jdbcTemplate;

  public DeliveryService(WeatherRepository weatherRepository, JdbcTemplate jdbcTemplate) {
    this.weatherRepository = weatherRepository;
    this.jdbcTemplate = jdbcTemplate;
  }

  private Float calculateRegionalBaseFee(String city, Vehicle vehicle) {
    try {
      RegionalBaseFee regionalBaseFee = getBaseFee(city);
      return switch (vehicle) {
        case CAR -> regionalBaseFee.getCarFee();
        case SCOOTER -> regionalBaseFee.getScooterFee();
        case BIKE -> regionalBaseFee.getBikeFee();
      };
    } catch (Exception e) {
      throw new InvalidInputException("Provided input is invalid");
    }
  }

  private RegionalBaseFee getBaseFee(String city) {
    String sql =
        String.format(
            "SELECT BIKE_FEE, CAR_FEE, SCOOTER_FEE, CITY FROM REGIONAL_BASE_FEE WHERE city = '%s'",
            city);
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RegionalBaseFee.class)).getFirst();
  }

  private float calculateAirTemperatureFee(float airTemperature) {
    if (airTemperature < -10) {
      return 1.0F;
    } else if (airTemperature < 0) {
      return 0.5F;
    } else {
      return 0.0F;
    }
  }

  private Float calculateWeatherPhenomenonFee(String phenomenon) {
    if (phenomenon.contains("glaze")
        || phenomenon.contains("hail")
        || phenomenon.contains("thunder")) {
      throw new BadWeatherException("Usage of selected vehicle type is forbidden");
    } else if (phenomenon.contains("snow") || phenomenon.contains("sleet")) {
      return 1.0F;
    } else if (phenomenon.contains("rain")
        || phenomenon.contains("shower")
        || phenomenon.contains("drizzle")) {
      return 0.5F;
    } else {
      return 0.0F;
    }
  }

  private float calculateWindSpeedFee(float windSpeed) {
    if (windSpeed > 20) {
      throw new BadWeatherException("Usage of selected vehicle type is forbidden");
    } else if (windSpeed > 10) {
      return 0.5F;
    } else {
      return 0F;
    }
  }

  /**
   * Main service method for calculating the delivery fee. Call all other methods to in order to
   * validate input and return fee.
   *
   * @param city String provided by call from controller
   * @param vehicle String provided by call from controller
   * @return String representation of the total fee
   */
  public String calculateFee(String city, Vehicle vehicle) {
    Float fee = 0.0F;
    fee += calculateRegionalBaseFee(city, vehicle);
    HashMap<String, String> endpointToCityHashMap = getEndpointToCityHashMap();
    WeatherData weatherData = weatherRepository.findLastCityByName(endpointToCityHashMap.get(city));
    if (vehicle == Vehicle.SCOOTER || vehicle == Vehicle.BIKE) {
      fee += calculateAirTemperatureFee(weatherData.getAirTemperature());
      fee += calculateWeatherPhenomenonFee(weatherData.getWeatherPhenomenon());
    }
    if (vehicle == Vehicle.BIKE) {
      fee += calculateWindSpeedFee(weatherData.getWindSpeed());
    }
    return String.valueOf(fee);
  }

  private HashMap<String, String> getEndpointToCityHashMap() {
    List<String> namesList = weatherRepository.findAllDistinctNames();
    HashMap<String, String> endpointToCityHashMap = new HashMap<>();
    namesList.forEach(
        (name ->
            endpointToCityHashMap.put(
                name.toLowerCase()
                    .replace('ä', 'a')
                    .replace('ö', 'o')
                    .replace('õ', 'o')
                    .replace('ü', 'u')
                    .split("-")[0],
                name)));
    return endpointToCityHashMap;
  }
}
