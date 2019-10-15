package ATMController;

import ATM.CustomerATM;
import User.BankSystemUser;
import User.Customer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerController {
    private CustomerATM atm;
    public CustomerController() {
        atm = new CustomerATM();
    }

    public CustomerATM getAtm() {
        return atm;
    }

    public void setAtm(CustomerATM atm) {
        this.atm = atm;
    }

    public void insertCard(String ID) {
        // set current bank system user by using ID
        atm.insertBankCard(ID);
        BankSystemUser currentCustomer = atm.getCurrentBankSystemUser();
        if(currentCustomer instanceof Customer) {
            currentCustomer = (Customer)currentCustomer;

        }
    }

    /**
     * return the field name and attribute of this field
     * @return
     */
    public List<Customer> getUser() {
        Map<String, BankSystemUser> bankSystemUserList = atm.getBankSystemUsers(); //String is ID
        List<Customer> resultList = new ArrayList<>();
        for(Map.Entry<String, BankSystemUser> entry : bankSystemUserList.entrySet())
            if(entry.getValue() instanceof Customer)
                resultList.add((Customer)entry.getValue());
        return resultList;
    }
}
