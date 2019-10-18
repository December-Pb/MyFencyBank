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
        List<Account> accountList = customer.getAccountList();
        List<String> result = new ArrayList<>();
        for(Account account : accountList) {
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
}
