package ee.exercise.delivery.weather.exceptions;

public class NoResponseBodyFoundException extends RuntimeException {
  public NoResponseBodyFoundException(String message) {
    super(message);
  }
}
