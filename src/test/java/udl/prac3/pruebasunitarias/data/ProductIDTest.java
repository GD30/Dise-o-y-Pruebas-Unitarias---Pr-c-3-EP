package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import java.lang.reflect.Field;
import java.util.Set;

class ProductIDTest {

    @BeforeEach
    void resetExistingCodes() throws Exception {
        Field field = ProductID.class.getDeclaredField("existingCodes");
        field.setAccessible(true);
        ((Set<String>) field.get(null)).clear();
    }

    @Test
    @DisplayName("ProductID rejects null code")
    void constructor_nullCode_throwsException() {
        assertThrows(ProductIDException.class,
                () -> new ProductID(null));
    }

    @Test
    @DisplayName("ProductID rejects invalid code format")
    void constructor_invalidFormat_throwsException() {
        assertThrows(ProductIDException.class,
                () -> new ProductID("ABC123"));
    }

    @Test
    @DisplayName("ProductID accepts valid code format")
    void constructor_validUPC_createsObject() throws ProductIDException {
        ProductID p = new ProductID("123456789012");
        assertNotNull(p);
        assertEquals("123456789012", p.getCode());
    }

    @Test
    @DisplayName("Can't create two ProductID objects with identical codes")
    void creatingDuplicateUPC_throwsException() throws ProductIDException {
        new ProductID("123456789012");
        assertThrows(ProductIDException.class,
                () -> new ProductID("123456789012"));
    }

    @Test
    @DisplayName("ProductID equals and hashCode work correctly for identical codes")
    void equalsAndHashCode_identicalCodes() throws ProductIDException {
        ProductID p1 = new ProductID("123456789012");
        ProductID p2 = new ProductID("123456789012", true); // constructor test

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
