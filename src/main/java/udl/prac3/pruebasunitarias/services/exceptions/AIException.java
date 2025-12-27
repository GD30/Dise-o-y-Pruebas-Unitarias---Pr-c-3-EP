package udl.prac3.pruebasunitarias.services.exceptions;

/**
 * Exception thrown when there's a problem with the AI service invocation.
 * This could indicate AI service unavailability, initialization failures,
 * or other AI-related technical issues.
 */
public class AIException extends Exception {
    public AIException() {
        super("AI service error");
    }

    public AIException(String message) {
        super(message);
    }
}
