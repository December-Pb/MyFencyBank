package Model;

public class DollarCurrency extends Currency{
    public DollarCurrency(String name, double deposit) {
        super(name, deposit);
        super.setTransferRate(8);
    }
}
