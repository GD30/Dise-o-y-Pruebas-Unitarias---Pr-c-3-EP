package udl.prac3.pruebasunitarias.data;

import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import udl.prac3.pruebasunitarias.data.exceptions.HealthCardIDException;

class HealthCardIDTest {
    @BeforeEach
    void resetExistingIDs() throws Exception {
        Field field = HealthCardID.class.getDeclaredField("existingIDs");
        field.setAccessible(true);
        ((Set)field.get((Object)null)).clear();
    }

    @Test
    @DisplayName("HealthCardID rejects null personalID")
    void constructor_nullCode_throwsException() {
        Assertions.assertThrows(HealthCardIDException.class, () -> new HealthCardID((String)null));
    }

    @Test
    @DisplayName("HealthCardID rejects invalid personalID format")
    void constructor_invalidFormat_throwsException() {
        Assertions.assertThrows(HealthCardIDException.class, () -> new HealthCardID("123"));
        Assertions.assertThrows(HealthCardIDException.class, () -> new HealthCardID("ABCD1234EFGH!678"));
    }

    @Test
    @DisplayName("HealthCardID accepts valid personalID format")
    void constructor_validCode_createsObject() throws HealthCardIDException {
        HealthCardID hc = new HealthCardID("ABCD1234EFGH5678");
        Assertions.assertNotNull(hc);
        Assertions.assertEquals("ABCD1234EFGH5678", hc.getPersonalID());
    }

    @Test
    @DisplayName("Can't create two HealthCardID objects with identical personalID")
    void creatingDuplicateHealthCardID_throwsException() throws HealthCardIDException {
        new HealthCardID("ABCD1234EFGH5678");
        Assertions.assertThrows(HealthCardIDException.class, () -> new HealthCardID("ABCD1234EFGH5678"));
    }

    @Test
    @DisplayName("HealthCardID equals and hashCode work correctly for identical codes (using test constructor)")
    void equalsAndHashCode_identicalCodes() throws HealthCardIDException {
        HealthCardID c1 = new HealthCardID("ABCD1234EFGH5678");
        HealthCardID c2 = new HealthCardID("ABCD1234EFGH5678", true);
        HealthCardID c3 = new HealthCardID("EFGH1234IJKL5678", true);
        Assertions.assertEquals(c1, c2);
        Assertions.assertEquals(c1.hashCode(), c2.hashCode());
        Assertions.assertNotEquals(c1, c3);
    }
}
