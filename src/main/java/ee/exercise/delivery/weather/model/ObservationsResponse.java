package ee.exercise.delivery.weather.model;

import jakarta.xml.bind.annotation.*;
import java.util.List;
import lombok.Data;

@XmlRootElement(name = "observations")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ObservationsResponse {
  @XmlAttribute private Long timestamp;

  @XmlElement(name = "station")
  private List<WeatherStationResponse> weatherStationResponseList;
}
