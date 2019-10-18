package Model;

import java.util.Map;

public interface CustomerDAO {
    Map<String, Customer> getAllCustomer();
    Customer getCustomer(String ID);
    void updateCustomer(Customer customer);
    void deleteCustomer(Customer customer);
    void addCustomer(Customer customer);
}
