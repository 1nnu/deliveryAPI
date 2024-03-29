package ee.exercise.delivery.weather.repository;

import java.util.List;

import ee.exercise.delivery.weather.model.WeatherData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends CrudRepository<WeatherData, Long> {

  @Query(
      "SELECT wd FROM WeatherData wd WHERE wd.stationName = :city AND wd.timestamp = (SELECT MAX(wd2.timestamp) FROM WeatherData wd2 WHERE wd2.stationName = :city)")
  WeatherData findLastCityByName(@Param("city") String city);

  @Query("SELECT DISTINCT stationName FROM WeatherData")
  List<String> findAllDistinctNames();
}
