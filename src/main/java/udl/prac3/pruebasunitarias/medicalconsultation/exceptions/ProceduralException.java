package udl.prac3.pruebasunitarias.medicalconsultation.exceptions;

/**
 * Exception thrown when the use case flow is not followed correctly.
 * This indicates that preconditions for an operation have not been met,
 * such as calling operations out of sequence.
 */
public class ProceduralException extends Exception {
    public ProceduralException() {
        super("Use case flow not followed correctly");
    }

    public ProceduralException(String message) {
        super(message);
    }
}
