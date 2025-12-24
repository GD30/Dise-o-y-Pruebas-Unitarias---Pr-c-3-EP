package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import udl.prac3.pruebasunitarias.data.exceptions.*;

class ProductIDTest {

    @Test
    void constructor_nullCode_throwsException() {
        assertThrows(ProductIDException.class,
                () -> new ProductID(null));
    }

    @Test
    void constructor_invalidFormat_throwsException() {
        assertThrows(ProductIDException.class,
                () -> new ProductID("ABC123"));
    }

    @Test
    void constructor_validUPC_createsObject() throws ProductIDException {
        ProductID p = new ProductID("123456789012");
        assertNotNull(p);
    }

    @Test
    void equals_sameUPC_returnsTrue() throws ProductIDException {
        ProductID p1 = new ProductID("123456789012");
        ProductID p2 = new ProductID("123456789012");

        assertEquals(p1, p2);
    }
}
