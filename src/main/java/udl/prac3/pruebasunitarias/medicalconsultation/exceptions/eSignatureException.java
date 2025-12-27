package udl.prac3.pruebasunitarias.medicalconsultation.exceptions;

/**
 * Exception thrown when there's a problem stamping the electronic signature.
 * This could be due to hardware failures, authentication issues, or signature validation problems.
 */
public class eSignatureException extends Exception {
    public eSignatureException() {
        super("Error stamping electronic signature");
    }

    public eSignatureException(String message) {
        super(message);
    }
}
