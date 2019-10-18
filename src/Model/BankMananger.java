package Model;

import java.util.ArrayList;
import java.util.List;

public class BankMananger extends BankSystemUser {

    public BankMananger(String ID, String userName, String password) {
        super(ID, userName, password);
        currentStatement = new ArrayList<>();
        wholeStatement = new ArrayList<>();
    }

    private List<Statement> currentStatement;
    private List<Statement> wholeStatement;

    public List<Statement> getWholeStatement() {
        return wholeStatement;
    }

    public void setWholeStatement(List<Statement> wholeStatement) {
        this.wholeStatement = wholeStatement;
    }

    public List<Statement> getCurrentStatement() {

        return currentStatement;
    }

    public void setCurrentStatement(List<Statement> currentStatement) {
        this.currentStatement = currentStatement;
    }
}
