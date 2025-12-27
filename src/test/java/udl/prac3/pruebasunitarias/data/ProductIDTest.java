//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package udl.prac3.pruebasunitarias.data;

import java.lang.reflect.Field;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import udl.prac3.pruebasunitarias.data.exceptions.ProductIDException;

class ProductIDTest {
    @BeforeEach
    void resetExistingCodes() throws Exception {
        Field field = ProductID.class.getDeclaredField("existingCodes");
        field.setAccessible(true);
        ((Set)field.get((Object)null)).clear();
    }

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
    @DisplayName("Can't create two ProductID objects with identical codes")
    void creatingDuplicateUPC_throwsException() throws ProductIDException {
        new ProductID("123456789012");
        Assertions.assertThrows(ProductIDException.class, () -> new ProductID("123456789012"));
    }

    @Test
    @DisplayName("ProductID equals and hashCode work correctly for identical codes")
    void equalsAndHashCode_identicalCodes() throws ProductIDException {
        ProductID p1 = new ProductID("123456789012");
        ProductID p2 = new ProductID("123456789012", true);
        Assertions.assertEquals(p1, p2);
        Assertions.assertEquals(p1.hashCode(), p2.hashCode());
    }
}
