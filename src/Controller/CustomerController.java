package Controller;

import Model.*;
import Service.ATMCustomerService;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller is called by GUI, and it will call service layer to get data and send the data back to GUI
 */
public class CustomerController {
    private static CustomerController customerController = new CustomerController();
    private ATMCustomerService atm;

    public static CustomerController getInstance() {
        return customerController;
    }

    private CustomerController() {
        atm = ATMCustomerService.getInstance();
    }

    public Customer createCustomer(String firstName, String lastName, String password) {
        return atm.createCustomer(firstName, lastName, password);
    }

    public boolean withdrawMoney(String ID, String accountNumber, String currencyName, double drawMoney) {
        return atm.withdrawMoney(ID, accountNumber, currencyName, drawMoney);
    }

    public boolean saveMoney(String ID, String accountNumber, String currencyName, double saveMoney) {
        return atm.saveMoney(ID, accountNumber, currencyName, saveMoney);
    }

    public boolean loginIn(String ID, String password) {
        return atm.checkPassword(ID, password);
    }

    public Customer getCustomer(String ID) {
        return atm.getCustomer(ID);
    }

    public void createAccout(String ID, String category, double money, String currency) {
        atm.createAccount(ID, category, money, currency);
    }

    public boolean getLoan(String ID, String pawnName, String currencyName, double money) {
        if(pawnName.equals("Pen") && money >= 10)
            return false;
        else if(pawnName.equals("Sedan Car") && money >= 10000)
            return false;
        else if(pawnName.equals("Sport Car") && money >= 15000)
            return false;
        else {
            atm.getLoan(ID, money, currencyName, pawnName);
            return true;
        }
    }

    public List<String> getAccountNumber(String ID) {
        Customer customer = atm.getCustomer(ID);
        if(customer == null)
            return null;
        List<Account> accountList = customer.getAccountList();
        List<String> result = new ArrayList<>();
        for(Account account : accountList) {
            result.add(account.getAccountNumber());
        }
        return result;
    }

    public List<String> getCheckingAccountNumber(String ID) {
        Customer customer = atm.getCustomer(ID);
        if(customer == null)
            return null;
        List<Account> accountList = customer.getAccountList();
        List<String> result = new ArrayList<>();
        for(Account account : accountList) {
            if(account instanceof CheckingAccount)
                result.add(account.getAccountNumber());
        }
        return result;
    }

    public void transferMoney(String ID, String inputAccountNumber, String inputCurrencyName, String outputAccountNumber,
                              String outputCurrencyName, double transferMoney) {
        atm.transferMoney(ID, inputAccountNumber, inputCurrencyName, outputAccountNumber, outputCurrencyName, transferMoney);
    }

    public List<Statement> getAllTransaction(String ID) {
        return atm.getAllTransaction(ID);
    }

    /**
     * Get the money in the customer's account
     * @param ID Customer ID
     * @param currencyName Currency Name
     * @param accountNumber Account Number
     * @return
     */
    public double getMoney(String ID, String currencyName, String accountNumber) {
        if(ID == "" && currencyName == "" && accountNumber == "")
            return 0.0;
        Customer customer = atm.getCustomer(ID);
        List<Account> accountList = customer.getAccountList();
        for(Account account : accountList) {
            if(account.getAccountNumber().equals(accountNumber)) {
                List<Currency> currencyList = account.getCurrencies();
                for(Currency currency : currencyList) {
                    if(currency.getCurrencyName().equals(currencyName))
                        return currency.getDeposit();
                }
            }
        }
        return 0.0;
    }

    public void deleteAccount(String ID, String accountNumber) {
        atm.deleteAccount(ID, accountNumber);
    }

    public boolean payLoan(String ID, String loanNumber, String accountNumber, String currencyName, String loanCurrencyName, double money) {
        return atm.payLoan(ID, loanNumber, accountNumber, currencyName, loanCurrencyName, money);
    }

    /**
     * Transfer money from one customer to another customer, I assume the customer will transfer the money out to one "pool", and then
     * another customer will get the money from the "pool". So the inputCardNumber represent the card transfer out the money
     * @param inputCardNumber The card transfer out money
     * @param outputCardNumber The card transfer in money
     * @param inputAccountNumber The account number transfer out money
     * @param outputAccountNumber The account number transfer in money
     * @param inputCurrencyName The currency transfer in money
     * @param outputCurrencyName The currency transfer out money
     * @param money The amount of money
     * @return Whether the transfer is successful
     */
    public boolean transferMoneyToOther(String inputCardNumber, String outputCardNumber, String inputAccountNumber, String outputAccountNumber,
                                        String inputCurrencyName, String outputCurrencyName, double money) {
        Customer inputCustomer = atm.getCustomer(inputCardNumber);
        Customer outputCustomer = atm.getCustomer(outputCardNumber);
        Account inputAccount = null;
        Account outputAccout = null;
        Currency inputCurrency = null;
        Currency outputCurrency = null;

        if(inputCustomer != null && outputCustomer != null) {
            List<Account> inputAccountList = inputCustomer.getAccountList();
            List<Account> outputAccountList = outputCustomer.getAccountList();
            for(Account account:inputAccountList)
                if(inputAccountNumber.equals(account.getAccountNumber()))
                    inputAccount = account;
            for(Account account:outputAccountList)
                if(outputAccountNumber.equals(account.getAccountNumber()))
                    outputAccout = account;
        }

        if(inputAccount != null && outputAccout != null && inputAccount instanceof CheckingAccount && outputAccout instanceof CheckingAccount) {
            List<Currency> inputCurrencyList = inputAccount.getCurrencies();
            List<Currency> outputCurrencyList = outputAccout.getCurrencies();
            for(Currency currency : inputCurrencyList)
                if(inputCurrencyName.equals(currency.getCurrencyName()))
                    inputCurrency = currency;
            for(Currency currency : outputCurrencyList)
                if(outputCurrencyName.equals(currency.getCurrencyName()))
                    outputCurrency = currency;
        }

        if(inputCurrency != null && outputCurrency != null) {
            if(inputCurrency.getDeposit() >= money) {
                inputCurrency.setDeposit(inputCurrency.getDeposit() - money);
                money = inputCurrency.transferOut(money);
                money = outputCurrency.transferIn(money);
                outputCurrency.setDeposit(outputCurrency.getDeposit() + money);
                inputCustomer.getStatement().add(new Statement("Transfer " + money + " out to " + outputCardNumber));
                outputCustomer.getStatement().add(new Statement(inputCardNumber + " transferred " + money + " to you"));
                BankMananger bankManager = BankManagerDAOImpl.getInstance().getBankManager("1");
                bankManager.getCurrentStatement().add(new Statement(inputCardNumber + " transferred " + money + " to " + outputCardNumber));
                return true;
            }
        }

        return false;
    }
}
