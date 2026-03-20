package models;

public class OnlineTransaction extends Transaction{
    private final String website;
    private final String ipAddress;
    public OnlineTransaction(String accountId, double amount, String category, String website, String ipAddress){
        super(accountId, amount, category);
        this.website = website;
        this.ipAddress = ipAddress;
    }
    @Override
    public double calculateFee() {
        return amount * 0.01;
    }
}
