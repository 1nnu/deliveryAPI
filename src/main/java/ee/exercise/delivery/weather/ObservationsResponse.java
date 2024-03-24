package ee.exercise.delivery.weather;

import ee.exercise.delivery.weather.WeatherStationResponse;
import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@XmlRootElement(name = "observations")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ObservationsResponse {
    @XmlAttribute
    private Long timestamp;
    @XmlElement(name = "station")
    private List<WeatherStationResponse> weatherStationResponseList;

}
