package udl.prac3.pruebasunitarias.medicalconsultation;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import udl.prac3.pruebasunitarias.services.*;
import udl.prac3.pruebasunitarias.services.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.*;

import java.net.ConnectException;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ConsultationTerminalTest {

    private ConsultationTerminal terminal;
    private HealthNationalService mockHNS;
    private DecisionMakingAI mockAI;

    private HealthCardID validCip;
    private ProductID validProduct1;
    private ProductID validProduct2;
    private MedicalHistory mockHistory;
    private MedicalPrescription mockPrescription;

    private static int testIdCounter = 0;
    private static int testProductCounter = 0;

    @BeforeEach
    void setUp() throws Exception {
        // Create mocks
        mockHNS = Mockito.mock(HealthNationalService.class);
        mockAI = Mockito.mock(DecisionMakingAI.class);

        // Create terminal and inject dependencies
        terminal = new ConsultationTerminal();
        terminal.setHealthNationalService(mockHNS);
        terminal.setDecisionMakingAI(mockAI);

        // Create test data
        validCip = new HealthCardID(String.format("TEST%012d", testIdCounter++));
        validProduct1 = new ProductID(String.format("%012d", testProductCounter++));
        validProduct2 = new ProductID(String.format("%012d", testProductCounter++));

        // Create real objects for mocking responses
        mockHistory = new MedicalHistory(validCip, 12345);
        mockPrescription = new MedicalPrescription(validCip, 12345, "Hypertension");
    }

    @Test
    @DisplayName("initRevision throws ProceduralException when HNS not set")
    void initRevision_hnsNotSet_throwsProceduralException() {
        ConsultationTerminal terminalWithoutHNS = new ConsultationTerminal();

        Assertions.assertThrows(ProceduralException.class,
                () -> terminalWithoutHNS.initRevision(validCip, "Hypertension"));
    }

    @Test
    @DisplayName("initRevision calls HNS to get medical history and prescription")
    void initRevision_success_callsHNS() throws Exception {
        when(mockHNS.getMedicalHistory(validCip)).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(validCip, "Hypertension")).thenReturn(mockPrescription);

        terminal.initRevision(validCip, "Hypertension");

        verify(mockHNS).getMedicalHistory(validCip);
        verify(mockHNS).getMedicalPrescription(validCip, "Hypertension");
        Assertions.assertNotNull(terminal.getMedicalHistory());
        Assertions.assertNotNull(terminal.getMedicalPrescription());
    }

    @Test
    @DisplayName("initRevision propagates ConnectException from HNS")
    void initRevision_connectException_propagated() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenThrow(new ConnectException("Network error"));

        Assertions.assertThrows(ConnectException.class,
                () -> terminal.initRevision(validCip, "Hypertension"));
    }

    @Test
    @DisplayName("initRevision propagates HealthCardIDException from HNS")
    void initRevision_healthCardIDException_propagated() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenThrow(new HealthCardIDException("CIP not found"));

        Assertions.assertThrows(HealthCardIDException.class,
                () -> terminal.initRevision(validCip, "Hypertension"));
    }

    @Test
    @DisplayName("initRevision propagates AnyCurrentPrescriptionException from HNS")
    void initRevision_noPrescriptionException_propagated() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString()))
                .thenThrow(new AnyCurrentPrescriptionException("No prescription found"));

        Assertions.assertThrows(AnyCurrentPrescriptionException.class,
                () -> terminal.initRevision(validCip, "Hypertension"));
    }

    @Test
    @DisplayName("enterMedicalAssessmentInHistory throws ProceduralException when revision not initialized")
    void enterAssessment_revisionNotInitialized_throwsProceduralException() {
        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.enterMedicalAssessmentInHistory("Patient feeling better"));
    }

    @Test
    @DisplayName("enterMedicalAssessmentInHistory adds annotation after initRevision")
    void enterAssessment_afterInit_success() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");

        terminal.enterMedicalAssessmentInHistory("Patient reports improvement");

        String history = terminal.getMedicalHistory().getHistory();
        Assertions.assertTrue(history.contains("Patient reports improvement"));
    }

    @Test
    @DisplayName("initMedicalPrescriptionEdition throws ProceduralException when revision not initialized")
    void initPrescriptionEdition_revisionNotInitialized_throwsProceduralException() {
        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.initMedicalPrescriptionEdition());
    }

    @Test
    @DisplayName("initMedicalPrescriptionEdition succeeds after initRevision")
    void initPrescriptionEdition_afterInit_success() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");

        Assertions.assertDoesNotThrow(() -> terminal.initMedicalPrescriptionEdition());
    }


    @Test
    @DisplayName("enterMedicineWithGuidelines throws ProceduralException when prescription edition not started")
    void enterMedicine_editionNotStarted_throwsProceduralException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");

        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};

        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.enterMedicineWithGuidelines(validProduct1, instruc));
    }

    @Test
    @DisplayName("enterMedicineWithGuidelines adds line after starting prescription edition")
    void enterMedicine_afterEditionStarted_success() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();

        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with water"};

        terminal.enterMedicineWithGuidelines(validProduct1, instruc);

        Assertions.assertEquals(1, terminal.getMedicalPrescription().getLines().size());
    }

    @Test
    @DisplayName("enterMedicineWithGuidelines propagates ProductAlreadyInPrescriptionException")
    void enterMedicine_duplicateProduct_throwsException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();

        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        terminal.enterMedicineWithGuidelines(validProduct1, instruc);

        Assertions.assertThrows(ProductAlreadyInPrescriptionException.class,
                () -> terminal.enterMedicineWithGuidelines(validProduct1, instruc));
    }


    @Test
    @DisplayName("modifyDoseInLine throws ProceduralException when prescription edition not started")
    void modifyDose_editionNotStarted_throwsProceduralException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");

        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.modifyDoseInLine(validProduct1, 5.0f));
    }

    @Test
    @DisplayName("modifyDoseInLine modifies dose successfully")
    void modifyDose_afterAddingLine_success() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();

        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        terminal.enterMedicineWithGuidelines(validProduct1, instruc);

        terminal.modifyDoseInLine(validProduct1, 3.0f);

        float actualDose = terminal.getMedicalPrescription()
                .getLines().get(validProduct1).getPosology().getDose();
        Assertions.assertEquals(3.0f, actualDose);
    }


    @Test
    @DisplayName("removeLine throws ProceduralException when prescription edition not started")
    void removeLine_editionNotStarted_throwsProceduralException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");

        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.removeLine(validProduct1));
    }

    @Test
    @DisplayName("removeLine removes line successfully")
    void removeLine_afterAddingLine_success() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();

        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        terminal.enterMedicineWithGuidelines(validProduct1, instruc);

        terminal.removeLine(validProduct1);

        Assertions.assertEquals(0, terminal.getMedicalPrescription().getLines().size());
    }


    @Test
    @DisplayName("enterTreatmentEndingDate throws ProceduralException when prescription edition not started")
    void enterEndingDate_editionNotStarted_throwsProceduralException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");

        Date futureDate = new Date(System.currentTimeMillis() + 86400000);

        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.enterTreatmentEndingDate(futureDate));
    }

    @Test
    @DisplayName("enterTreatmentEndingDate rejects past date")
    void enterEndingDate_pastDate_throwsException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();

        Date pastDate = new Date(System.currentTimeMillis() - 86400000);

        Assertions.assertThrows(IncorrectEndingDateException.class,
                () -> terminal.enterTreatmentEndingDate(pastDate));
    }

    @Test
    @DisplayName("enterTreatmentEndingDate accepts future date")
    void enterEndingDate_futureDate_success() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();

        Date futureDate = new Date(System.currentTimeMillis() + 86400000);

        Assertions.assertDoesNotThrow(() -> terminal.enterTreatmentEndingDate(futureDate));
        Assertions.assertNotNull(terminal.getMedicalPrescription().getPrescDate());
        Assertions.assertNotNull(terminal.getMedicalPrescription().getEndDate());
    }


    @Test
    @DisplayName("finishMedicalPrescriptionEdition throws ProceduralException when edition not started")
    void finishEdition_editionNotStarted_throwsProceduralException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");

        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.finishMedicalPrescriptionEdition());
    }

    @Test
    @DisplayName("finishMedicalPrescriptionEdition succeeds after starting edition")
    void finishEdition_afterEditionStarted_success() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();

        Assertions.assertDoesNotThrow(() -> terminal.finishMedicalPrescriptionEdition());
    }

    @Test
    @DisplayName("stampeeSignature throws ProceduralException when edition not finished")
    void stampSignature_editionNotFinished_throwsProceduralException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();

        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.stampeeSignature());
    }

    @Test
    @DisplayName("stampeeSignature stamps signature successfully")
    void stampSignature_afterFinishingEdition_success() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();
        terminal.finishMedicalPrescriptionEdition();

        terminal.stampeeSignature();

        Assertions.assertNotNull(terminal.getMedicalPrescription().geteSign());
    }

    @Test
    @DisplayName("sendHistoryAndPrescription throws ProceduralException when not signed")
    void sendHistory_notSigned_throwsProceduralException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();
        terminal.finishMedicalPrescriptionEdition();

        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.sendHistoryAndPrescription());
    }

    @Test
    @DisplayName("sendHistoryAndPrescription calls HNS after signing")
    void sendHistory_afterSigning_callsHNS() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        when(mockHNS.sendHistoryAndPrescription(any(), any(), anyString(), any()))
                .thenReturn(mockPrescription);

        terminal.initRevision(validCip, "Hypertension");
        terminal.initMedicalPrescriptionEdition();
        terminal.finishMedicalPrescriptionEdition();
        terminal.stampeeSignature();

        terminal.sendHistoryAndPrescription();

        verify(mockHNS).sendHistoryAndPrescription(
                eq(validCip),
                any(MedicalHistory.class),
                eq("Hypertension"),
                any(MedicalPrescription.class)
        );
    }

    @Test
    @DisplayName("callDecisionMakingAI throws ProceduralException when revision not initialized")
    void callAI_revisionNotInitialized_throwsProceduralException() {
        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.callDecisionMakingAI());
    }

    @Test
    @DisplayName("callDecisionMakingAI throws ProceduralException when AI not set")
    void callAI_aiNotSet_throwsProceduralException() throws Exception {
        ConsultationTerminal terminalWithoutAI = new ConsultationTerminal();
        terminalWithoutAI.setHealthNationalService(mockHNS);
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminalWithoutAI.initRevision(validCip, "Hypertension");

        Assertions.assertThrows(ProceduralException.class,
                () -> terminalWithoutAI.callDecisionMakingAI());
    }

    @Test
    @DisplayName("callDecisionMakingAI calls AI initialization")
    void callAI_afterInit_callsAI() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");

        terminal.callDecisionMakingAI();

        verify(mockAI).initDecisionMakingAI();
    }

    @Test
    @DisplayName("askAIForSuggest stores AI answer")
    void askAI_storesAnswer() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        when(mockAI.getSuggestions(anyString())).thenReturn("AI recommendation here");
        terminal.initRevision(validCip, "Hypertension");

        terminal.askAIForSuggest("What should I adjust?");

        verify(mockAI).getSuggestions("What should I adjust?");
        Assertions.assertEquals("AI recommendation here", terminal.getAiAnswer());
    }

    @Test
    @DisplayName("extractGuidelinesFromSugg throws ProceduralException when no AI answer")
    void extractGuidelines_noAnswer_throwsProceduralException() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        terminal.initRevision(validCip, "Hypertension");

        Assertions.assertThrows(ProceduralException.class,
                () -> terminal.extractGuidelinesFromSugg());
    }

    @Test
    @DisplayName("Complete use case workflow from start to finish")
    void completeWorkflow_allSteps_success() throws Exception {
        when(mockHNS.getMedicalHistory(any())).thenReturn(mockHistory);
        when(mockHNS.getMedicalPrescription(any(), anyString())).thenReturn(mockPrescription);
        when(mockHNS.sendHistoryAndPrescription(any(), any(), anyString(), any()))
                .thenReturn(mockPrescription);

        // 1. Init revision
        terminal.initRevision(validCip, "Hypertension");

        // 2. Add medical assessment
        terminal.enterMedicalAssessmentInHistory("Patient reports feeling better");

        // 3. Start prescription edition
        terminal.initMedicalPrescriptionEdition();

        // 4. Add a medicine
        String[] instruc1 = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with water"};
        terminal.enterMedicineWithGuidelines(validProduct1, instruc1);

        // 5. Add another medicine
        String[] instruc2 = {"AFTERDINNER", "30", "2", "2", "DAY"};
        terminal.enterMedicineWithGuidelines(validProduct2, instruc2);

        // 6. Modify dose of first medicine
        terminal.modifyDoseInLine(validProduct1, 2.0f);

        // 7. Remove second medicine
        terminal.removeLine(validProduct2);

        // 8. Set ending date (con buffer de tiempo adicional)
        Date futureDate = new Date(System.currentTimeMillis() + 86400000L * 30 + 5000L);
        terminal.enterTreatmentEndingDate(futureDate);

        // 9. Finish edition
        terminal.finishMedicalPrescriptionEdition();

        // 10. Stamp signature
        terminal.stampeeSignature();

        // 11. Send to HNS
        terminal.sendHistoryAndPrescription();

        verify(mockHNS).getMedicalHistory(validCip);
        verify(mockHNS).getMedicalPrescription(validCip, "Hypertension");
        verify(mockHNS).sendHistoryAndPrescription(any(), any(), anyString(), any());

        Assertions.assertEquals(1, terminal.getMedicalPrescription().getLines().size());
        Assertions.assertNotNull(terminal.getMedicalPrescription().geteSign());
    }
}