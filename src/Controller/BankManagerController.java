package Controller;

import Model.Statement;
import Service.ATMManagerService;

import java.util.List;

/**
 * The controller is called by GUI, and it will call service layer to get data and send the data back to GUI
 */
public class BankManagerController {

    private static BankManagerController bankManagerController = new BankManagerController();
    private ATMManagerService atm;

    public static BankManagerController getInstance() {
        return bankManagerController;
    }
    private BankManagerController() {
        atm = ATMManagerService.getInstance();
    }

    public boolean loginIn(String ID, String password) {
        String truePassword = atm.getManagerPassword(ID);
        return password.equals(truePassword);
    }

    public List<Statement> getCurrentStatement(String ID) {
        return atm.getBankManager(ID).getCurrentStatement();
    }

    public List<Statement> getFullyStatement(String ID) {
        List<Statement> wholeStatement = atm.getBankManager(ID).getWholeStatement();
        return wholeStatement;
    }

    public void initStatementList(String ID) {
        List<Statement> currentStatement = atm.getBankManager(ID).getCurrentStatement();
        List<Statement> wholeStatement = atm.getBankManager(ID).getWholeStatement();
        wholeStatement.addAll(currentStatement);
        atm.getBankManager(ID).setWholeStatement(wholeStatement);
    }
}
