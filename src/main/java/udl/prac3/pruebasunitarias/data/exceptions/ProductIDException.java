package udl.prac3.pruebasunitarias.data.exceptions;

/**
 * Exception thrown when a ProductID (UPC code) is invalid.
 * This includes null values or incorrectly formatted codes.
 */
public class ProductIDException extends Exception {
    public ProductIDException(String message) {
        super(message);
    }
}