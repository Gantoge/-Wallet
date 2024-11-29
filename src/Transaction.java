import java.io.Serial;
import java.io.Serializable;

public class Transaction implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String type;
    private final String category;
    private final double amount;

    public Transaction(String type, String category, double amount) {
        this.type = type;
        this.category = category;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}