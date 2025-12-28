//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import udl.prac3.pruebasunitarias.data.exceptions.ProductIDException;

class ProductIDTest {
    @Test
    @DisplayName("ProductID rejects null code")
    void constructor_nullCode_throwsException() {
        Assertions.assertThrows(ProductIDException.class, () -> new ProductID((String)null));
    }

    @Test
    @DisplayName("ProductID rejects invalid code format")
    void constructor_invalidFormat_throwsException() {
        Assertions.assertThrows(ProductIDException.class, () -> new ProductID("ABC123"));
    }

    @Test
    @DisplayName("ProductID accepts valid code format")
    void constructor_validUPC_createsObject() throws ProductIDException {
        ProductID p = new ProductID("123456789012");
        Assertions.assertNotNull(p);
        Assertions.assertEquals("123456789012", p.getCode());
    }

    @Test
    @DisplayName("ProductID equals and hashCode work correctly for identical codes")
    void equalsAndHashCode_identicalCodes() throws ProductIDException {
        ProductID p1 = new ProductID("123456789012");
        ProductID p2 = new ProductID("123456789012");
        Assertions.assertEquals(p1, p2);
        Assertions.assertEquals(p1.hashCode(), p2.hashCode());
    }
}
