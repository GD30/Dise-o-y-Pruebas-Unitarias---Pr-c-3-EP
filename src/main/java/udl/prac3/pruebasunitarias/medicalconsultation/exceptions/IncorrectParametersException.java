package udl.prac3.pruebasunitarias.medicalconsultation.exceptions;

/**
 * Exception thrown when incorrect parameters are provided to a constructor or method.
 * Used for validation of input parameters in medical consultation classes.
 */
public class IncorrectParametersException extends Exception {
    public IncorrectParametersException() {
        super("Incorrect parameters provided");
    }

    public IncorrectParametersException(String message) {
        super(message);
    }
}