package udl.prac3.pruebasunitarias.medicalconsultation;

import data.*;
import java.util.*;

public class MedicalPrescription {// A class that represents medical prescription
    private HealthCardID cip; // the healthcard ID of the patient
    private int membShipNumb; // the membership number of the family doctor
    private String illness; // illness associated
    private ePrescripCode prescCode; // the prescription code
    private Date prescDate; // the current date
    private Date endDate; // the date when the new treatment ends
    private DigitalSignature eSign; // the eSignature of the doctor

    private Map<ProductID, TakingGuideline> lines;

    // Its components, that is, the set of medical prescription lines
    public MedicalPrescription (HealthCardID cip, int membShipNumb, String illness) {
        if (cip == null || illness == null || illness.isBlank() || membShipNumb <= 0) {
            throw new IllegalArgumentException("Incorrect medical prescription parameters");
        }
        this.cip = cip;
        this.membShipNumb = membShipNumb;
        this.illness = illness;
        this.lines = new HashMap<>();
    }

    public void addLine(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException {

        if (prodID == null || instruc == null) {
            throw new IncorrectTakingGuidelinesException();
        }

        if (lines.containsKey(prodID)) {
            throw new ProductAlreadyInPrescriptionException();
        }

        try {
            // Expected order:
            // 0: dayMoment
            // 1: duration
            // 2: dose
            // 3: frequency
            // 4: frequency unit
            // 5: instructions (optional)

            dayMoment dM = dayMoment.valueOf(instruc[0]);
            float du = Float.parseFloat(instruc[1]);
            float d = Float.parseFloat(instruc[2]);
            float f = Float.parseFloat(instruc[3]);
            FqUnit fu = FqUnit.valueOf(instruc[4]);
            String i = instruc.length > 5 ? instruc[5] : null;

            TakingGuideline tg = new TakingGuideline(dM, du, d, f, fu, i);
            lines.put(prodID, tg);

        } catch (Exception e) {
            throw new IncorrectTakingGuidelinesException();
        }
    }

    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException {

        TakingGuideline tg = lines.get(prodID);
        if (tg == null) {
            throw new ProductNotInPrescriptionException();
        }

        tg.getPosology().setDose(newDose);
    }

    public void removeLine(ProductID prodID)
            throws ProductNotInPrescriptionException {

        if (!lines.containsKey(prodID)) {
            throw new ProductNotInPrescriptionException();
        }
        lines.remove(prodID);
    }

    public HealthCardID getCip() {
        return cip;
    }

    public int getMembShipNumb() {
        return membShipNumb;
    }

    public String getIllness() {
        return illness;
    }

    public ePrescripCode getPrescCode() {
        return prescCode;
    }

    public void setPrescCode(ePrescripCode prescCode) {
        this.prescCode = prescCode;
    }

    public Date getPrescDate() {
        return prescDate;
    }

    public void setPrescDate(Date prescDate) {
        this.prescDate = prescDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DigitalSignature geteSign() {
        return eSign;
    }

    public void seteSign(DigitalSignature eSign) {
        this.eSign = eSign;
    }

    public Map<ProductID, TakingGuideline> getLines() {
        return Collections.unmodifiableMap(lines);
    }
}

