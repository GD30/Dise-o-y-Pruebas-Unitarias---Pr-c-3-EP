package udl.prac3.pruebasunitarias.services.exceptions;

/**
 * Exception thrown when trying to send an incomplete medical prescription.
 * The prescription must have all required fields filled before transmission,
 * including signature, dates, and at least one prescription line.
 */
public class NotCompletedMedicalPrescription extends Exception {
    public NotCompletedMedicalPrescription() {
        super("Medical prescription is incomplete");
    }

    public NotCompletedMedicalPrescription(String message) {
        super(message);
    }
}