package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import udl.prac3.pruebasunitarias.data.exceptions.*;

class DigitalSignatureTest {

    @Test
    @DisplayName("DigitalSignature reject null byte array")
    void constructor_nullSignature_throwsException() {
        assertThrows(DigitalSignatureException.class,
                () -> new DigitalSignature(null));
    }

    @Test
    @DisplayName("DigitalSignature reject empty byte array")
    void constructor_emptySignature_throwsException() {
        assertThrows(DigitalSignatureException.class,
                () -> new DigitalSignature(new byte[0]));
    }

    @Test
    @DisplayName("DigitalSignature accept valid byte array")
    void constructor_validSignature_createsObject() throws DigitalSignatureException {
        byte[] sig = {1, 2, 3};
        DigitalSignature ds = new DigitalSignature(sig);
        assertNotNull(ds);
    }

    @Test
    @DisplayName("Defensive copy protects internal signature from external modification")
    void defensiveCopy_isPreserved() throws DigitalSignatureException {
        byte[] sig = {1, 2, 3};
        DigitalSignature ds = new DigitalSignature(sig);

        sig[0] = 9; // intento modificar desde fuera

        assertNotEquals(9, ds.getSignature()[0]);
    }
}
