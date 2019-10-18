package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Include all the manipulation of bank manager data
 */
public class BankManagerDAOImpl implements BankManagerDAO {

    private static BankManagerDAOImpl bankManagerDAOImpl = new BankManagerDAOImpl();
    private Map<String, BankMananger> bankManangerMap;

    public static BankManagerDAOImpl getInstance() {
        return bankManagerDAOImpl;
    }

    private BankManagerDAOImpl() {
        bankManangerMap = new HashMap<>();
        BankMananger bankMananger = new BankMananger("U48621793", "Jiaqian", "123456");
        BankMananger bankMananger1 = new BankMananger("1", "1", "1");
        bankManangerMap.put(bankMananger1.getID(), bankMananger1);
        bankManangerMap.put(bankMananger.getID(), bankMananger);
    }

    @Override
    public Map<String, BankMananger> getAllBankManager() {
        return bankManangerMap;
    }

    @Override
    public BankMananger getBankManager(String ID) {
        return bankManangerMap.get(ID);
    }

    @Override
    public void updateManager(BankMananger bankMananger) {
        bankManangerMap.put(bankMananger.getID(), bankMananger);
    }

    @Override
    public void deleteManager(BankMananger bankMananger) {
        bankManangerMap.remove(bankMananger);
    }
}
