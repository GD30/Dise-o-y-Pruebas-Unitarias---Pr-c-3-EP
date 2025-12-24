package udl.prac3.pruebasunitarias.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import udl.prac3.pruebasunitarias.data.exceptions.*;

class DigitalSignatureTest {

    @Test
    void constructor_nullSignature_throwsException() {
        assertThrows(DigitalSignatureException.class,
                () -> new DigitalSignature(null));
    }

    @Test
    void constructor_emptySignature_throwsException() {
        assertThrows(DigitalSignatureException.class,
                () -> new DigitalSignature(new byte[0]));
    }

    @Test
    void constructor_validSignature_createsObject() throws DigitalSignatureException {
        byte[] sig = {1, 2, 3};
        DigitalSignature ds = new DigitalSignature(sig);
        assertNotNull(ds);
    }

    @Test
    void defensiveCopy_isPreserved() throws DigitalSignatureException {
        byte[] sig = {1, 2, 3};
        DigitalSignature ds = new DigitalSignature(sig);

        sig[0] = 9; // intento modificar desde fuera

        assertNotEquals(9, ds.getSignature()[0]);
    }
}
