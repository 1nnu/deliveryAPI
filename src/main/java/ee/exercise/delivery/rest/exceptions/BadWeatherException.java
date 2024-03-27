package ee.exercise.delivery.rest.exceptions;

public class BadWeatherException extends RuntimeException {
  public BadWeatherException(String message) {
    super(message);
  }
}
