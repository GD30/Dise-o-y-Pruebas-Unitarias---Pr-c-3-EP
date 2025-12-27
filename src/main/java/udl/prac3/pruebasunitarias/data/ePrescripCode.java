package udl.prac3.pruebasunitarias.data;

import udl.prac3.pruebasunitarias.data.exceptions.*;
import java.util.*;
import java.util.concurrent.*;

final public class ePrescripCode {
    private final String code;
    private static final Set<String> existingCodes = ConcurrentHashMap.newKeySet();

    public ePrescripCode(String code) throws ePrescripCodeException {
        this(code, false);
    }

    ePrescripCode(String code, boolean skipRegistry) throws ePrescripCodeException {
        if (code == null) {
            throw new ePrescripCodeException("ePrescription code cannot be null");
        }
        if (!code.matches("^[A-Za-z0-9]{16}$")) {
            throw new ePrescripCodeException("ePrescription code must be exactly 16 alphanumeric characters");
        }
        this.code = code;

        if (!skipRegistry) {
            if (!existingCodes.add(code)) {
                throw new ePrescripCodeException("ePrescription code already exists");
            }
        }
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ePrescripCode that = (ePrescripCode) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "ePrescripCode{" + "code='" + code + '\'' + '}';
    }
}