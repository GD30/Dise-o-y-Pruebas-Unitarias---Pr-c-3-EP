package udl.prac3.pruebasunitarias.data.exceptions;

/**
 * Exception thrown when an ePrescripCode is invalid.
 * This includes null values or incorrectly formatted codes.
 */
public class ePrescripCodeException extends Exception {
    public ePrescripCodeException(String message) {
        super(message);
    }
}