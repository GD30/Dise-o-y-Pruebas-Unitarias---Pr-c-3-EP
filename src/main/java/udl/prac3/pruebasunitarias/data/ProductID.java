package udl.prac3.pruebasunitarias.data;
import udl.prac3.pruebasunitarias.data.exceptions.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * Universal Product Code (UPC) identifier for medicines.
 * Standard UPC codes consist of 12 digits.
 *
 * This is an immutable value class - once created, it cannot be modified.
 */
final public class ProductID {
    private final String code;
    private static final Set<String> existingCodes = ConcurrentHashMap.newKeySet();

    /**
     * Creates a new ProductID
     * @param code The product UPC code (must be 12 digits)
     * @throws ProductIDException if code is null or incorrectly formatted
     */
    // Constructor normal: bloquea duplicados
    public ProductID(String code) throws ProductIDException {
        this(code, false);
    }

    // Constructor especial para tests
    ProductID(String code, boolean skipRegistry) throws ProductIDException {
        if (code == null) {
            throw new ProductIDException("Product code cannot be null");
        }
        if (!code.matches("^[0-9]{12}$")) {
            throw new ProductIDException("Product code must be exactly 12 digits");
        }
        this.code = code;

        if (!skipRegistry) {
            if (!existingCodes.add(code)) {
                throw new ProductIDException("Product code already exists");
            }
        }
    }

    /**
     * Gets the product code
     * @return the product code as a String
     */
    public String getCode() {
        return code;
    }

    /**
     * Compares this ProductID with another object for equality
     * Two ProductIDs are equal if their code values are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID productID = (ProductID) o;
        return code.equals(productID.code);
    }

    /**
     * Returns a hash code for this ProductID
     */
    @Override
    public int hashCode() {
        return code.hashCode();
    }

    /**
     * Returns a string representation of this ProductID
     */
    @Override
    public String toString() {
        return "ProductID{" + "code='" + code + '\'' + '}';
    }
}