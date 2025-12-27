package udl.prac3.pruebasunitarias.medicalconsultation.exceptions;

/**
 * Exception thrown when the treatment ending date is invalid.
 * This includes dates in the past, too close to the current date, or null values.
 */
public class IncorrectEndingDateException extends Exception {
    public IncorrectEndingDateException() {
        super("Treatment ending date is incorrect");
    }

    public IncorrectEndingDateException(String message) {
        super(message);
    }
}