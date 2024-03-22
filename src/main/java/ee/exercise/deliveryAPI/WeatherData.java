package ee.exercise.deliveryAPI;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData {

    public WeatherData(String stationName, int wmo, Float airTemperature, Float windSpeed, String weatherPhenomenon, Long timestamp){
        this.stationName = stationName;
        this.wmo = wmo;
        this.airTemperature = airTemperature;
        this.windSpeed = windSpeed;
        this.weatherPhenomenon = weatherPhenomenon;
        this.timestamp = timestamp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String stationName;
    private int wmo;
    private float airTemperature;
    private float windSpeed;
    private String weatherPhenomenon;
    private Long timestamp;
}
