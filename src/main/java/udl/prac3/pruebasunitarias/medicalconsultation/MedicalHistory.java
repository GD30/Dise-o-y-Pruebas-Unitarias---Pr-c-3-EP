package udl.prac3.pruebasunitarias.medicalconsultation;

import data.HealthCardID;

/**
 * Package for the classes involved in the use case Supervise treatment
 */
public class MedicalHistory {// A class that represents a medical history

    private HealthCardID cip;      // the CIP of the patient
    private int membShipNumb;      // the membership number of the family doctor
    private String history;        // the diverse annotations in the patient’s HCE

    public MedicalHistory(HealthCardID cip, int memberShipNum)
            throws IncorrectParametersException {

        if (cip == null || memberShipNum <= 0) {
            throw new IncorrectParametersException();
        }

        this.cip = cip;
        this.membShipNumb = memberShipNum;
        this.history = "";
    }

    /**
     * Adds new annotations to the patient history
     */
    public void addMedicalHistoryAnnotations(String annot) {

        if (annot == null || annot.isBlank()) {
            return; // no afegim anotacions buides
        }

        if (!history.isEmpty()) {
            history += System.lineSeparator();
        }
        history += annot;
    }

    /**
     * Modifies the family doctor for patient
     */
    public void setNewDoctor(int mshN)// Modifies the family doctor for patient
            throws IncorrectParametersException {

        if (mshN <= 0) {
            throw new IncorrectParametersException();
        }
        this.membShipNumb = mshN;
    }

    //getters

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