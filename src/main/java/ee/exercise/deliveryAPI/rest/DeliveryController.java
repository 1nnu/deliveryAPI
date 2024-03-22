package ee.exercise.deliveryAPI.rest;

import ee.exercise.deliveryAPI.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeliveryController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/data")
    public List<WeatherData> getWeatherDataList(){
        return weatherService.fetchWeatherDataList();
    }

    @PostMapping("/delivery/{city}/{vehicle}")
    public String calculateDeliveryFee(@PathVariable String city, @PathVariable String vehicle){
        float fee = 0.0F;
        vehicle = vehicle.toLowerCase();
        city = city.toLowerCase();
        String regionalBaseFee = deliveryService.calculateRegionalBaseFee(city, vehicle);
        if (regionalBaseFee.equals("No such city available")){
            return  regionalBaseFee;
        }
        fee += Float.parseFloat(regionalBaseFee);
        WeatherData weatherData = deliveryService.getLatestWeatherData(city);
        if (vehicle.equals("scooter") || vehicle.equals("bike")){
            fee += deliveryService.calculateAirTemperatureFee(weatherData);
            String weatherPhenomenonFee = deliveryService.calculateWeatherPhenomenonFee(weatherData);
            if (weatherPhenomenonFee.equals("Usage of selected vehicle type is forbidden")){
                return weatherPhenomenonFee;
            }
            fee += Float.parseFloat(weatherPhenomenonFee);
        }
        if (vehicle.equals("bike")){
            String windSpeedFee = deliveryService.calculateWindSpeedFee(weatherData);
            if (windSpeedFee.equals("Usage of selected vehicle type is forbidden")){
                return windSpeedFee;
            }
            fee += Float.parseFloat(windSpeedFee);
        }
        return String.valueOf(fee);
    }
}
