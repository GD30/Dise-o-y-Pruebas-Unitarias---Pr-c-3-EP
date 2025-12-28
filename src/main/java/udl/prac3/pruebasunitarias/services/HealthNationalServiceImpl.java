package udl.prac3.pruebasunitarias.services;

import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalHistory;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalPrescription;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.*;
import udl.prac3.pruebasunitarias.services.exceptions.*;

import java.net.ConnectException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class HealthNationalServiceImpl implements HealthNationalService {

    private final Map<HealthCardID, MedicalHistory> historyDB = new HashMap<>();
    private final Map<String, MedicalPrescription> prescriptionDB = new HashMap<>();
    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    @Override
    public MedicalHistory getMedicalHistory(HealthCardID cip)
            throws ConnectException, HealthCardIDException {

        if (cip == null) {
            throw new HealthCardIDException("HealthCardID cannot be null");
        }

        MedicalHistory history = historyDB.get(cip);

        if (history == null) {
            try {
                history = new MedicalHistory(cip, 12345); // Número de membresía por defecto
                historyDB.put(cip, history);
            } catch (IncorrectParametersException e) {
                throw new HealthCardIDException("Error creating MedicalHistory: " + e.getMessage());
            }
        }

        return history;
    }

    @Override
    public MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException {

        if (cip == null) {
            throw new HealthCardIDException("HealthCardID cannot be null");
        }

        if (illness == null || illness.isBlank()) {
            throw new AnyCurrentPrescriptionException("Illness not specified");
        }

        String key = generateKey(cip, illness);
        MedicalPrescription presc = prescriptionDB.get(key);

        if (presc == null) {
            try {
                presc = new MedicalPrescription(cip, 12345, illness);
                prescriptionDB.put(key, presc);
            } catch (IncorrectParametersException e) {
                throw new AnyCurrentPrescriptionException("Error creating MedicalPrescription: " + e.getMessage());
            }
        }

        return presc;
    }

    @Override
    public MedicalPrescription sendHistoryAndPrescription(HealthCardID cip,
                                                          MedicalHistory hce,
                                                          String illness,
                                                          MedicalPrescription mPresc)
            throws ConnectException, HealthCardIDException,
            AnyCurrentPrescriptionException, NotCompletedMedicalPrescription {

        if (cip == null) {
            throw new HealthCardIDException("HealthCardID is null");
        }

        if (hce == null || mPresc == null) {
            throw new AnyCurrentPrescriptionException("History or prescription is null");
        }

        // Validación correcta con la excepción correcta
        if (!mPresc.isComplete()) {
            throw new NotCompletedMedicalPrescription(
                    "Prescription is not complete. Missing: " +
                            (mPresc.getPrescDate() == null ? "prescDate " : "") +
                            (mPresc.getEndDate() == null ? "endDate " : "") +
                            (mPresc.geteSign() == null ? "signature " : "") +
                            (mPresc.getLines().isEmpty() ? "lines " : "")
            );
        }

        historyDB.put(cip, hce);
        return generateTreatmCodeAndRegister(mPresc);
    }

    @Override
    public MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc)
            throws ConnectException {

        if (ePresc.getPrescCode() == null) {
            try {
                String code = generateRandomCode(16);
                ePresc.setPrescCode(new ePrescripCode(code));
                System.out.println("✓ Código de prescripción generado: " + code);
            } catch (ePrescripCodeException ex) {
                throw new ConnectException("Error generating ePrescripCode: " + ex.getMessage());
            }
        }

        String key = generateKey(ePresc.getCip(), ePresc.getIllness());
        prescriptionDB.put(key, ePresc);

        return ePresc;
    }

    private String generateKey(HealthCardID cip, String illness) {
        return cip.getPersonalID() + "_" + illness;
    }

    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUM.charAt(random.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }
}