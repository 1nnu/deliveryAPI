package ee.exercise.delivery.rest;

import ee.exercise.delivery.weather.WeatherData;
import ee.exercise.delivery.weather.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DeliveryService {

    @Autowired
    private WeatherRepository weatherRepository;

    public String calculateRegionalBaseFee(String city, String vehicle){
        Map<String, Map<String, Float>> baseFees = new HashMap<>();
        baseFees.put("tallinn", Map.of("car", 4.0F, "scooter", 4.0F, "bike", 4.0F));
        baseFees.put("tartu", Map.of("car", 3.5F, "scooter", 3.0F, "bike", 2.5F));
        baseFees.put("parnu", Map.of("car", 3.0F, "scooter", 2.5F, "bike", 2.0F));

        Map<String, Float> cityFees = baseFees.get(city);
        if (cityFees != null) {
            Float baseFee = cityFees.get(vehicle);
            if (baseFee != null) {
                return baseFee.toString();
            }
        }
        return "No such city available";
    }

    public WeatherData getLatestWeatherData(String city){
        switch (city){
            case "tallinn":
                city = "Tallinn-Harku";
                break;
            case "parnu":
                city = "Pärnu";
                break;
            case "tartu":
                city = "Tartu-Tõravere";
                break;
        }
        return weatherRepository.findLastCityByName(city);
    }

    public float calculateAirTemperatureFee(WeatherData weatherData){
        float airTemp = weatherData.getAirTemperature();
        if (airTemp < -10){
            return 1.0F;
        } else if(airTemp < 0){
            return 0.5F;
        } else {
            return 0.0F;
        }
    }
    public String calculateWeatherPhenomenonFee(WeatherData weatherData){
        String phenomenon = weatherData.getWeatherPhenomenon().toLowerCase();
        if (phenomenon.contains("glaze") || phenomenon.contains("hail") || phenomenon.contains("thunder")){
            return "Usage of selected vehicle type is forbidden";
        }
        else if(phenomenon.contains("snow") || phenomenon.contains("sleet")){
            return String.valueOf(1.0F);
        } else if(phenomenon.contains("rain") || phenomenon.contains("shower") || phenomenon.contains("drizzle")){
            return String.valueOf(0.5F);
        } else {
            return String.valueOf(0.0F);
        }
    }
    public String calculateWindSpeedFee(WeatherData weatherData){
        float windSpeed = weatherData.getWindSpeed();
        if (windSpeed > 20){
            return "Usage of selected vehicle type is forbidden";
        } else if (windSpeed > 10){
            return String.valueOf(0.5F);
        } else {
            return String.valueOf(0F);
        }
    }

}
