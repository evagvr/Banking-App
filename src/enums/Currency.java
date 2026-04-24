package enums;

public enum Currency {
    RON("lei"),
    EUR("€"),
    USD("$"),
    GBP("£");
    private String symbol;
    private Currency(String symbol){
        this.symbol = symbol;
    }
}
