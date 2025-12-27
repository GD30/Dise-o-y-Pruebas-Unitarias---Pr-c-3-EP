package udl.prac3.pruebasunitarias.medicalconsultation;

import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import udl.prac3.pruebasunitarias.services.*;
import udl.prac3.pruebasunitarias.services.Suggestion;
import udl.prac3.pruebasunitarias.services.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.*;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

/**
 * Facade controller for the "Supervise Treatment" use case.
 * Manages the flow of medical consultation events using a state machine.
 */
public class ConsultationTerminal {

    private HealthNationalService hns;
    private DecisionMakingAI decisionAI;

    private MedicalHistory medicalHistory;
    private MedicalPrescription medicalPrescription;

    private HealthCardID currentCip;
    private String currentIllness;

    /**
     * State machine for consultation flow control
     */
    private enum ConsultationState {
        INIT,                      // Initial state
        REVISION_INITIALIZED,      // After initRevision()
        PRESCRIPTION_EDITING,      // After initMedicalPrescriptionEdition()
        PRESCRIPTION_FINISHED,     // After finishMedicalPrescriptionEdition()
        SIGNED                     // After stampeeSignature()
    }

    private ConsultationState state = ConsultationState.INIT;

    // AI-related state
    private String aiAnswer;
    private List<Suggestion> suggestions;

    public void setHealthNationalService(HealthNationalService hns) {
        this.hns = hns;
    }

    public void setDecisionMakingAI(DecisionMakingAI ai) {
        this.decisionAI = ai;
    }

    /**
     * Initiates a patient revision by downloading medical history and prescription
     */
    public void initRevision(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException,
            AnyCurrentPrescriptionException, ProceduralException {

        if (hns == null) {
            throw new ProceduralException("HealthNationalService not set");
        }

        requireState(ConsultationState.INIT);

        this.currentCip = cip;
        this.currentIllness = illness;

        this.medicalHistory = hns.getMedicalHistory(cip);
        this.medicalPrescription = hns.getMedicalPrescription(cip, illness);

        state = ConsultationState.REVISION_INITIALIZED;
    }

    /**
     * Adds medical assessment annotations to patient's history
     */
    public void enterMedicalAssessmentInHistory(String assess)
            throws ProceduralException {

        checkRevisionInitialized();
        medicalHistory.addMedicalHistoryAnnotations(assess);
    }

    /**
     * Starts the medical prescription edition process
     */
    public void initMedicalPrescriptionEdition()
            throws ProceduralException {

        requireState(ConsultationState.REVISION_INITIALIZED);
        state = ConsultationState.PRESCRIPTION_EDITING;
    }

    /**
     * Adds a medicine with its taking guidelines to the prescription
     */
    public void enterMedicineWithGuidelines(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException,
            ProceduralException {

        requireState(ConsultationState.PRESCRIPTION_EDITING);
        medicalPrescription.addLine(prodID, instruc);
    }

    /**
     * Modifies the dose in a prescription line
     */
    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException,
            ProceduralException {

        requireState(ConsultationState.PRESCRIPTION_EDITING);
        medicalPrescription.modifyDoseInLine(prodID, newDose);
    }

    /**
     * Removes a prescription line
     */
    public void removeLine(ProductID prodID)
            throws ProductNotInPrescriptionException,
            ProceduralException {

        requireState(ConsultationState.PRESCRIPTION_EDITING);
        medicalPrescription.removeLine(prodID);
    }

    /**
     * Sets the treatment ending date
     */
    public void enterTreatmentEndingDate(Date date)
            throws IncorrectEndingDateException,
            ProceduralException {

        requireState(ConsultationState.PRESCRIPTION_EDITING);

        Date now = new Date();
        if (date == null || !date.after(now)) {
            throw new IncorrectEndingDateException("Ending date must be in the future");
        }

        medicalPrescription.setPrescDate(now);
        medicalPrescription.setEndDate(date);
    }

    /**
     * Finishes the prescription edition process
     */
    public void finishMedicalPrescriptionEdition()
            throws ProceduralException {

        requireState(ConsultationState.PRESCRIPTION_EDITING);
        state = ConsultationState.PRESCRIPTION_FINISHED;
    }

    /**
     * Stamps the electronic signature on the prescription
     */
    public void stampeeSignature()
            throws eSignatureException, ProceduralException {

        requireState(ConsultationState.PRESCRIPTION_FINISHED);

        try {
            byte[] signatureData = new byte[]{1, 2, 3, 4, 5};
            DigitalSignature signature = new DigitalSignature(signatureData);
            medicalPrescription.seteSign(signature);
            state = ConsultationState.SIGNED;

        } catch (DigitalSignatureException e) {
            throw new eSignatureException("Failed to create digital signature: " + e.getMessage());
        }
    }

    /**
     * Sends the medical history and prescription to the Health National Service
     */
    public MedicalPrescription sendHistoryAndPrescription()
            throws ConnectException, HealthCardIDException,
            AnyCurrentPrescriptionException,
            NotCompletedMedicalPrescription,
            ProceduralException {

        if (hns == null) {
            throw new ProceduralException("HealthNationalService not set");
        }

        requireState(ConsultationState.SIGNED);

        return hns.sendHistoryAndPrescription(
                currentCip,
                medicalHistory,
                currentIllness,
                medicalPrescription
        );
    }

    /**
     * Prints the medical prescription (not implemented)
     */
    public void printMedicalPrescrip() {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Calls the Decision Making AI for support
     */
    public void callDecisionMakingAI()
            throws AIException, ProceduralException {

        checkRevisionInitialized();

        if (decisionAI == null) {
            throw new ProceduralException("DecisionMakingAI not set");
        }

        decisionAI.initDecisionMakingAI();
    }

    /**
     * Asks the AI for suggestions using a prompt
     */
    public void askAIForSuggest(String prompt)
            throws BadPromptException, ProceduralException {

        checkRevisionInitialized();

        if (decisionAI == null) {
            throw new ProceduralException("DecisionMakingAI not set");
        }

        aiAnswer = decisionAI.getSuggestions(prompt);
    }

    /**
     * Extracts structured guidelines from AI suggestions
     */
    public void extractGuidelinesFromSugg()
            throws ProceduralException {

        if (aiAnswer == null) {
            throw new ProceduralException("No AI answer available to extract guidelines from");
        }

        if (decisionAI == null) {
            throw new ProceduralException("DecisionMakingAI not set");
        }

        suggestions = decisionAI.parseSuggest(aiAnswer);
    }


    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public String getAiAnswer() {
        return aiAnswer;
    }

    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public MedicalPrescription getMedicalPrescription() {
        return medicalPrescription;
    }

    public ConsultationState getState() {
        return state;
    }

    /**
     * Checks if revision has been initialized (any state except INIT)
     */
    private void checkRevisionInitialized() throws ProceduralException {
        if (state == ConsultationState.INIT) {
            throw new ProceduralException("Revision must be initialized first");
        }
    }

    /**
     * Requires the consultation to be in a specific state
     */
    private void requireState(ConsultationState expected) throws ProceduralException {
        if (state != expected) {
            throw new ProceduralException(
                    "Invalid state. Expected: " + expected + ", current: " + state
            );
        }
    }
}