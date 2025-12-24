package udl.prac3.pruebasunitarias.data;
import udl.prac3.pruebasunitarias.data.exceptions.*;

/**
 * The personal identifying code in the National Health Service.
 * Consists of a sequence of 16 alphanumeric characters.
 *
 * This is an immutable value class - once created, it cannot be modified.
 */
final public class HealthCardID {
    private final String personalID;

    /**
     * Creates a new HealthCardID
     * @param code The health card identification code (must be 16 alphanumeric characters)
     * @throws HealthCardIDException if code is null or incorrectly formatted
     */
    public HealthCardID(String code) throws HealthCardIDException {
        // Check if code is null
        if (code == null) {
            throw new HealthCardIDException("Health card ID cannot be null");
        }

        // Check if code has exactly 16 alphanumeric characters
        if (!code.matches("^[A-Za-z0-9]{16}$")) {
            throw new HealthCardIDException("Health card ID must be exactly 16 alphanumeric characters");
        }

        this.personalID = code;
    }

    /**
     * Gets the personal identification code
     * @return the personal ID as a String
     */
    public String getPersonalID() {
        return personalID;
    }

    /**
     * Compares this HealthCardID with another object for equality
     * Two HealthCardIDs are equal if their personalID values are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthCardID hcardID = (HealthCardID) o;
        return personalID.equals(hcardID.personalID);
    }

    /**
     * Returns a hash code for this HealthCardID
     */
    @Override
    public int hashCode() {
        return personalID.hashCode();
    }

    /**
     * Returns a string representation of this HealthCardID
     */
    @Override
    public String toString() {
        return "HealthCardID{" + "personal code='" + personalID + '\'' + '}';
    }
}