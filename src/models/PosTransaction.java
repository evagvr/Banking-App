package models;

public class PosTransaction extends Transaction{
    private final String merchantName;
    private final String merchantLocation;

    public PosTransaction(String accountId, double amount, String category, String merchantName, String merchantLocation){
        super(accountId, amount, category);
        this.merchantName = merchantName;
        this.merchantLocation = merchantLocation;
    }
    @Override
    public double calculateFee() {
        return 0.0;
    }
}
