package udl.prac3.pruebasunitarias.medicalconsultation;

import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.*;

import java.util.*;

public class MedicalPrescription {

    private final HealthCardID cip;
    private final int membShipNumb;
    private final String illness;

    private ePrescripCode prescCode;
    private Date prescDate;
    private Date endDate;
    private DigitalSignature eSign;

    private final Map<ProductID, TakingGuideline> lines;

    public MedicalPrescription(HealthCardID cip, int membShipNumb, String illness)
            throws IncorrectParametersException {

        if (cip == null || illness == null || illness.isBlank() || membShipNumb <= 0) {
            throw new IncorrectParametersException("Incorrect medical prescription parameters");
        }

        this.cip = cip;
        this.membShipNumb = membShipNumb;
        this.illness = illness;
        this.lines = new HashMap<>();
    }

    // ========== Prescription lines ==========

    public void addLine(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException {

        if (prodID == null || instruc == null || instruc.length < 5) {
            throw new IncorrectTakingGuidelinesException("Invalid parameters for prescription line");
        }

        if (lines.containsKey(prodID)) {
            throw new ProductAlreadyInPrescriptionException(
                    "Product " + prodID + " already in prescription"
            );
        }

        try {
            dayMoment dMoment = dayMoment.valueOf(instruc[0]);
            float duration = Float.parseFloat(instruc[1]);
            float dose = Float.parseFloat(instruc[2]);
            float freq = Float.parseFloat(instruc[3]);
            FqUnit freqUnit = FqUnit.valueOf(instruc[4]);
            String instructions = instruc.length > 5 ? instruc[5] : null;

            TakingGuideline guideline =
                    new TakingGuideline(dMoment, duration, dose, freq, freqUnit, instructions);

            lines.put(prodID, guideline);

        } catch (RuntimeException e) {
            throw new IncorrectTakingGuidelinesException("Error parsing taking guidelines: " + e);
        }
    }

    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException {

        TakingGuideline tg = lines.get(prodID);
        if (tg == null) {
            throw new ProductNotInPrescriptionException(
                    "Product " + prodID + " not found in prescription"
            );
        }

        tg.getPosology().setDose(newDose);
    }

    public void removeLine(ProductID prodID)
            throws ProductNotInPrescriptionException {

        if (!lines.containsKey(prodID)) {
            throw new ProductNotInPrescriptionException(
                    "Product " + prodID + " not found in prescription"
            );
        }
        lines.remove(prodID);
    }

    // ========== Prescription completion ==========

    public boolean isComplete() {
        return prescDate != null
                && endDate != null
                && eSign != null
                && !lines.isEmpty();
    }

    // ========== Getters ==========

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

    public Date getPrescDate() {
        return prescDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public DigitalSignature geteSign() {
        return eSign;
    }

    public Map<ProductID, TakingGuideline> getLines() {
        return Collections.unmodifiableMap(lines);
    }

    // ========== Controlled setters (used by HNS / terminal) ==========

    /**
     * The prescription code is assigned by the Health National Service.
     * It can only be set once.
     */
    public void setPrescCode(ePrescripCode prescCode) {
        if (prescCode == null) {
            throw new IllegalArgumentException("Prescription code cannot be null");
        }
        if (this.prescCode != null) {
            throw new IllegalStateException("Prescription code already assigned");
        }
        this.prescCode = prescCode;
    }

    public void setPrescDate(Date prescDate) {
        if (prescDate == null) {
            throw new IllegalArgumentException("Prescription date cannot be null");
        }
        this.prescDate = prescDate;
    }

    public void setEndDate(Date endDate) {
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        this.endDate = endDate;
    }

    public void seteSign(DigitalSignature eSign) {
        if (eSign == null) {
            throw new IllegalArgumentException("Digital signature cannot be null");
        }
        this.eSign = eSign;
    }
}
