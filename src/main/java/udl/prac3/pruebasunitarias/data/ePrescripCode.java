package udl.prac3.pruebasunitarias.data;
import udl.prac3.pruebasunitarias.data.exceptions.*;

/**
 * Electronic prescription code identifier.
 * Consists of 16 alphanumeric characters.
 *
 * This is an immutable value class - once created, it cannot be modified.
 */
final public class ePrescripCode {
    private final String code;

    /**
     * Creates a new ePrescripCode
     * @param code The electronic prescription code (must be 16 alphanumeric characters)
     * @throws ePrescripCodeException if code is null or incorrectly formatted
     */
    public ePrescripCode(String code) throws ePrescripCodeException {
        // Check if code is null
        if (code == null) {
            throw new ePrescripCodeException("ePrescription code cannot be null");
        }

        // Must be exactly 16 alphanumeric characters
        if (!code.matches("^[A-Za-z0-9]{16}$")) {
            throw new ePrescripCodeException("ePrescription code must be exactly 16 alphanumeric characters");
        }

        this.code = code;
    }

    /**
     * Gets the prescription code
     * @return the code as a String
     */
    public String getCode() {
        return code;
    }

    /**
     * Compares this ePrescripCode with another object for equality
     * Two ePrescripCodes are equal if their code values are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ePrescripCode that = (ePrescripCode) o;
        return code.equals(that.code);
    }

    /**
     * Returns a hash code for this ePrescripCode
     */
    @Override
    public int hashCode() {
        return code.hashCode();
    }

    /**
     * Returns a string representation of this ePrescripCode
     */
    @Override
    public String toString() {
        return "ePrescripCode{" + "code='" + code + '\'' + '}';
    }
}