package moneyexchange;

public class CurrencyInfo {
    private String name;
    private String price;

    public CurrencyInfo(String name, String price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
