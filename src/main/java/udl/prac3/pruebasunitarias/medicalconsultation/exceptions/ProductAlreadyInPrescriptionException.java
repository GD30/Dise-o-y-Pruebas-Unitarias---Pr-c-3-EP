package udl.prac3.pruebasunitarias.medicalconsultation.exceptions;

/**
 * Exception thrown when trying to add a product that already exists in the prescription.
 * A prescription cannot contain duplicate product IDs.
 */
public class ProductAlreadyInPrescriptionException extends Exception {
    public ProductAlreadyInPrescriptionException() {
        super("Product already exists in the prescription");
    }

    public ProductAlreadyInPrescriptionException(String message) {
        super(message);
    }
}
