package udl.prac3.pruebasunitarias.medicalconsultation.exceptions;

/**
 * Exception thrown when trying to modify or remove a product that doesn't exist in the prescription.
 * The product ID must be present in the prescription to perform these operations.
 */
public class ProductNotInPrescriptionException extends Exception {
    public ProductNotInPrescriptionException() {
        super("Product not found in the prescription");
    }

    public ProductNotInPrescriptionException(String message) {
        super(message);
    }
}
