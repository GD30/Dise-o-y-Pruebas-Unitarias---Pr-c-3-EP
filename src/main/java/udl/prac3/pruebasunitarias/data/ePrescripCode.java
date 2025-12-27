package udl.prac3.pruebasunitarias.data;

import udl.prac3.pruebasunitarias.data.exceptions.*;
import java.util.*;
import java.util.concurrent.*;

final public class ePrescripCode {
    private final String code;

    ePrescripCode(String code) throws ePrescripCodeException {
        if (code == null) {
            throw new ePrescripCodeException("ePrescription code cannot be null");
        }
        if (!code.matches("^[A-Za-z0-9]{16}$")) {
            throw new ePrescripCodeException("ePrescription code must be exactly 16 alphanumeric characters");
        }
        this.code = code;
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