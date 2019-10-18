package Model;

import java.util.Map;

public interface BankManagerDAO {
    Map<String, BankMananger> getAllBankManager();
    BankMananger getBankManager(String ID);
    void updateManager(BankMananger bankMananger);
    void deleteManager(BankMananger bankMananger);
}
