package udl.prac3.pruebasunitarias.services;

import udl.prac3.pruebasunitarias.data.ProductID;

public class Suggestion {

    public enum SuggestionType {
        INSERT,
        MODIFY,
        ELIMINATE
    }

    private SuggestionType type;
    private ProductID productID;
    private String[] guidelines;

    public Suggestion(SuggestionType type, ProductID productID, String[] guidelines) {
        this.type = type;
        this.productID = productID;
        this.guidelines = guidelines;
    }

    public Suggestion(ProductID productID) {
        this.type = SuggestionType.ELIMINATE;
        this.productID = productID;
        this.guidelines = null;
    }

    public SuggestionType getType() {
        return type;
    }

    public ProductID getProductID() {
        return productID;
    }

    public String[] getGuidelines() {
        return guidelines;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Suggestion{type=").append(type);
        sb.append(", product=").append(productID);

        if (guidelines != null && guidelines.length > 0) {
            sb.append(", guidelines=[");
            for (int i = 0; i < guidelines.length; i++) {
                sb.append(guidelines[i]);
                if (i < guidelines.length - 1) sb.append(", ");
            }
            sb.append("]");
        }

        sb.append("}");
        return sb.toString();
    }
}