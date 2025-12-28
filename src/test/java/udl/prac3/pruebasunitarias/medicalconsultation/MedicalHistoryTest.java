package udl.prac3.pruebasunitarias.medicalconsultation;

import org.junit.jupiter.api.*;

import udl.prac3.pruebasunitarias.data.HealthCardID;
import udl.prac3.pruebasunitarias.data.exceptions.HealthCardIDException;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.IncorrectParametersException;

class MedicalHistoryTest {

    private HealthCardID validCip;
    private MedicalHistory validHistory;

    @BeforeEach
    void setUp() throws HealthCardIDException, IncorrectParametersException {
        validCip = new HealthCardID("ABCD1234EFGH5678");
        validHistory = new MedicalHistory(validCip, 12345);
    }

    @Test
    @DisplayName("Constructor rejects null HealthCardID")
    void constructor_nullCip_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> new MedicalHistory(null, 12345));
    }

    @Test
    @DisplayName("Constructor rejects zero membership number")
    void constructor_zeroMembershipNumber_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> new MedicalHistory(validCip, 0));
    }

    @Test
    @DisplayName("Constructor rejects negative membership number")
    void constructor_negativeMembershipNumber_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> new MedicalHistory(validCip, -1));
    }

    @Test
    @DisplayName("Constructor creates MedicalHistory with valid parameters")
    void constructor_validParameters_createsObject() throws IncorrectParametersException {
        MedicalHistory history = new MedicalHistory(validCip, 12345);

        Assertions.assertNotNull(history);
        Assertions.assertEquals(validCip, history.getCip());
        Assertions.assertEquals(12345, history.getMembShipNumb());
        Assertions.assertEquals("", history.getHistory());
    }

    @Test
    @DisplayName("Constructor initializes history as empty string")
    void constructor_initializesHistoryEmpty() {
        Assertions.assertEquals("", validHistory.getHistory());
    }

    @Test
    @DisplayName("addMedicalHistoryAnnotations ignores null annotation")
    void addAnnotations_nullAnnotation_ignored() {
        validHistory.addMedicalHistoryAnnotations(null);
        Assertions.assertEquals("", validHistory.getHistory());
    }

    @Test
    @DisplayName("addMedicalHistoryAnnotations ignores empty annotation")
    void addAnnotations_emptyAnnotation_ignored() {
        validHistory.addMedicalHistoryAnnotations("");
        Assertions.assertEquals("", validHistory.getHistory());
    }

    @Test
    @DisplayName("addMedicalHistoryAnnotations ignores blank annotation")
    void addAnnotations_blankAnnotation_ignored() {
        validHistory.addMedicalHistoryAnnotations("   ");
        Assertions.assertEquals("", validHistory.getHistory());
    }

    @Test
    @DisplayName("addMedicalHistoryAnnotations adds single annotation correctly")
    void addAnnotations_singleAnnotation_added() {
        validHistory.addMedicalHistoryAnnotations("Patient reports headache");
        Assertions.assertEquals("Patient reports headache", validHistory.getHistory());
    }

    @Test
    @DisplayName("addMedicalHistoryAnnotations adds multiple annotations with line separator")
    void addAnnotations_multipleAnnotations_separatedByNewline() {
        validHistory.addMedicalHistoryAnnotations("First annotation");
        validHistory.addMedicalHistoryAnnotations("Second annotation");

        String expected = "First annotation" + System.lineSeparator() + "Second annotation";
        Assertions.assertEquals(expected, validHistory.getHistory());
    }

    @Test
    @DisplayName("addMedicalHistoryAnnotations ignores blank between valid annotations")
    void addAnnotations_blankBetweenValid_ignored() {
        validHistory.addMedicalHistoryAnnotations("First annotation");
        validHistory.addMedicalHistoryAnnotations("   "); // Should be ignored
        validHistory.addMedicalHistoryAnnotations("Second annotation");

        String expected = "First annotation" + System.lineSeparator() + "Second annotation";
        Assertions.assertEquals(expected, validHistory.getHistory());
    }

    @Test
    @DisplayName("addMedicalHistoryAnnotations handles three annotations correctly")
    void addAnnotations_threeAnnotations_allAdded() {
        validHistory.addMedicalHistoryAnnotations("Annotation 1");
        validHistory.addMedicalHistoryAnnotations("Annotation 2");
        validHistory.addMedicalHistoryAnnotations("Annotation 3");

        String expected = "Annotation 1" + System.lineSeparator() +
                "Annotation 2" + System.lineSeparator() +
                "Annotation 3";
        Assertions.assertEquals(expected, validHistory.getHistory());
    }

    @Test
    @DisplayName("setNewDoctor rejects zero membership number")
    void setNewDoctor_zeroMembership_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> validHistory.setNewDoctor(0));
    }

    @Test
    @DisplayName("setNewDoctor rejects negative membership number")
    void setNewDoctor_negativeMembership_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> validHistory.setNewDoctor(-1));
    }

    @Test
    @DisplayName("setNewDoctor updates membership number correctly")
    void setNewDoctor_validMembership_updated() throws IncorrectParametersException {
        validHistory.setNewDoctor(99999);
        Assertions.assertEquals(99999, validHistory.getMembShipNumb());
    }

    @Test
    @DisplayName("setNewDoctor preserves CIP when updating doctor")
    void setNewDoctor_preservesCip() throws IncorrectParametersException {
        HealthCardID originalCip = validHistory.getCip();
        validHistory.setNewDoctor(99999);
        Assertions.assertEquals(originalCip, validHistory.getCip());
    }

    @Test
    @DisplayName("setNewDoctor preserves history when updating doctor")
    void setNewDoctor_preservesHistory() throws IncorrectParametersException {
        validHistory.addMedicalHistoryAnnotations("Important medical note");
        String originalHistory = validHistory.getHistory();

        validHistory.setNewDoctor(99999);

        Assertions.assertEquals(originalHistory, validHistory.getHistory());
    }

    @Test
    @DisplayName("getCip returns correct HealthCardID")
    void getCip_returnsCorrectValue() {
        Assertions.assertEquals(validCip, validHistory.getCip());
    }

    @Test
    @DisplayName("getMembShipNumb returns correct membership number")
    void getMembShipNumb_returnsCorrectValue() {
        Assertions.assertEquals(12345, validHistory.getMembShipNumb());
    }

    @Test
    @DisplayName("getHistory returns empty string initially")
    void getHistory_initiallyEmpty() {
        Assertions.assertEquals("", validHistory.getHistory());
    }

    @Test
    @DisplayName("getHistory returns updated history after annotations")
    void getHistory_returnsUpdatedValue() {
        validHistory.addMedicalHistoryAnnotations("Test annotation");
        Assertions.assertEquals("Test annotation", validHistory.getHistory());
    }

    @Test
    @DisplayName("Complete workflow: create history, add annotations, change doctor")
    void completeWorkflow_success() throws IncorrectParametersException {
        // Create new history
        MedicalHistory history = new MedicalHistory(validCip, 11111);

        // Add annotations
        history.addMedicalHistoryAnnotations("Initial consultation");
        history.addMedicalHistoryAnnotations("Follow-up visit");

        // Change doctor
        history.setNewDoctor(22222);

        // Add more annotations
        history.addMedicalHistoryAnnotations("New doctor's notes");

        // Verify state
        Assertions.assertEquals(validCip, history.getCip());
        Assertions.assertEquals(22222, history.getMembShipNumb());

        String expectedHistory = "Initial consultation" + System.lineSeparator() +
                "Follow-up visit" + System.lineSeparator() +
                "New doctor's notes";
        Assertions.assertEquals(expectedHistory, history.getHistory());
    }
}