package Model;

/**
 * Every transaction of customer will be recorded here
 */
public class Statement {
    String transaction;
    public Statement(String transaction) {
        this.transaction = transaction;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}
