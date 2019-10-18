package Model;

public class GBPCurrency extends Currency {
    public GBPCurrency(String name, double deposit) {
        super(name, deposit);
        super.setTransferRate(10);
    }
}
