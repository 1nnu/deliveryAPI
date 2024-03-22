package ee.exercise.deliveryAPI;

import ee.exercise.deliveryAPI.weatherScraper.ScraperController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class DeliveryApiApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(DeliveryApiApplication.class, args);
		ScraperController scraperController = context.getBean(ScraperController.class);
		scraperController.insertWeatherData();
	}


}
