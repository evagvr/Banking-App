package models.transaction;

import enums.Currency;

public class TransferTransaction extends Transaction {
    private String destinationIban;
    public TransferTransaction(String iban, double amount, Currency currency, String description, String destinationIban){
        super(iban, amount, currency, description);
        this.destinationIban = destinationIban;
    }

    public String getDestinationIban() {
        return destinationIban;
    }
    public boolean isInternalTransaction(){
        return destinationIban.regionMatches(4,"BSFB", 0, 4);
    }
}
