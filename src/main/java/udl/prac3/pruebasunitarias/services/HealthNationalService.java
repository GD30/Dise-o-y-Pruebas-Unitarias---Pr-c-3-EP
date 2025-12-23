package udl.prac3.pruebasunitarias.services;

import udl.prac3.pruebasunitarias.data.HealthCardID;

/**
 * External services for managing and storing ePrescriptions from population and IA support
 */

public interface HealthNationalService {
    MedicalHistory getMedicalHistory (HealthCardID cip)
            throws ConnectException, HealthCardIDException;

    MedicalPrescription5 getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException,
            AnyCurrentPrescriptionException;

    MedicalPrescription6 sendHistoryAndPrescription(HealthCardID cip,
                                                    History hce, String illness, MedicalPrescription mPresc)
            throws ConnectException, HealthCardIDException,
            AnyCurrentPrescriptionException, NotCompletedMedicalPrescription;

    // Internal operation
    MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc)
            throws ConnectException;
}


