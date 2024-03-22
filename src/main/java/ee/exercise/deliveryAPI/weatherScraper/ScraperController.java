package ee.exercise.deliveryAPI.weatherScraper;

import ee.exercise.deliveryAPI.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ScraperController {
    @Autowired
    private ScraperService service;

    @Scheduled(cron = "0 15 * * * *")
    public void insertWeatherData(){
        WeatherStationWrapper weatherStationWrapper = service.getWeather();
        List<WeatherStation> weatherDataList = weatherStationWrapper.getWeatherStationList();
        Set<String> nameSet = new HashSet<>();
        nameSet.add("Tallinn-Harku");
        nameSet.add("Tartu-Tõravere");
        nameSet.add("Pärnu");
        for (WeatherStation station : weatherDataList){
            if (nameSet.contains(station.getName())){
                WeatherData weatherData = new WeatherData(
                        station.getName(),
                        Integer.parseInt(station.getWmocode()),
                        Float.parseFloat(station.getAirtemperature()),
                        Float.parseFloat(station.getWindspeed()),
                        station.getPhenomenon(),
                        weatherStationWrapper.getTimestamp()
                );
                service.insertWeather(weatherData);
            }
        }
    }

}
