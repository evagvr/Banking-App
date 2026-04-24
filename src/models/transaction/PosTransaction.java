package models.transaction;

import enums.Currency;
import enums.MerchantCategory;

public class PosTransaction extends Transaction {
    private final String merchantName;
    private final MerchantCategory merchantCategory;

    public PosTransaction(String iban, double amount, Currency currency, String description, String merchantName, MerchantCategory merchantCategory){
        super(iban, amount, currency, description);
        this.merchantName = merchantName;
        this.merchantCategory = merchantCategory;
    }
    public String getMerchantName(){
        return merchantName;
    }
    public MerchantCategory getMerchantCategory() {
        return merchantCategory;
    }
    public String getSourceAccountIban(){ return getIban();}
}
