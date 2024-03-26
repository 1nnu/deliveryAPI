package ee.exercise.delivery.rest;

import ee.exercise.delivery.weather.WeatherRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.List;

@Configuration
public class DeliveryServiceConfiguration {

    WeatherRepository weatherRepository;

    public DeliveryServiceConfiguration(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Bean
    @DependsOn("weatherRepository")
    HashMap<String, String> endpointToCityHashMap() {
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
