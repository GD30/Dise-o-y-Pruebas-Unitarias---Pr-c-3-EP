package udl.prac3.pruebasunitarias.medicalconsultation;

import udl.prac3.pruebasunitarias.data.HealthCardID;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.IncorrectParametersException;

public class MedicalHistory {
    private HealthCardID cip;
    private int membShipNumb;
    private String history;

    public MedicalHistory(HealthCardID cip, int memberShipNum)
            throws IncorrectParametersException {

        if (cip == null || memberShipNum <= 0) {
            throw new IncorrectParametersException("CIP cannot be null and membership number must be positive");
        }

        this.cip = cip;
        this.membShipNumb = memberShipNum;
        this.history = "";
    }

    public void addMedicalHistoryAnnotations(String annot) {
        if (annot == null || annot.isBlank()) {
            return;
        }

        if (!history.isEmpty()) {
            history += System.lineSeparator();
        }
        history += annot;
    }

    public void setNewDoctor(int mshN) throws IncorrectParametersException {
        if (mshN <= 0) {
            throw new IncorrectParametersException("Membership number must be positive");
        }
        this.membShipNumb = mshN;
    }

    public HealthCardID getCip() {
        return cip;
    }

    public int getMembShipNumb() {
        return membShipNumb;
    }

    public String getHistory() {
        return history;
    }
}