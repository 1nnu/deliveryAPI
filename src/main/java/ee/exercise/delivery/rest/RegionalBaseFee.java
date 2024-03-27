package ee.exercise.delivery.rest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RegionalBaseFee {

  @Id private String city;
  private Float carFee;
  private Float scooterFee;
  private Float bikeFee;
}
