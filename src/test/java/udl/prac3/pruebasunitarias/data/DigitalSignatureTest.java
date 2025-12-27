//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import udl.prac3.pruebasunitarias.data.exceptions.DigitalSignatureException;

class DigitalSignatureTest {
    @Test
    @DisplayName("DigitalSignature reject null byte array")
    void constructor_nullSignature_throwsException() {
        Assertions.assertThrows(DigitalSignatureException.class, () -> new DigitalSignature((byte[])null));
    }

    @Test
    @DisplayName("DigitalSignature reject empty byte array")
    void constructor_emptySignature_throwsException() {
        Assertions.assertThrows(DigitalSignatureException.class, () -> new DigitalSignature(new byte[0]));
    }

    @Test
    @DisplayName("DigitalSignature accept valid byte array")
    void constructor_validSignature_createsObject() throws DigitalSignatureException {
        byte[] sig = new byte[]{1, 2, 3};
        DigitalSignature ds = new DigitalSignature(sig);
        Assertions.assertNotNull(ds);
    }

    @Test
    @DisplayName("Defensive copy protects internal signature from external modification")
    void defensiveCopy_isPreserved() throws DigitalSignatureException {
        byte[] sig = new byte[]{1, 2, 3};
        DigitalSignature ds = new DigitalSignature(sig);
        sig[0] = 9;
        Assertions.assertNotEquals(9, ds.getSignature()[0]);
    }
}
