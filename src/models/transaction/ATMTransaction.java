package models.transaction;

import enums.AtmOperationType;
import enums.Currency;

public class ATMTransaction extends Transaction {
    private AtmOperationType atmOperationType;
    public ATMTransaction(String iban, double amount, Currency currency, String description, AtmOperationType atmOperationType){
        super(iban, amount, currency, description);
        this.atmOperationType = atmOperationType;
    }
    public AtmOperationType getAtmOperationType(){
        return atmOperationType;
    }
}
