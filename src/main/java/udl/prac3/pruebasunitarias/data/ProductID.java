package udl.prac3.pruebasunitarias.data;

import udl.prac3.pruebasunitarias.data.exceptions.*;
import java.util.*;
import java.util.concurrent.*;

final public class ProductID {
    private final String code;

    public ProductID(String code) throws ProductIDException {
        if (code == null) {
            throw new ProductIDException("Product code cannot be null");
        }
        if (!code.matches("^[0-9]{12}$")) {
            throw new ProductIDException("Product code must be exactly 12 digits");
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
        ProductID productID = (ProductID) o;
        return code.equals(productID.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "ProductID{" + "code='" + code + '\'' + '}';
    }
}