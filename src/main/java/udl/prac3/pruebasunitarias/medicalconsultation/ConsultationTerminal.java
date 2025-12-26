package udl.prac3.pruebasunitarias.medicalconsultation;

import data.*;
import services.*;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

public class ConsultationTerminal {

    private HealthNationalService hns;
    private DecisionMakingAI decisionAI;
    
    private MedicalHistory medicalHistory;
    private MedicalPrescription medicalPrescription;

    private HealthCardID currentCip;
    private String currentIllness;

    private boolean revisionInitialized = false;
    private boolean prescriptionEditionStarted = false;
    private boolean prescriptionEditionFinished = false;
    private boolean signed = false;

    private String aiAnswer;
    private List<Suggestion> suggestions;

    public void setHealthNationalService(HealthNationalService hns) {
        this.hns = hns;
    }

    public void setDecisionMakingAI(DecisionMakingAI ai) {
        this.decisionAI = ai;
    }

    public void initRevision(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException,
            AnyCurrentPrescriptionException {

        this.currentCip = cip;
        this.currentIllness = illness;

        this.medicalHistory = hns.getMedicalHistory(cip);
        this.medicalPrescription = hns.getMedicalPrescription(cip, illness);

        this.revisionInitialized = true;
    }

    public void enterMedicalAssessmentInHistory(String assess)
            throws ProceduralException {

        checkRevisionInitialized();
        medicalHistory.addMedicalHistoryAnnotations(assess);
    }

    public void initMedicalPrescriptionEdition()
            throws ProceduralException {

        checkRevisionInitialized();
        this.prescriptionEditionStarted = true;
    }

    public void enterMedicineWithGuidelines(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException,
            ProceduralException {

        checkPrescriptionEditionStarted();
        medicalPrescription.addLine(prodID, instruc);
    }

    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException,
            ProceduralException {

        checkPrescriptionEditionStarted();
        medicalPrescription.modifyDoseInLine(prodID, newDose);
    }

    public void removeLine(ProductID prodID)
            throws ProductNotInPrescriptionException,
            ProceduralException {

        checkPrescriptionEditionStarted();
        medicalPrescription.removeLine(prodID);
    }

    public void enterTreatmentEndingDate(Date date)
            throws IncorrectEndingDateException,
            ProceduralException {

        checkPrescriptionEditionStarted();

        Date now = new Date();
        if (date == null || !date.after(now)) {
            throw new IncorrectEndingDateException();
        }

        medicalPrescription.setPrescDate(now);
        medicalPrescription.setEndDate(date);
    }

    public void finishMedicalPrescriptionEdition()
            throws ProceduralException {

        checkPrescriptionEditionStarted();
        this.prescriptionEditionFinished = true;
    }

    public void stampeeSignature()
            throws eSignatureException,
            ProceduralException {

        checkPrescriptionEditionFinished();

        // Simplified signature generation
        this.medicalPrescription.seteSign(
                new DigitalSignature(new byte[]{1, 2, 3})
        );
        this.signed = true;
    }

    public MedicalPrescription sendHistoryAndPrescription()
            throws ConnectException, HealthCardIDException,
            AnyCurrentPrescriptionException,
            NotCompletedMedicalPrescription,
            ProceduralException {

        if (!signed) {
            throw new ProceduralException();
        }

        MedicalPrescription updated =
                hns.sendHistoryAndPrescription(
                        currentCip,
                        medicalHistory,
                        currentIllness,
                        medicalPrescription
                );

        this.medicalPrescription = updated;
        return updated;
    }

    public void printMedicalPrescrip() {
        // No implementat
    }

    public void callDecisionMakingAI()
            throws AIException,
            ProceduralException {

        checkRevisionInitialized();
        decisionAI.initDecisionMakingAI();
    }

    public void askAIForSuggest(String prompt)
            throws BadPromptException,
            ProceduralException {

        checkRevisionInitialized();
        aiAnswer = decisionAI.getSuggestions(prompt);
    }

    public void extractGuidelinesFromSugg()
            throws ProceduralException {

        if (aiAnswer == null) {
            throw new ProceduralException();
        }
        suggestions = decisionAI.parseSuggest(aiAnswer);
    }

    private void checkRevisionInitialized()
            throws ProceduralException {

        if (!revisionInitialized) {
            throw new ProceduralException();
        }
    }

    private void checkPrescriptionEditionStarted()
            throws ProceduralException {

        if (!prescriptionEditionStarted) {
            throw new ProceduralException();
        }
    }

    private void checkPrescriptionEditionFinished()
            throws ProceduralException {

        if (!prescriptionEditionFinished) {
            throw new ProceduralException();
        }
    }
}