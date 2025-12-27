//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import udl.prac3.pruebasunitarias.data.exceptions.ePrescripCodeException;

class ePrescripCodeTest {
    @Test
    @DisplayName("ePrescriptionCode rejects null code")
    void constructor_nullCode_throwsException() {
        Assertions.assertThrows(ePrescripCodeException.class, () -> new ePrescripCode((String)null));
    }

    @Test
    @DisplayName("ePrescriptionCode rejects invalid code format")
    void constructor_invalidFormat_throwsException() {
        Assertions.assertThrows(ePrescripCodeException.class, () -> new ePrescripCode("123"));
    }

    @Test
    @DisplayName("ePrescriptionCode accepts valid code format")
    void constructor_validCode_createsObject() throws ePrescripCodeException {
        ePrescripCode code = new ePrescripCode("A1B2C3D4E5F6G7H8");
        Assertions.assertNotNull(code);
        Assertions.assertEquals("A1B2C3D4E5F6G7H8", code.getCode());
    }

    @Test
    @DisplayName("ePrescriptionCode equals and hashCode work correctly for identical codes")
    void equalsAndHashCode_identicalCodes() throws ePrescripCodeException {
        ePrescripCode c1 = new ePrescripCode("A1B2C3D4E5F6G7H8");
        ePrescripCode c2 = new ePrescripCode("A1B2C3D4E5F6G7H8");
        Assertions.assertEquals(c1, c2);
        Assertions.assertEquals(c1.hashCode(), c2.hashCode());
    }
}
