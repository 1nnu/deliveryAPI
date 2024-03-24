package ee.exercise.deliveryAPI.weatherScraper;


import ee.exercise.deliveryAPI.data.WeatherData;
import ee.exercise.deliveryAPI.data.WeatherRepository;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;


@Service
public class ScraperService {

    @Autowired
    WeatherRepository weatherRepository;

    @Bean
    public WeatherStationWrapper getWeather(){
        final String url = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String result = response.getBody();

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(WeatherStationWrapper.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(result);
            return (WeatherStationWrapper) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void insertWeather(WeatherData weatherData){
        weatherRepository.save(weatherData);
    }
}
