package ee.exercise.deliveryAPI.weatherScraper;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@XmlRootElement(name = "observations")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class WeatherStationWrapper {
    @XmlAttribute
    private Long timestamp;
    @XmlElement(name = "station")
    private List<WeatherStation> weatherStationList;

}
