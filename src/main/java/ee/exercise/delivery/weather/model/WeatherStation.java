package ee.exercise.delivery.weather.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class WeatherStation {

  @Id private String stationName;
}
