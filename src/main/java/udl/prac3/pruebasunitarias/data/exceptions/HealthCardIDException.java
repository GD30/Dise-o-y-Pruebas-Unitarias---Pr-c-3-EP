package udl.prac3.pruebasunitarias.data.exceptions;

/**
 * Exception thrown when a HealthCardID is invalid.
 * This includes null values or incorrectly formatted codes.
 */
public class HealthCardIDException extends Exception {
    public HealthCardIDException(String message) {
        super(message);
    }
}