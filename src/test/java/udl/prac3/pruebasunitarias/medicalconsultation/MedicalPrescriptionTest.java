package udl.prac3.pruebasunitarias.medicalconsultation;

import org.junit.jupiter.api.*;

import udl.prac3.pruebasunitarias.data.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import udl.prac3.pruebasunitarias.medicalconsultation.exceptions.*;

import java.util.*;

class MedicalPrescriptionTest {

    private HealthCardID validCip;
    private ProductID validProduct1;
    private ProductID validProduct2;
    private ProductID validProduct3;
    private MedicalPrescription validPrescription;

    private static int testIdCounter = 0;
    private static int testProductCounter = 0;

    @BeforeEach
    void setUp() throws Exception {
        HealthCardID.clearRegistry();
        ProductID.clearRegistry();
        validCip = new HealthCardID(String.format("TEST%012d", testIdCounter++));
        validProduct1 = new ProductID(String.format("%012d", testProductCounter++));
        validProduct2 = new ProductID(String.format("%012d", testProductCounter++));
        validProduct3 = new ProductID(String.format("%012d", testProductCounter++));

        validPrescription = new MedicalPrescription(validCip, 12345, "Hypertension");
    }

    @Test
    @DisplayName("Constructor rejects null HealthCardID")
    void constructor_nullCip_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> new MedicalPrescription(null, 12345, "Hypertension"));
    }

    @Test
    @DisplayName("Constructor rejects null illness")
    void constructor_nullIllness_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> new MedicalPrescription(validCip, 12345, null));
    }

    @Test
    @DisplayName("Constructor rejects blank illness")
    void constructor_blankIllness_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> new MedicalPrescription(validCip, 12345, "   "));
    }

    @Test
    @DisplayName("Constructor rejects empty illness")
    void constructor_emptyIllness_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> new MedicalPrescription(validCip, 12345, ""));
    }

    @Test
    @DisplayName("Constructor rejects zero membership number")
    void constructor_zeroMembership_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> new MedicalPrescription(validCip, 0, "Hypertension"));
    }

    @Test
    @DisplayName("Constructor rejects negative membership number")
    void constructor_negativeMembership_throwsException() {
        Assertions.assertThrows(IncorrectParametersException.class,
                () -> new MedicalPrescription(validCip, -1, "Hypertension"));
    }

    @Test
    @DisplayName("Constructor creates MedicalPrescription with valid parameters")
    void constructor_validParameters_createsObject() throws IncorrectParametersException {
        MedicalPrescription prescription = new MedicalPrescription(validCip, 12345, "Diabetes");

        Assertions.assertNotNull(prescription);
        Assertions.assertEquals(validCip, prescription.getCip());
        Assertions.assertEquals(12345, prescription.getMembShipNumb());
        Assertions.assertEquals("Diabetes", prescription.getIllness());
        Assertions.assertTrue(prescription.getLines().isEmpty());
    }

    @Test
    @DisplayName("Constructor initializes lines as empty map")
    void constructor_initializesLinesEmpty() {
        Assertions.assertTrue(validPrescription.getLines().isEmpty());
        Assertions.assertEquals(0, validPrescription.getLines().size());
    }

    @Test
    @DisplayName("addLine rejects null ProductID")
    void addLine_nullProductID_throwsException() {
        String[] validInstruc = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with water"};

        Assertions.assertThrows(IncorrectTakingGuidelinesException.class,
                () -> validPrescription.addLine(null, validInstruc));
    }

    @Test
    @DisplayName("addLine rejects null instructions array")
    void addLine_nullInstructions_throwsException() {
        Assertions.assertThrows(IncorrectTakingGuidelinesException.class,
                () -> validPrescription.addLine(validProduct1, null));
    }

    @Test
    @DisplayName("addLine rejects instructions array with less than 5 elements")
    void addLine_shortInstructions_throwsException() {
        String[] shortInstruc = {"BEFORELUNCH", "15", "1", "1"}; // Missing FqUnit

        Assertions.assertThrows(IncorrectTakingGuidelinesException.class,
                () -> validPrescription.addLine(validProduct1, shortInstruc));
    }

    @Test
    @DisplayName("addLine rejects invalid dayMoment")
    void addLine_invalidDayMoment_throwsException() {
        String[] invalidInstruc = {"INVALID_MOMENT", "15", "1", "1", "DAY"};

        Assertions.assertThrows(IncorrectTakingGuidelinesException.class,
                () -> validPrescription.addLine(validProduct1, invalidInstruc));
    }

    @Test
    @DisplayName("addLine rejects invalid duration format")
    void addLine_invalidDuration_throwsException() {
        String[] invalidInstruc = {"BEFORELUNCH", "not_a_number", "1", "1", "DAY"};

        Assertions.assertThrows(IncorrectTakingGuidelinesException.class,
                () -> validPrescription.addLine(validProduct1, invalidInstruc));
    }

    @Test
    @DisplayName("addLine rejects invalid dose format")
    void addLine_invalidDose_throwsException() {
        String[] invalidInstruc = {"BEFORELUNCH", "15", "invalid", "1", "DAY"};

        Assertions.assertThrows(IncorrectTakingGuidelinesException.class,
                () -> validPrescription.addLine(validProduct1, invalidInstruc));
    }

    @Test
    @DisplayName("addLine rejects invalid frequency format")
    void addLine_invalidFrequency_throwsException() {
        String[] invalidInstruc = {"BEFORELUNCH", "15", "1", "xyz", "DAY"};

        Assertions.assertThrows(IncorrectTakingGuidelinesException.class,
                () -> validPrescription.addLine(validProduct1, invalidInstruc));
    }

    @Test
    @DisplayName("addLine rejects invalid FqUnit")
    void addLine_invalidFqUnit_throwsException() {
        String[] invalidInstruc = {"BEFORELUNCH", "15", "1", "1", "INVALID_UNIT"};

        Assertions.assertThrows(IncorrectTakingGuidelinesException.class,
                () -> validPrescription.addLine(validProduct1, invalidInstruc));
    }

    @Test
    @DisplayName("addLine rejects duplicate ProductID")
    void addLine_duplicateProduct_throwsException() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};

        validPrescription.addLine(validProduct1, instruc);

        Assertions.assertThrows(ProductAlreadyInPrescriptionException.class,
                () -> validPrescription.addLine(validProduct1, instruc));
    }

    @Test
    @DisplayName("addLine adds prescription line without instructions")
    void addLine_withoutInstructions_success() throws Exception {
        String[] instruc = {"AFTERDINNER", "30", "2", "3", "HOUR"};

        validPrescription.addLine(validProduct1, instruc);

        Assertions.assertEquals(1, validPrescription.getLines().size());
        Assertions.assertTrue(validPrescription.getLines().containsKey(validProduct1));
    }

    @Test
    @DisplayName("addLine adds prescription line with instructions")
    void addLine_withInstructions_success() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with abundant water"};

        validPrescription.addLine(validProduct1, instruc);

        Map<ProductID, TakingGuideline> lines = validPrescription.getLines();
        Assertions.assertEquals(1, lines.size());
        Assertions.assertTrue(lines.containsKey(validProduct1));

        TakingGuideline guideline = lines.get(validProduct1);
        Assertions.assertEquals(dayMoment.BEFORELUNCH, guideline.getdMoment());
        Assertions.assertEquals(15, guideline.getDuration());
        Assertions.assertEquals(1, guideline.getPosology().getDose());
        Assertions.assertEquals(1, guideline.getPosology().getFreq());
        Assertions.assertEquals(FqUnit.DAY, guideline.getPosology().getFreqUnit());
        Assertions.assertEquals("Take with abundant water", guideline.getInstructions());
    }

    @Test
    @DisplayName("addLine adds multiple prescription lines correctly")
    void addLine_multipleProducts_success() throws Exception {
        String[] instruc1 = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        String[] instruc2 = {"AFTERDINNER", "30", "2", "2", "DAY"};
        String[] instruc3 = {"BEFOREBREAKFAST", "7", "0.5", "1", "DAY"};

        validPrescription.addLine(validProduct1, instruc1);
        validPrescription.addLine(validProduct2, instruc2);
        validPrescription.addLine(validProduct3, instruc3);

        Assertions.assertEquals(3, validPrescription.getLines().size());
    }

    @Test
    @DisplayName("modifyDoseInLine rejects non-existent ProductID")
    void modifyDoseInLine_productNotFound_throwsException() {
        Assertions.assertThrows(ProductNotInPrescriptionException.class,
                () -> validPrescription.modifyDoseInLine(validProduct1, 5.0f));
    }

    @Test
    @DisplayName("modifyDoseInLine updates dose correctly")
    void modifyDoseInLine_validProduct_updatesSuccessfully() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        validPrescription.addLine(validProduct1, instruc);

        validPrescription.modifyDoseInLine(validProduct1, 3.0f);

        TakingGuideline guideline = validPrescription.getLines().get(validProduct1);
        Assertions.assertEquals(3.0f, guideline.getPosology().getDose());
    }

    @Test
    @DisplayName("modifyDoseInLine preserves other guideline fields")
    void modifyDoseInLine_preservesOtherFields() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "2", "DAY", "Important note"};
        validPrescription.addLine(validProduct1, instruc);

        validPrescription.modifyDoseInLine(validProduct1, 5.0f);

        TakingGuideline guideline = validPrescription.getLines().get(validProduct1);
        Assertions.assertEquals(dayMoment.BEFORELUNCH, guideline.getdMoment());
        Assertions.assertEquals(15, guideline.getDuration());
        Assertions.assertEquals(5.0f, guideline.getPosology().getDose()); // Changed
        Assertions.assertEquals(2, guideline.getPosology().getFreq()); // Unchanged
        Assertions.assertEquals(FqUnit.DAY, guideline.getPosology().getFreqUnit()); // Unchanged
        Assertions.assertEquals("Important note", guideline.getInstructions()); // Unchanged
    }

    @Test
    @DisplayName("modifyDoseInLine rejects invalid dose (zero)")
    void modifyDoseInLine_zeroDose_throwsException() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        validPrescription.addLine(validProduct1, instruc);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> validPrescription.modifyDoseInLine(validProduct1, 0));
    }

    @Test
    @DisplayName("modifyDoseInLine rejects invalid dose (negative)")
    void modifyDoseInLine_negativeDose_throwsException() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        validPrescription.addLine(validProduct1, instruc);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> validPrescription.modifyDoseInLine(validProduct1, -1));
    }

    @Test
    @DisplayName("removeLine rejects non-existent ProductID")
    void removeLine_productNotFound_throwsException() {
        Assertions.assertThrows(ProductNotInPrescriptionException.class,
                () -> validPrescription.removeLine(validProduct1));
    }

    @Test
    @DisplayName("removeLine removes prescription line correctly")
    void removeLine_validProduct_removesSuccessfully() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        validPrescription.addLine(validProduct1, instruc);

        Assertions.assertEquals(1, validPrescription.getLines().size());

        validPrescription.removeLine(validProduct1);

        Assertions.assertEquals(0, validPrescription.getLines().size());
        Assertions.assertFalse(validPrescription.getLines().containsKey(validProduct1));
    }

    @Test
    @DisplayName("removeLine removes only specified product")
    void removeLine_preservesOtherProducts() throws Exception {
        String[] instruc1 = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        String[] instruc2 = {"AFTERDINNER", "30", "2", "2", "DAY"};

        validPrescription.addLine(validProduct1, instruc1);
        validPrescription.addLine(validProduct2, instruc2);

        validPrescription.removeLine(validProduct1);

        Assertions.assertEquals(1, validPrescription.getLines().size());
        Assertions.assertFalse(validPrescription.getLines().containsKey(validProduct1));
        Assertions.assertTrue(validPrescription.getLines().containsKey(validProduct2));
    }

    @Test
    @DisplayName("isComplete returns false when prescription is incomplete (no dates, no signature, no lines)")
    void isComplete_allMissing_returnsFalse() {
        Assertions.assertFalse(validPrescription.isComplete());
    }

    @Test
    @DisplayName("isComplete returns false when only lines exist")
    void isComplete_onlyLines_returnsFalse() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        validPrescription.addLine(validProduct1, instruc);

        Assertions.assertFalse(validPrescription.isComplete());
    }

    @Test
    @DisplayName("isComplete returns false when dates and lines exist but no signature")
    void isComplete_noSignature_returnsFalse() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        validPrescription.addLine(validProduct1, instruc);
        validPrescription.setPrescDate(new Date());
        validPrescription.setEndDate(new Date(System.currentTimeMillis() + 86400000));

        Assertions.assertFalse(validPrescription.isComplete());
    }

    @Test
    @DisplayName("isComplete returns false when all fields exist but no lines")
    void isComplete_noLines_returnsFalse() throws Exception {
        validPrescription.setPrescDate(new Date());
        validPrescription.setEndDate(new Date(System.currentTimeMillis() + 86400000));
        validPrescription.seteSign(new DigitalSignature(new byte[]{1, 2, 3}));

        Assertions.assertFalse(validPrescription.isComplete());
    }

    @Test
    @DisplayName("isComplete returns true when all required fields are present")
    void isComplete_allFieldsPresent_returnsTrue() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        validPrescription.addLine(validProduct1, instruc);
        validPrescription.setPrescDate(new Date());
        validPrescription.setEndDate(new Date(System.currentTimeMillis() + 86400000));
        validPrescription.seteSign(new DigitalSignature(new byte[]{1, 2, 3}));

        Assertions.assertTrue(validPrescription.isComplete());
    }

    @Test
    @DisplayName("setPrescDate rejects null date")
    void setPrescDate_nullDate_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> validPrescription.setPrescDate(null));
    }

    @Test
    @DisplayName("setEndDate rejects null date")
    void setEndDate_nullDate_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> validPrescription.setEndDate(null));
    }

    @Test
    @DisplayName("seteSign rejects null signature")
    void seteSign_nullSignature_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> validPrescription.seteSign(null));
    }

    @Test
    @DisplayName("setPrescCode rejects null code")
    void setPrescCode_nullCode_throwsException() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> validPrescription.setPrescCode(null));
    }

    @Test
    @DisplayName("setPrescCode can only be set once")
    void setPrescCode_canOnlyBeSetOnce() throws Exception {
        ePrescripCode code1 = new ePrescripCode("A1B2C3D4E5F6G7H8");
        ePrescripCode code2 = new ePrescripCode("B2C3D4E5F6G7H8I9");

        validPrescription.setPrescCode(code1);

        Assertions.assertThrows(IllegalStateException.class,
                () -> validPrescription.setPrescCode(code2));
    }

    @Test
    @DisplayName("getLines returns unmodifiable map")
    void getLines_returnsUnmodifiableMap() throws Exception {
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        validPrescription.addLine(validProduct1, instruc);

        Map<ProductID, TakingGuideline> lines = validPrescription.getLines();

        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> lines.put(validProduct2, null));
    }

    @Test
    @DisplayName("Getters return correct values")
    void getters_returnCorrectValues() {
        Assertions.assertEquals(validCip, validPrescription.getCip());
        Assertions.assertEquals(12345, validPrescription.getMembShipNumb());
        Assertions.assertEquals("Hypertension", validPrescription.getIllness());
    }

    @Test
    @DisplayName("Complete workflow: add lines, modify dose, remove line, complete prescription")
    void completeWorkflow_success() throws Exception {
        // Add multiple lines
        String[] instruc1 = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with water"};
        String[] instruc2 = {"AFTERDINNER", "30", "2", "2", "DAY", "After meal"};
        String[] instruc3 = {"BEFOREBREAKFAST", "7", "0.5", "1", "DAY"};

        validPrescription.addLine(validProduct1, instruc1);
        validPrescription.addLine(validProduct2, instruc2);
        validPrescription.addLine(validProduct3, instruc3);

        Assertions.assertEquals(3, validPrescription.getLines().size());

        // Modify dose
        validPrescription.modifyDoseInLine(validProduct2, 3.0f);
        Assertions.assertEquals(3.0f,
                validPrescription.getLines().get(validProduct2).getPosology().getDose());

        // Remove a line
        validPrescription.removeLine(validProduct3);
        Assertions.assertEquals(2, validPrescription.getLines().size());

        // Complete prescription
        validPrescription.setPrescDate(new Date());
        validPrescription.setEndDate(new Date(System.currentTimeMillis() + 86400000));
        validPrescription.seteSign(new DigitalSignature(new byte[]{1, 2, 3, 4, 5}));
        validPrescription.setPrescCode(new ePrescripCode("CODE1234567890AB"));

        Assertions.assertTrue(validPrescription.isComplete());
        Assertions.assertNotNull(validPrescription.getPrescCode());
        Assertions.assertNotNull(validPrescription.getPrescDate());
        Assertions.assertNotNull(validPrescription.getEndDate());
        Assertions.assertNotNull(validPrescription.geteSign());
    }
}