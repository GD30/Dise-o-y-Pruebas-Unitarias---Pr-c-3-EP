package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import udl.prac3.pruebasunitarias.data.exceptions.*;

class HealthCardIDTest {

    @Test
    void constructor_nullCode_throwsException() {
        assertThrows(HealthCardIDException.class,
                () -> new HealthCardID(null));
    }

    @Test
    void constructor_invalidFormat_throwsException() {
        assertThrows(HealthCardIDException.class,
                () -> new HealthCardID("123"));
    }

    @Test
    void constructor_validCode_createsObject() throws HealthCardIDException {
        HealthCardID cip = new HealthCardID("ABCD1234EFGH5678");
        assertNotNull(cip);
    }

    @Test
    void equals_sameCode_returnsTrue() throws HealthCardIDException {
        HealthCardID c1 = new HealthCardID("ABCD1234EFGH5678");
        HealthCardID c2 = new HealthCardID("ABCD1234EFGH5678");

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
