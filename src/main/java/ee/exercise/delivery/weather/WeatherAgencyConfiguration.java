package ee.exercise.delivery.weather;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WeatherAgencyConfiguration {

  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
