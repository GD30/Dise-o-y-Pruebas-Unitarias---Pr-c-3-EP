package udl.prac3.pruebasunitarias.data;
import udl.prac3.pruebasunitarias.data.exceptions.*;

/**
 * Universal Product Code (UPC) identifier for medicines.
 * Standard UPC codes consist of 12 digits.
 *
 * This is an immutable value class - once created, it cannot be modified.
 */
final public class ProductID {
    private final String code;

    /**
     * Creates a new ProductID
     * @param code The product UPC code (must be 12 digits)
     * @throws ProductIDException if code is null or incorrectly formatted
     */
    public ProductID(String code) throws ProductIDException {
        // Check if code is null
        if (code == null) {
            throw new ProductIDException("Product code cannot be null");
        }

        // UPC codes must be exactly 12 digits
        if (!code.matches("^[0-9]{12}$")) {
            throw new ProductIDException("Product code must be exactly 12 digits");
        }

        this.code = code;
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