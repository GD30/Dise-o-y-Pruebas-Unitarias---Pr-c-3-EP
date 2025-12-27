package udl.prac3.pruebasunitarias.medicalconsultation;

import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.*;

import java.util.*;

public class MedicalPrescription {
    private HealthCardID cip;
    private int membShipNumb;
    private String illness;
    private ePrescripCode prescCode;
    private Date prescDate;
    private Date endDate;
    private DigitalSignature eSign;
    private Map<ProductID, TakingGuideline> lines;

    public MedicalPrescription(HealthCardID cip, int membShipNumb, String illness)
            throws IncorrectParametersException {

        if (cip == null || illness == null || illness.isBlank() || membShipNumb <= 0) throw new IncorrectParametersException("Incorrect medical prescription parameters");

        this.cip = cip;
        this.membShipNumb = membShipNumb;
        this.illness = illness;
        this.lines = new HashMap<>();
    }

    public void addLine(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException {

        if (prodID == null || instruc == null || instruc.length < 5) throw new IncorrectTakingGuidelinesException("Invalid parameters for prescription line");
        else if (lines.containsKey(prodID)) throw new ProductAlreadyInPrescriptionException("Product " + prodID + " already in prescription");

        try {
            dayMoment dM = dayMoment.valueOf(instruc[0]);
            float du = Float.parseFloat(instruc[1]);
            float d = Float.parseFloat(instruc[2]);
            float f = Float.parseFloat(instruc[3]);
            FqUnit fu = FqUnit.valueOf(instruc[4]);
            String i = instruc.length > 5 ? instruc[5] : null;

            TakingGuideline tg = new TakingGuideline(dM, du, d, f, fu, i);
            lines.put(prodID, tg);

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            throw new IncorrectTakingGuidelinesException("Error parsing taking guidelines: " + e.getMessage());
        }
    }

    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException {

        TakingGuideline tg = lines.get(prodID);
        if (tg == null) throw new ProductNotInPrescriptionException("Product " + prodID + " not found in prescription");

        tg.getPosology().setDose(newDose);
    }

    public void removeLine(ProductID prodID)
            throws ProductNotInPrescriptionException {

        if (!lines.containsKey(prodID)) throw new ProductNotInPrescriptionException("Product " + prodID + " not found in prescription");

        lines.remove(prodID);
    }

    public boolean isComplete() {
        return prescDate != null &&
                endDate != null &&
                eSign != null &&
                !lines.isEmpty();
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