package udl.prac3.pruebasunitarias.services;

import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalHistory;
import udl.prac3.pruebasunitarias.medicalconsultation.MedicalPrescription;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.*;
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
            throws ConnectException, HealthCardIDException, ProceduralException {

        if (cip == null) throw new HealthCardIDException("HealthCardID cannot be null");

        MedicalHistory history = historyDB.get(cip);

        if (history == null) {
            try {
                history = new MedicalHistory(cip, 1); // constructor con 2 args
                historyDB.put(cip, history);
            } catch (IncorrectParametersException e) {
                throw new ProceduralException("Error creando MedicalHistory: " + e.getMessage());
            }
        }

        return history;
    }


    @Override
    public MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException, ProceduralException {

        if (cip == null) throw new HealthCardIDException("HealthCardID cannot be null");
        if (illness == null || illness.isBlank())
            throw new ProceduralException("Illness no especificada");

        String key = cip.toString() + "_" + illness;
        MedicalPrescription presc = prescriptionDB.get(key);

        if (presc == null) {
            try {
                presc = new MedicalPrescription(cip, 1, illness);
                prescriptionDB.put(key, presc);
            } catch (IncorrectParametersException e) {
                throw new ProceduralException("Error creando MedicalPrescription: " + e.getMessage());
            }
        }

        return presc;
    }

    @Override
    public MedicalPrescription sendHistoryAndPrescription(HealthCardID cip,
                                                          MedicalHistory hce,
                                                          String illness,
                                                          MedicalPrescription mPresc)
            throws ConnectException, HealthCardIDException, ProceduralException {

        if (cip == null) throw new HealthCardIDException("HealthCardID null");
        if (hce == null || mPresc == null)
            throw new ProceduralException("History o prescription null");
        if (!mPresc.isComplete())
            throw new ProceduralException("Prescripción no completa");

        historyDB.put(cip, hce);
        return generateTreatmCodeAndRegister(mPresc);
    }

    @Override
    public MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc)
            throws ConnectException {

        if (ePresc.getPrescCode() == null) {
            try {
                ePresc.setPrescCode(new ePrescripCode(generateRandomCode(16)));
            } catch (ePrescripCodeException ex) {
                throw new RuntimeException("Error generando ePrescripCode", ex);
            }
        }

        String key = ePresc.getCip().toString() + "_" + ePresc.getIllness();
        prescriptionDB.put(key, ePresc);

        return ePresc;
    }

    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHANUM.charAt(random.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }
}
