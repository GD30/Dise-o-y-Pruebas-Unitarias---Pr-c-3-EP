package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import udl.prac3.pruebasunitarias.data.exceptions.HealthCardIDException;

import java.util.Set;

class HealthCardIDTest {

    @BeforeEach
    void resetExistingIDs() throws Exception {
        // Limpiar el Set estático antes de cada test
        java.lang.reflect.Field field = HealthCardID.class.getDeclaredField("existingIDs");
        field.setAccessible(true);
        ((Set<String>) field.get(null)).clear();
    }

    @Test
    @DisplayName("HealthCardID rejects null personalID")
    void constructor_nullCode_throwsException() {
        assertThrows(HealthCardIDException.class,
                () -> new HealthCardID(null));
    }

    @Test
    @DisplayName("HealthCardID rejects invalid personalID format")
    void constructor_invalidFormat_throwsException() {
        assertThrows(HealthCardIDException.class,
                () -> new HealthCardID("123")); // demasiado corto
        assertThrows(HealthCardIDException.class,
                () -> new HealthCardID("ABCD1234EFGH!678")); // carácter inválido
    }

    @Test
    @DisplayName("HealthCardID accepts valid personalID format")
    void constructor_validCode_createsObject() throws HealthCardIDException {
        HealthCardID hc = new HealthCardID("ABCD1234EFGH5678");
        assertNotNull(hc);
        assertEquals("ABCD1234EFGH5678", hc.getPersonalID());
    }

    @Test
    @DisplayName("Can't create two HealthCardID objects with identical personalID")
    void creatingDuplicateHealthCardID_throwsException() throws HealthCardIDException {
        HealthCardID c1 = new HealthCardID("ABCD1234EFGH5678");
        assertThrows(HealthCardIDException.class,
                () -> new HealthCardID("ABCD1234EFGH5678"));
    }

    @Test
    @DisplayName("HealthCardID equals and hashCode work correctly for identical codes (using test constructor)")
    void equalsAndHashCode_identicalCodes() throws HealthCardIDException {
        HealthCardID c1 = new HealthCardID("ABCD1234EFGH5678");              // registro normal
        HealthCardID c2 = new HealthCardID("ABCD1234EFGH5678", true);        // skipRegistry para test
        HealthCardID c3 = new HealthCardID("EFGH1234IJKL5678", true);

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
    }
}
