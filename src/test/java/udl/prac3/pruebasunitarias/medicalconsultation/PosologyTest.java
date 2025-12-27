package udl.prac3.pruebasunitarias.medicalconsultation;

import org.junit.jupiter.api.*;

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
    @DisplayName("Constructor rejects zero or negative dose")
    void testConstructorInvalidDose() {
        assertInvalidPosology(() -> new Posology(0, 1, FqUnit.DAY));
        assertInvalidPosology(() -> new Posology(-1, 1, FqUnit.DAY));
    }

    @Test
    @DisplayName("Constructor rejects zero or negative frequency")
    void testConstructorInvalidFrequency() {
        assertInvalidPosology(() -> new Posology(1, 0, FqUnit.DAY));
        assertInvalidPosology(() -> new Posology(1, -1, FqUnit.DAY));
    }

    @Test
    @DisplayName("Constructor rejects null frequency unit")
    void testConstructorNullFrequencyUnit() {
        assertInvalidPosology(() -> new Posology(1, 1, null));
    }

    @Test
    @DisplayName("setDose rejects zero or negative dose")
    void testSetDoseInvalid() {
        assertInvalidPosology(() -> validPosology.setDose(0));
        assertInvalidPosology(() -> validPosology.setDose(-5));
    }

    @Test
    @DisplayName("setFreq rejects zero or negative frequency")
    void testSetFreqInvalid() {
        assertInvalidPosology(() -> validPosology.setFreq(0));
        assertInvalidPosology(() -> validPosology.setFreq(-5));
    }

    @Test
    @DisplayName("setFreqUnit rejects null frequency unit")
    void testSetFreqUnitInvalid() {
        assertInvalidPosology(() -> validPosology.setFreqUnit(null));
    }

    @Test
    @DisplayName("setDose updates dose correctly")
    void setDoseTest() {
        validPosology.setDose(2);
        Assertions.assertEquals(new Posology(2, 2, FqUnit.DAY), validPosology);
    }

    @Test
    @DisplayName("setFreq updates frequency correctly")
    void setFreqTest() {
        validPosology.setFreq(3);
        Assertions.assertEquals(new Posology(1, 3, FqUnit.DAY), validPosology);
    }

    @Test
    @DisplayName("setFreqUnit updates frequency unit correctly")
    void setFreqUnitTest() {
        validPosology.setFreqUnit(FqUnit.HOUR);
        Assertions.assertEquals(new Posology(1, 2, FqUnit.HOUR), validPosology);
    }


    @Test
    @DisplayName("Getters return correct values")
    void gettersTest() {
        Assertions.assertEquals(1, validPosology.getDose());
        Assertions.assertEquals(2, validPosology.getFreq());
        Assertions.assertEquals(FqUnit.DAY, validPosology.getFreqUnit());
    }

    @Test
    @DisplayName("toString returns expected string representation")
    void toStringTest() {
        String expected = "Posology{dose=1.0, freq=2.0, unit=DAY}";
        Assertions.assertEquals(expected, validPosology.toString());
    }
}