package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * The account has three different currencies
 */
public abstract class Account {
    private List<Currency> currencies;
    private String accountNumber;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        currencies = new ArrayList<>();
        Currency rmbCurrency = new RMBCurrency("RMB", 5);
        Currency dollarCurrency = new DollarCurrency("Dollar", 5);
        Currency GBPCurrency = new GBPCurrency("GBP", 5);
        currencies.add(rmbCurrency);
        currencies.add(dollarCurrency);
        currencies.add(GBPCurrency);
    }


    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
