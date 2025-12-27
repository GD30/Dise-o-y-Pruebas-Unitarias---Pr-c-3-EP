package udl.prac3.pruebasunitarias.medicalconsultation;

import org.junit.jupiter.api.*;

public class TakingGuidelineTest {
    private TakingGuideline validGuideLine;

    @BeforeEach
    void setUp(){
        validGuideLine = new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 2, 3, FqUnit.DAY, "ABC");
    }

    void assertInvalidGuideLine(Runnable action) {
        Assertions.assertThrows(IllegalArgumentException.class, action::run);
    }

    @Test
    @DisplayName("Constructor rejects null day moment")
    void testConstructorNullDayMoment() {
        assertInvalidGuideLine(() -> new TakingGuideline(null, 1, 2, 3, FqUnit.DAY, "ABC"));
    }

    @Test
    @DisplayName("Constructor rejects zero or negative duration")
    void testConstructorInvalidDuration() {
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 0, 2, 3, FqUnit.DAY, "ABC"));
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, -1, 2, 3, FqUnit.DAY, "ABC"));
    }

    @Test
    @DisplayName("Constructor rejects invalid posology parameters (zero or negative dose)")
    void testConstructorInvalidDose() {
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 0, 3, FqUnit.DAY, "ABC"));
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, -1, 3, FqUnit.DAY, "ABC"));
    }

    @Test
    @DisplayName("Constructor rejects invalid posology parameters (zero or negative frequency)")
    void testConstructorInvalidFrequency() {
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 2, 0, FqUnit.DAY, "ABC"));
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 2, -1, FqUnit.DAY, "ABC"));
    }

    @Test
    @DisplayName("Constructor rejects null frequency unit")
    void testConstructorNullFrequencyUnit() {
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 2, 3, null, "ABC"));
    }

    @Test
    @DisplayName("setdMoment rejects null day moment")
    void testSetdMomentInvalid() {
        assertInvalidGuideLine(() -> validGuideLine.setdMoment(null));
    }

    @Test
    @DisplayName("setDuration rejects zero or negative duration")
    void testSetDurationInvalid() {
        assertInvalidGuideLine(() -> validGuideLine.setDuration(0));
        assertInvalidGuideLine(() -> validGuideLine.setDuration(-1));
    }

    @Test
    @DisplayName("setPosology rejects null posology")
    void testSetPosologyNull() {
        assertInvalidGuideLine(() -> validGuideLine.setPosology(null));
    }

    @Test
    @DisplayName("setPosology rejects posology with invalid dose")
    void testSetPosologyInvalidDose() {
        assertInvalidGuideLine(() -> validGuideLine.setPosology(new Posology(0, 2, FqUnit.DAY)));
        assertInvalidGuideLine(() -> validGuideLine.setPosology(new Posology(-1, 2, FqUnit.DAY)));
    }

    @Test
    @DisplayName("setPosology rejects posology with invalid frequency")
    void testSetPosologyInvalidFrequency() {
        assertInvalidGuideLine(() -> validGuideLine.setPosology(new Posology(1, 0, FqUnit.DAY)));
        assertInvalidGuideLine(() -> validGuideLine.setPosology(new Posology(1, -1, FqUnit.DAY)));
    }

    @Test
    @DisplayName("setPosology rejects posology with null frequency unit")
    void testSetPosologyNullFrequencyUnit() {
        assertInvalidGuideLine(() -> validGuideLine.setPosology(new Posology(1, 2, null)));
    }

    @Test
    @DisplayName("setInstructions updates instructions correctly")
    void testSetInstructionsValid() {
        validGuideLine.setInstructions("Take with water");
        Assertions.assertEquals(
                new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 2, 3, FqUnit.DAY, "Take with water"),
                validGuideLine
        );
    }

    @Test
    @DisplayName("Getters return correct values")
    void gettersTest() {
        Assertions.assertEquals(dayMoment.AFTERBREAKFAST, validGuideLine.getdMoment());
        Assertions.assertEquals(1, validGuideLine.getDuration());
        Assertions.assertEquals("ABC", validGuideLine.getInstructions());
        Assertions.assertEquals(new Posology(2, 3, FqUnit.DAY), validGuideLine.getPosology());
    }

    @Test
    @DisplayName("toString returns expected string representation")
    void toStringTest() {
        String expected = "TakingGuideline{moment=AFTERBREAKFAST, duration=1.0, " +
                "posology=Posology{dose=2.0, freq=3.0, unit=DAY}, instructions='ABC'}";
        Assertions.assertEquals(expected, validGuideLine.toString());
    }
}