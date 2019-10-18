package Service;

import Model.BankManagerDAOImpl;
import Model.BankMananger;

public class ATMManagerService {

    private static ATMManagerService atmManagerService = new ATMManagerService();
    private static double wallet = 1000000;
    public static final double WITHDRAWMONEYFROMSAVINGACCOUNT = 10;
    public static final double CHECKTRANSACTION = 10;
    public static final double CREATEACCOUNT = 10;
    public static final double DELETEACCOUNT = 10;
    public static final double LEASTMONEYWITHINTEREST = 1000;

    private ATMManagerService() {}

    public static ATMManagerService getInstance() {
        return atmManagerService;
    }

    /**
     * The bank will return a bank manager according to the ID
     * @param ID
     * @return
     */
    public String getManagerPassword(String ID) {
        BankMananger bankMananger = BankManagerDAOImpl.getInstance().getBankManager(ID);
        if(bankMananger != null) {
            return bankMananger.getID();
        }
        return null;
    }

    public BankMananger getBankManager(String ID) {
        return BankManagerDAOImpl.getInstance().getBankManager(ID);
    }

    public static double getWallet() {
        return wallet;
    }

    /**
     * The bank can earn money when customer create account, withdraw money from saving account, pay the loan and look through the transaction
     * @param money
     */
    public static void earnMoney(double money) {
        ATMManagerService.wallet += money;
    }

    /**
     * The bank can lose money when customer try to get loan
     * @param money
     */
    public static void loseMoney(double money) {
        ATMManagerService.wallet -= money;
    }

}
