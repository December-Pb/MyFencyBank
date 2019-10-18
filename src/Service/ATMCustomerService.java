package Service;

import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ATMCustomerService {

    private static ATMCustomerService atmService = new ATMCustomerService();

    public static ATMCustomerService getInstance() {
        return atmService;
    }

    private ATMCustomerService() {}

    public Customer getCustomer(String ID) {
        return CustomerDAOImpl.getInstance().getCustomer(ID);
    }

    /**
     * When the customer wants to create an saving account, the system will generate the account number by UUID, then customer can save money
     * in a currency. The initiate account have 50 in each currency
     * @param ID Customer ID
     * @param money The money customer wants to put in a currency
     * @param currencyName The currency name
     * @return A new account
     */
    public Account createSavingAccount(String ID, double money, String currencyName) {
        CustomerDAO customerDAO = CustomerDAOImpl.getInstance();
        String accountNumber = UUID.randomUUID().toString().substring(0,8);
        Account savingAccount = new SavingAccount(accountNumber);
        for(Currency currency : savingAccount.getCurrencies()){
            if(currency.getCurrencyName().equals(currencyName))
                currency.setDeposit(money);
        }
        Customer customer = customerDAO.getCustomer(ID);
        customer.getAccountList().add(savingAccount);
        ATMManagerService.earnMoney(ATMManagerService.CREATEACCOUNT);
        for(Currency currency : savingAccount.getCurrencies()){
            ATMManagerService.earnMoney(currency.transferOut(currency.getDeposit()));
        }
        customer.getStatement().add(new Statement(ID + " Create Saving Account: " + accountNumber + " Charge for 10 bucks"));
        BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
        bankMananger.getCurrentStatement().add(new Statement(ID + " Create Saving Account: " + accountNumber + " Charge for 10 bucks"));
        return savingAccount;
    }

    /**
     * When the customer wants to create an saving account, the system will generate the account number by UUID, then customer can save money
     * in a currency. The initiate account have 50 in each currency
     * @param ID Customer ID
     * @param money The money customer wants to put in a currency
     * @param currencyName The currency name
     * @return A new account
     */
    public Account createCheckingAccount(String ID, double money, String currencyName) {
        CustomerDAO customerDAO = CustomerDAOImpl.getInstance();
        String accountNumber = UUID.randomUUID().toString().substring(0,8);
        Account checkingAccount = new CheckingAccount(accountNumber);
        for(Currency currency : checkingAccount.getCurrencies()){
            if(currency.getCurrencyName().equals(currencyName))
                currency.setDeposit(money);
        }
        Customer customer = customerDAO.getCustomer(ID);
        customer.getAccountList().add(checkingAccount);
        ATMManagerService.earnMoney(ATMManagerService.CREATEACCOUNT);
        for(Currency currency : checkingAccount.getCurrencies()){
            ATMManagerService.earnMoney(currency.transferOut(currency.getDeposit()));
        }
        customer.getStatement().add(new Statement(ID + " Create Checking Account: " + accountNumber + " Charge for 10 bucks"));
        BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
        bankMananger.getCurrentStatement().add(new Statement(ID + " Create Saving Account: " + accountNumber + " Charge for 10 bucks"));
        return checkingAccount;
    }

    /**
     * Create a new customer
     * @param firstName The first name of customer
     * @param lastName The last name of customer
     * @param password The password of customer
     * @return a new customer
     */
    public Customer createCustomer(String firstName, String lastName, String password) {
        String ID = UUID.randomUUID().toString().substring(0,8);
        Customer customer = new Customer(ID, firstName + lastName, password, firstName, lastName, ID);
        CustomerDAO customerDAO = CustomerDAOImpl.getInstance();
        customerDAO.addCustomer(customer);
        customer.getStatement().add(new Statement("Create New Customer, Customer ID is " + ID));
        BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
        bankMananger.getCurrentStatement().add(new Statement(ID + " Create Customer Account: " + ID));
        return customer;
    }

    /**
     * The customer get a loan
     * @param ID The customer ID
     * @param amount The amount of loan the customer wants to get
     * @param currencyName The currency name of the loan
     * @param pawnName The pawn for the loan
     * @return If the customer get the loan successfully
     */
    public boolean getLoan(String ID, double amount, String currencyName, String pawnName) {
        CustomerDAO customerDAO = CustomerDAOImpl.getInstance();
        Customer customer = customerDAO.getCustomer(ID);
        Currency currency;
        if(customer != null) {
            if(currencyName.equals("RMB"))
                currency = new RMBCurrency("RMB Currency", amount);
            else if(currencyName.equals("Dollar"))
                currency = new DollarCurrency("Dollar Currency", amount);
            else
                currency = new GBPCurrency("GBP Currency", amount);
            String loanNumber = UUID.randomUUID().toString().substring(0,8);
            Loan loan = new Loan(pawnName, 0.1, currency, loanNumber);

            customer.getLoanItems().add(loan);
            customer.getStatement().add(new Statement("Get " + amount + " money from bank. The pawn is " + pawnName + ". The" +
                    "Interest is 0.1"));
            BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
            bankMananger.getCurrentStatement().add(new Statement(ID + " Get " + amount + " money from bank. The pawn is " + pawnName + ". The" +
                    "Interest is 0.1"));
            ATMManagerService.loseMoney(currency.transferOut(amount));
            return true;
        }
        return false;
    }

    /**
     * The customer wants to look through all transaction, each time this will charge 10 bucks
     * @param ID The customer ID
     * @return
     */
    public List<Statement> getAllTransaction(String ID) {
        CustomerDAO customerDAO = CustomerDAOImpl.getInstance();
        Customer customer = customerDAO.getCustomer(ID);
        customer.getStatement().add(new Statement("10 bucks for Checking Transaction!"));
        double currentDeposit = customer.getAccountList().get(0).getCurrencies().get(1).getDeposit();
        customer.getAccountList().get(0).getCurrencies().get(1).setDeposit(currentDeposit - ATMManagerService.CHECKTRANSACTION);
        ATMManagerService.earnMoney(ATMManagerService.CHECKTRANSACTION);
        BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
        bankMananger.getCurrentStatement().add(new Statement(ID + " 10 bucks for Checking Transaction!"));
        return customer.getStatement();
    }

    /**
     * The customer will withdraw money, if the customer withdraw money from a saving account, 10 bucks will be charged
     * @param ID The customer ID
     * @param accountNumber The account number
     * @param currencyName The currency name
     * @param drawMoney The amount of money customer wants to draw
     * @return
     */
    public boolean withdrawMoney(String ID, String accountNumber, String currencyName, double drawMoney) {
        CustomerDAO customerDAO = CustomerDAOImpl.getInstance();
        Customer customer = customerDAO.getCustomer(ID);
        List<Account> accountList = customer.getAccountList();
        for(Account account : accountList) {
            if(account.getAccountNumber().equals(accountNumber) && account instanceof CheckingAccount) {
                List<Currency> currencyList = account.getCurrencies();
                for(Currency currency : currencyList) {
                    if(currency.getCurrencyName().equals(currencyName)) {
                        if(currency.getDeposit() >= drawMoney) {
                            currency.setDeposit(currency.getDeposit() - drawMoney);
                            customer.getStatement().add(new Statement("Withdraw " + drawMoney + " money from checking account"));
                            BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
                            bankMananger.getCurrentStatement().add(new Statement(ID + " Withdraw " + drawMoney + " money from checking account"));
                            ATMManagerService.loseMoney(currency.transferOut(drawMoney));
                            return true;
                        }
                    }
                }
            }
            else if(account.getAccountNumber().equals(accountNumber) && account instanceof SavingAccount) {
                List<Currency> currencyList = account.getCurrencies();
                for(Currency currency : currencyList) {
                    if(currency.getCurrencyName().equals(currencyName)) {
                        if(currency.getDeposit() >= drawMoney) {
                            currency.setDeposit(currency.getDeposit() - drawMoney);
                            customer.getStatement().add(new Statement("Withdraw " + drawMoney + " money from saving account, requires for 10 bucks"));
                            ATMManagerService.earnMoney(ATMManagerService.WITHDRAWMONEYFROMSAVINGACCOUNT);
                            BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
                            bankMananger.getCurrentStatement().add(new Statement(ID + " Withdraw " + drawMoney + " money from saving account, requires for 10 bucks"));
                            ATMManagerService.loseMoney(currency.transferOut(drawMoney));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * The Customer wants to save money in bank
     * @param ID The ID of customer
     * @param accountNumber The number of account which customer want to save in
     * @param currencyName The currency name
     * @param saveMoney The amount of money
     * @return Whether customer save money successfully
     */
    public boolean saveMoney(String ID, String accountNumber, String currencyName, double saveMoney) {
        CustomerDAO customerDAO = CustomerDAOImpl.getInstance();
        Customer customer = customerDAO.getCustomer(ID);
        List<Account> accountList = customer.getAccountList();
        for(Account account : accountList) {
            if(account.getAccountNumber().equals(accountNumber)) {
                List<Currency> currencyList = account.getCurrencies();
                for(Currency currency : currencyList) {
                    if(currency.getCurrencyName().equals(currencyName)) {
                        currency.setDeposit(currency.getDeposit() + saveMoney);
                        customer.getStatement().add(new Statement("Save " + saveMoney + " money to bank"));
                        BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
                        bankMananger.getCurrentStatement().add(new Statement(ID + " Save " + saveMoney + " money to bank"));
                        ATMManagerService.earnMoney(currency.transferOut(saveMoney));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * The customer wants to transfer money from his/her one account to his/her another account
     * @param ID The customer ID
     * @param inputAccountNumber The account the customer wants to transfer in
     * @param inputCurrencyName The currency the customer wants to transfer in
     * @param outputAccountNumber The account the customer wants to transfer out
     * @param outputCurrencyName The currency the customer wants to transfer out
     * @param transferMoney The amount of money
     * @return whether the customer transfer successfully
     */
    public boolean transferMoney(String ID, String inputAccountNumber, String inputCurrencyName, String outputAccountNumber,
                                 String outputCurrencyName, double transferMoney) {
        CustomerDAO customerDAO = CustomerDAOImpl.getInstance();
        Customer customer = customerDAO.getCustomer(ID);
        Account outAccout = null;
        Account inAccount = null;
        Currency outCurrency = null;
        Currency inCurrency = null;
        List<Account> accountList = customer.getAccountList();
        for(Account account : accountList) {
            if(account.getAccountNumber().equals(inputAccountNumber)) {
                inAccount = account;
                List<Currency> currencyList = account.getCurrencies();
                for(Currency currency : currencyList) {
                    if(currency.getCurrencyName().equals(inputCurrencyName))
                        inCurrency = currency;
                }
            }
            if(account.getAccountNumber().equals(outputAccountNumber)) {
                outAccout = account;
                List<Currency> currencyList = account.getCurrencies();
                for(Currency currency : currencyList) {
                    if(currency.getCurrencyName().equals(outputCurrencyName))
                        outCurrency = currency;
                }
            }
        }
        if(outAccout != null && inAccount != null && outCurrency != null && inCurrency != null) {
            outCurrency.setDeposit(outCurrency.getDeposit() - transferMoney);
            inCurrency.setDeposit(inCurrency.getDeposit() + inCurrency.transferIn(outCurrency.transferOut(transferMoney)));
            return true;
        }
        return false;
    }

    /**
     * Check the ID and password for a user
     * @param ID The customer's ID
     * @param password The customer's password
     * @return Whether the ID and password is correct
     */
    public boolean checkPassword(String ID, String password) {
        CustomerDAO customerDAO = CustomerDAOImpl.getInstance();
        Customer customer = customerDAO.getCustomer(ID);
        if(customer != null && customer.getPassword().equals(password))
            return true;
        return false;
    }

    /**
     * The customer wants to create a new account, the bank will charge 10 bucks for each new account
     * @param ID The customer ID
     * @param category Saving account or checking account
     * @param money The amount of money the customer wants to save
     * @param currencyName The name of currency
     */
    public void createAccount(String ID, String category, double money, String currencyName) {
        if(category.equals("Checking Account"))
            createCheckingAccount(ID, money, currencyName);
        else
            createSavingAccount(ID, money, currencyName);
    }

    /**
     * The customer wants to delete account, if the customer only has one accont, he/she cannot delete it,
     * each delete option will charge 10 bucks
     * @param ID Customer ID
     * @param accountNumber Account Number
     */
    public void deleteAccount(String ID, String accountNumber) {
        Customer customer = CustomerDAOImpl.getInstance().getCustomer(ID);
        List<Account> accountList = customer.getAccountList();
        Account outMoneyAccout = null;
        Account inMoneyAccount = null;
        for(int i=0;i<accountList.size();i++) {
            if(accountNumber.equals(accountList.get(i).getAccountNumber())) {
                outMoneyAccout = accountList.get(i);
            }
            else {
                inMoneyAccount = accountList.get(i);
            }
        }
        if(outMoneyAccout != null && inMoneyAccount != null) {
            List<Currency> outCurrencyList = outMoneyAccout.getCurrencies();
            List<Currency> inCurrencyList = inMoneyAccount.getCurrencies();
            for(int i=0;i<outCurrencyList.size();i++) {
                double outMoney = outCurrencyList.get(i).getDeposit();
                double originMoney = inCurrencyList.get(i).getDeposit();
                Currency temp = inCurrencyList.get(i);
                if(temp instanceof DollarCurrency)
                    temp.setDeposit(originMoney + outMoney - ATMManagerService.DELETEACCOUNT);
                else
                    temp.setDeposit(originMoney + outMoney);
                customer.getStatement().add(new Statement("Delete Account " + outMoneyAccout.getAccountNumber() + ", Charge 10 bucks"));
                BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
                bankMananger.getCurrentStatement().add(new Statement(ID + " Delete Account " + outMoneyAccout.getAccountNumber() + ", Charge 10 bucks"));
                ATMManagerService.earnMoney(ATMManagerService.DELETEACCOUNT);
            }
            accountList.remove(outMoneyAccout);
        }
    }

    /**
     * The customer wants to pay loan, if the account does have enough money, it will return false
     * @param ID The customer ID
     * @param loanNumber The number of current loan
     * @param accountNumber The account number to pay for the loan
     * @param currencyName The currency name to pay for the loan
     * @param loanCurrencyName The currency of loan
     * @param money The amount of loan
     * @return Whether customer pay for the loan successfully
     */
    public boolean payLoan(String ID, String loanNumber, String accountNumber, String currencyName, String loanCurrencyName, double money) {
        Customer customer = CustomerDAOImpl.getInstance().getCustomer(ID);
        List<Loan> loanList = customer.getLoanItems();
        List<Account> accountList = customer.getAccountList();
        Currency loanCurrency = null;
        Loan currentLoan = null;
        Currency payCurrency = null;
        for(Loan loan:loanList) {
            if(loanNumber.equals(loan.getLoanNumber())) {
                loanCurrency = loan.getCurrency();
                currentLoan = loan;
            }
        }
        for(Account account : accountList) {
            if(accountNumber.equals(account.getAccountNumber())) {
                List<Currency> currencyList = account.getCurrencies();
                for(Currency currency : currencyList) {
                    if(currencyName.equals(currency.getCurrencyName()))
                        payCurrency = currency;
                }
            }
        }
        if(loanCurrency != null && currentLoan != null && payCurrency != null) {
            money = loanCurrency.transferOut(money);
            double payMoney = payCurrency.transferOut(payCurrency.getDeposit());
            if(payMoney >= money) {
                payCurrency.setDeposit(payCurrency.transferIn(payMoney - money));
                customer.getStatement().add(new Statement("Pay Loan: " + currentLoan.getLoanNumber()));
                ATMManagerService.earnMoney(money);
                BankMananger bankMananger = ATMManagerService.getInstance().getBankManager("1");
                bankMananger.getCurrentStatement().add(new Statement(ID + "Pay Loan: " + currentLoan.getLoanNumber()));
                return true;
            }
        }
        return false;
    }
}
