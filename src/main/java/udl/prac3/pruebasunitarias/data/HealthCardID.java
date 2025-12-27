package udl.prac3.pruebasunitarias.data;

import udl.prac3.pruebasunitarias.data.exceptions.HealthCardIDException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

final public class HealthCardID {

    private final String personalID;
    private static final Set<String> existingIDs = ConcurrentHashMap.newKeySet();

    // Constructor normal: bloquea IDs duplicados
    public HealthCardID(String code) throws HealthCardIDException {
        this(code, false);
    }

    // Constructor especial para tests: skipRegistry = true permite crear duplicados
    HealthCardID(String code, boolean skipRegistry) throws HealthCardIDException {
        if (code == null) throw new HealthCardIDException("Health card ID cannot be null");
        else if (!code.matches("^[A-Za-z0-9]{16}$")) throw new HealthCardIDException("Health card ID must be exactly 16 alphanumeric characters");

        this.personalID = code;

        if (!skipRegistry) {
            if (!existingIDs.add(code)) throw new HealthCardIDException("Health card ID already exists");
        }
    }

    public String getPersonalID() {
        return personalID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthCardID hcardID = (HealthCardID) o;
        return personalID.equals(hcardID.personalID);
    }

    @Override
    public int hashCode() {
        return personalID.hashCode();
    }

    @Override
    public String toString() {
        return "HealthCardID{personalID='" + personalID + "'}";
    }
}