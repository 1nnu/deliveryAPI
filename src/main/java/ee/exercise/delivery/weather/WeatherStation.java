package ee.exercise.delivery.weather;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class WeatherStation {

  @Id private Long id;
  private String stationName;
}
