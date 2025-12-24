package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import udl.prac3.pruebasunitarias.data.exceptions.*;

class ePrescripCodeTest {

    @Test
    void constructor_nullCode_throwsException() {
        assertThrows(ePrescripCodeException.class,
                () -> new ePrescripCode(null));
    }

    @Test
    void constructor_invalidFormat_throwsException() {
        assertThrows(ePrescripCodeException.class,
                () -> new ePrescripCode("123"));
    }

    @Test
    void constructor_validCode_createsObject() throws ePrescripCodeException {
        ePrescripCode code = new ePrescripCode("A1B2C3D4E5F6G7H8");
        assertNotNull(code);
    }

    @Test
    void equals_sameCode_returnsTrue() throws ePrescripCodeException {
        ePrescripCode c1 = new ePrescripCode("A1B2C3D4E5F6G7H8");
        ePrescripCode c2 = new ePrescripCode("A1B2C3D4E5F6G7H8");

        assertEquals(c1, c2);
    }
}
