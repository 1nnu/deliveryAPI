package ee.exercise.deliveryAPI.rest;

import ee.exercise.deliveryAPI.data.WeatherData;
import ee.exercise.deliveryAPI.data.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    public WeatherData saveWeatherData(WeatherData weatherData) {
        return weatherRepository.save(weatherData);
    }

    public List<WeatherData> fetchWeatherDataList() {
        List<WeatherData> list = (List<WeatherData>) weatherRepository.findAll();
        for (WeatherData item : list){
            System.out.println(item.getStationName());
        }
        return list;
    }
}
