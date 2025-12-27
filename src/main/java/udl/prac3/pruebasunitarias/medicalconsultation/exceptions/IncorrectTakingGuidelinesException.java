package udl.prac3.pruebasunitarias.medicalconsultation.exceptions;

/**
 * Exception thrown when taking guidelines are incorrectly formatted or incomplete.
 * This includes incorrect posology data or missing required information.
 */
public class IncorrectTakingGuidelinesException extends Exception {
    public IncorrectTakingGuidelinesException() {
        super("Taking guidelines are incorrect or incomplete");
    }

    public IncorrectTakingGuidelinesException(String message) {
        super(message);
    }
}
