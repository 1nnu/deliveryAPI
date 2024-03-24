package ee.exercise.deliveryAPI.weatherScraperTest;


import ee.exercise.deliveryAPI.data.WeatherData;
import ee.exercise.deliveryAPI.data.WeatherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class WeatherScraperTests {

    @Autowired
    private WeatherRepository weatherRepository;

    @Test
    public void deliveryAPI_Save_ReturnSavedWeatherData(){

        WeatherData weatherData = WeatherData.builder()
                .id(1L)
                .stationName("Põlva")
                .wmo(123)
                .airTemperature(2.0F)
                .windSpeed(11.2F)
                .weatherPhenomenon("Rain")
                .timestamp(100000L)
                .build();

        WeatherData savedWeatherData = weatherRepository.save(weatherData);

        Assertions.assertNotEquals(null, savedWeatherData);
        Assertions.assertEquals(savedWeatherData, weatherRepository.findById(savedWeatherData.getId()));

    }
}
