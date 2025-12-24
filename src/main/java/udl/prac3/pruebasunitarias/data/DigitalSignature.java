package udl.prac3.pruebasunitarias.data;
import udl.prac3.pruebasunitarias.data.exceptions.*;
import java.util.Arrays;

/**
 * Digital signature of the doctor.
 * Represented as a byte array.
 *
 * This is an immutable value class - once created, it cannot be modified.
 * Uses defensive copying to ensure immutability.
 */
final public class DigitalSignature {
    private final byte[] signature;

    /**
     * Creates a new DigitalSignature
     * @param signature The digital signature as a byte array
     * @throws DigitalSignatureException if signature is null or empty
     */
    public DigitalSignature(byte[] signature) throws DigitalSignatureException {
        // Check if signature is null
        if (signature == null) {
            throw new DigitalSignatureException("Digital signature cannot be null");
        }

        // Check if signature is empty
        if (signature.length == 0) {
            throw new DigitalSignatureException("Digital signature cannot be empty");
        }

        // Create a defensive copy to maintain immutability
        // This prevents external code from modifying the internal array
        this.signature = Arrays.copyOf(signature, signature.length);
    }

    /**
     * Gets the digital signature
     * @return a copy of the signature byte array (defensive copy)
     */
    public byte[] getSignature() {
        // Return a defensive copy to maintain immutability
        // This prevents external code from modifying the internal array
        return Arrays.copyOf(signature, signature.length);
    }

    /**
     * Compares this DigitalSignature with another object for equality
     * Two DigitalSignatures are equal if their byte arrays are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigitalSignature that = (DigitalSignature) o;
        return Arrays.equals(signature, that.signature);
    }

    /**
     * Returns a hash code for this DigitalSignature
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(signature);
    }

    /**
     * Returns a string representation of this DigitalSignature
     */
    @Override
    public String toString() {
        return "DigitalSignature{" + "signature=" + Arrays.toString(signature) + '}';
    }
}