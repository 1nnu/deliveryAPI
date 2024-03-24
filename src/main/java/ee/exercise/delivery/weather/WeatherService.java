package ee.exercise.delivery.weather;

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
