package udl.prac3.pruebasunitarias.services.exceptions;

/**
 * Exception thrown when the prompt provided to the AI is unclear or inconsistent.
 * The AI cannot process prompts that are ambiguous, incomplete, or contradictory.
 */
public class BadPromptException extends Exception {
    public BadPromptException() {
        super("Prompt is unclear or inconsistent");
    }

    public BadPromptException(String message) {
        super(message);
    }
}
