package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Manipulate customer's data
 */
public class CustomerDAOImpl implements CustomerDAO {

    private static CustomerDAOImpl customerDAOImpl = new CustomerDAOImpl();
    private Map<String, Customer> customerMap;

    public static CustomerDAOImpl getInstance() {
        return customerDAOImpl;
    }
    private CustomerDAOImpl() {
        customerMap = new HashMap<>();
        Customer c1 = new Customer("U48621793", "jiaqsun@bu.edu", "123456", "Jiaqian", "Sun", "U48621793");
        Customer c2 = new Customer("U48621794", "jiaqsun@bu.edu", "123456", "Jiaqian", "Sun", "U48621794");
        Customer c3 = new Customer("U48621795", "jiaqsun@bu.edu", "123456", "Jiaqian", "Sun", "U48621795");
        Customer c4 = new Customer("U48621796", "jiaqsun@bu.edu", "123456", "Jiaqian", "Sun", "U48621796");
        Customer c5 = new Customer("1", "a", "1", "c", "b", "1");
        customerMap.put(c1.getID(), c1);
        customerMap.put(c2.getID(), c2);
        customerMap.put(c3.getID(), c3);
        customerMap.put(c4.getID(), c4);
        customerMap.put(c5.getID(), c5);
    }

    @Override
    public Map<String, Customer> getAllCustomer() {
        return customerMap;
    }

    @Override
    public Customer getCustomer(String ID) {
        return customerMap.get(ID);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerMap.put(customer.getID(), customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerMap.remove(customer);
    }

    @Override
    public void addCustomer(Customer customer) {
        customerMap.put(customer.getID(), customer);
    }
}
