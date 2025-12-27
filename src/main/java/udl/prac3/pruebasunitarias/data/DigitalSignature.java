package udl.prac3.pruebasunitarias.data;

import udl.prac3.pruebasunitarias.data.exceptions.*;
import java.util.Arrays;

final public class DigitalSignature {
    private final byte[] signature;

    public DigitalSignature(byte[] signature) throws DigitalSignatureException {
        if (signature == null) throw new DigitalSignatureException("Digital signature cannot be null");
        else if (signature.length == 0) throw new DigitalSignatureException("Digital signature cannot be empty");

        this.signature = Arrays.copyOf(signature, signature.length);
    }

    public byte[] getSignature() {
        return Arrays.copyOf(signature, signature.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigitalSignature that = (DigitalSignature) o;
        return Arrays.equals(signature, that.signature);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(signature);
    }

    @Override
    public String toString() {
        return "DigitalSignature{" + "signature=" + Arrays.toString(signature) + '}';
    }
}