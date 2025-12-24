package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import java.lang.reflect.Field;
import java.util.Set;

class ePrescripCodeTest {

    @BeforeEach
    void resetExistingCodes() throws Exception {
        Field field = ePrescripCode.class.getDeclaredField("existingCodes");
        field.setAccessible(true);
        ((Set<String>) field.get(null)).clear();
    }

    @Test
    @DisplayName("ePrescriptionCode rejects null code")
    void constructor_nullCode_throwsException() {
        assertThrows(ePrescripCodeException.class,
                () -> new ePrescripCode(null));
    }

    @Test
    @DisplayName("ePrescriptionCode rejects invalid code format")
    void constructor_invalidFormat_throwsException() {
        assertThrows(ePrescripCodeException.class,
                () -> new ePrescripCode("123"));
    }

    @Test
    @DisplayName("ePrescriptionCode accepts valid code format")
    void constructor_validCode_createsObject() throws ePrescripCodeException {
        ePrescripCode code = new ePrescripCode("A1B2C3D4E5F6G7H8");
        assertNotNull(code);
        assertEquals("A1B2C3D4E5F6G7H8", code.getCode());
    }

    @Test
    @DisplayName("Can't create two ePrescripCode objects with identical codes")
    void creatingDuplicateCode_throwsException() throws ePrescripCodeException {
        new ePrescripCode("A1B2C3D4E5F6G7H8");
        assertThrows(ePrescripCodeException.class,
                () -> new ePrescripCode("A1B2C3D4E5F6G7H8"));
    }

    @Test
    @DisplayName("ePrescriptionCode equals and hashCode work correctly for identical codes")
    void equalsAndHashCode_identicalCodes() throws ePrescripCodeException {
        ePrescripCode c1 = new ePrescripCode("A1B2C3D4E5F6G7H8");
        ePrescripCode c2 = new ePrescripCode("A1B2C3D4E5F6G7H8", true); // constructor test

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
