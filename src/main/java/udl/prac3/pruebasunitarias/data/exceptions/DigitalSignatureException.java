package udl.prac3.pruebasunitarias.data.exceptions;

/**
 * Exception thrown when a DigitalSignature is invalid.
 * This includes null values or empty byte arrays.
 */
public class DigitalSignatureException extends Exception {
    public DigitalSignatureException(String message) {
        super(message);
    }
}