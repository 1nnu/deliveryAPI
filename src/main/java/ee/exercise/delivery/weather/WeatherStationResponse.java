package ee.exercise.delivery.weather;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement(name = "station")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class WeatherStationResponse {
  private String name;
  private String wmocode;
  private String longitude;
  private String latitude;
  private String phenomenon;
  private String visibility;
  private String precipitations;
  private String airpressure;
  private String relativehumidity;
  private String airtemperature;
  private String winddirection;
  private String windspeed;
  private String windspeedmax;
  private String waterlevel;
  private String waterlevel_eh2000;
  private String watertemperature;
  private String uvindex;
  private String sunshineduration;
  private String globalradiation;
}
