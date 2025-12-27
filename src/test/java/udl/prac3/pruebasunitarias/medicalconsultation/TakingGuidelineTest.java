package udl.prac3.pruebasunitarias.medicalconsultation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import udl.prac3.pruebasunitarias.medicalconsultation.TakingGuideline;

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
    void testConstructorAtrInvalid() {
        assertInvalidGuideLine(() -> new TakingGuideline(null, 1, 2, 3, FqUnit.DAY, "ABC"));
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 0, 2, 3, FqUnit.DAY, "ABC"));
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 0, 3, FqUnit.DAY, "ABC"));
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 2, 0, FqUnit.DAY, "ABC"));
        assertInvalidGuideLine(() -> new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 2, 3, null, "ABC"));
    }

    @Test
    void testSetdMomentInvalid() {
        assertInvalidGuideLine(() -> validGuideLine.setdMoment(null));
    }

    @Test
    void testSetDurationInvalid() {
        assertInvalidGuideLine(() -> validGuideLine.setDuration(0));
        assertInvalidGuideLine(() -> validGuideLine.setDuration(-1));
    }

    @Test
    void testSetPosologyInvalid() {
        assertInvalidGuideLine(() -> validGuideLine.setPosology(null));
        assertInvalidGuideLine(() -> validGuideLine.setPosology(new Posology(0, 2, FqUnit.DAY)));
        assertInvalidGuideLine(() -> validGuideLine.setPosology(new Posology(1, 0, FqUnit.DAY)));
        assertInvalidGuideLine(() -> validGuideLine.setPosology(new Posology(1, 2, null)));
    }

    @Test
    void testSetInstructionsInvalid() {
        validGuideLine.setInstructions("123");
        Assertions.assertEquals(new TakingGuideline(dayMoment.AFTERBREAKFAST, 1, 2, 3, FqUnit.DAY, "123"), validGuideLine);
    }

    @Test
    void gettersTest() {
        Assertions.assertEquals(dayMoment.AFTERBREAKFAST, validGuideLine.getdMoment());
        Assertions.assertEquals(1, validGuideLine.getDuration());
        Assertions.assertEquals("ABC", validGuideLine.getInstructions());
        Assertions.assertEquals(new Posology(2, 3, FqUnit.DAY), validGuideLine.getPosology());
    }

    @Test
    void toStringTest() {
        String exp = "TakingGuideline{moment=AFTERBREAKFAST, duration=1.0, posology=Posology{dose=2.0, freq=3.0, unit=DAY}, instructions='ABC'}";
        String str = validGuideLine.toString();
        Assertions.assertEquals(exp, str);
    }
}
