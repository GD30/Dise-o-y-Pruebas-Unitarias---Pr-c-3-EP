package udl.prac3.pruebasunitarias.services.exceptions;

/**
 * Exception thrown when no active prescription exists for the patient for the specified illness.
 * The patient must have an active prescription to perform certain operations.
 */
public class AnyCurrentPrescriptionException extends Exception {
    public AnyCurrentPrescriptionException() {
        super("No active prescription found for the patient");
    }

    public AnyCurrentPrescriptionException(String message) {
        super(message);
    }
}
