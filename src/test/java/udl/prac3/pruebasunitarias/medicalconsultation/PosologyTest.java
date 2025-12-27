package udl.prac3.pruebasunitarias.medicalconsultation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;

public class PosologyTest {
    private Posology validPosology;

    @BeforeEach
    void setUp() {
        validPosology = new Posology(1, 2, FqUnit.DAY);
    }

    private void assertInvalidPosology(Runnable action) {
        Assertions.assertThrows(IllegalArgumentException.class, action::run);
    }

    @Test
    void testConstructorAtrInvalid() {
        assertInvalidPosology(() -> new Posology(-1, 1, FqUnit.DAY));
        assertInvalidPosology(() -> new Posology(1, -1, FqUnit.DAY));
        assertInvalidPosology(() -> new Posology(1, 1, null));
    }

    @Test
    void testSetDoseInvalid() {
        assertInvalidPosology(() -> validPosology.setDose(0));
        assertInvalidPosology(() -> validPosology.setDose(-5));
    }

    @Test
    void testSetFreqInvalid() {
        assertInvalidPosology(() -> validPosology.setFreq(0));
        assertInvalidPosology(() -> validPosology.setFreq(-5));
    }

    @Test
    void testSetFreqUnitInvalid() {
        assertInvalidPosology(() -> validPosology.setFreqUnit(null));
    }

    @Test
    void setDoseTest() {
        validPosology.setDose(2);
        Assertions.assertEquals(new Posology(2, 2, FqUnit.DAY), validPosology);
    }

    @Test
    void setFreqTest() {
        validPosology.setFreq(3);
        Assertions.assertEquals(new Posology(1, 3, FqUnit.DAY), validPosology);
    }

    @Test
    void setFreqUnitTest() {
        validPosology.setFreqUnit(FqUnit.HOUR);
        Assertions.assertEquals(new Posology(1, 2, FqUnit.HOUR), validPosology);
    }

    @Test
    void gettersTest() {
        Assertions.assertEquals(1, validPosology.getDose());
        Assertions.assertEquals(2, validPosology.getFreq());
        Assertions.assertEquals(FqUnit.DAY, validPosology.getFreqUnit());
    }

    @Test
    void toStringTest() {
        String exp = "Posology{dose=1.0, freq=2.0, unit=DAY}";
        String str = validPosology.toString();
        Assertions.assertEquals(exp, str);
    }

}
