package udl.prac3.pruebasunitarias.services;

import udl.prac3.pruebasunitarias.data.HealthCardID;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalHistory;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalPrescription;
import udl.prac3.pruebasunitarias.data.exceptions.HealthCardIDException;
import udl.prac3.pruebasunitarias.services.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.ProceduralException;

import java.net.ConnectException;

public interface HealthNationalService {

    MedicalHistory getMedicalHistory(HealthCardID cip)
            throws ConnectException, HealthCardIDException, ProceduralException;

    MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException,
            AnyCurrentPrescriptionException, ProceduralException;

    MedicalPrescription sendHistoryAndPrescription(HealthCardID cip,
                                                   MedicalHistory hce,
                                                   String illness,
                                                   MedicalPrescription mPresc)
            throws ConnectException, HealthCardIDException,
            AnyCurrentPrescriptionException,
            NotCompletedMedicalPrescription,
            ProceduralException;


    MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc)
            throws ConnectException;
}