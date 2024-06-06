package moneyexchange;

public class CurrencyInfo {
    private String currency;
    private double price;
    private double change;
    private double highest;
    private double lowest;

    public CurrencyInfo(String currency, double price, double change, double highest, double lowest) {
        this.currency = currency;
        this.price = price;
        this.change = change;
        this.highest = highest;
        this.lowest = lowest;
    }

    public String getCurrency() {
        return currency;
    }
}