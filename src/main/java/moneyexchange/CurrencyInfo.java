package moneyexchange;

public class CurrencyInfo {
    private String name;
    private String price;
    private String date;
    private String time;
    private double maxValue;
    private double minValue;
    private String previousPrice;
    private String change;

    public CurrencyInfo(String name, String price, String date, String time) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.time = time;
        this.maxValue = Double.parseDouble(price);
        this.minValue = Double.parseDouble(price);
        this.previousPrice = price;
        this.change = "0";
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.previousPrice = this.price;
        this.price = price;
        double priceValue = Double.parseDouble(price);
        if (priceValue > maxValue) {
            maxValue = priceValue;
        }
        if (priceValue < minValue) {
            minValue = priceValue;
        }
        calculateChange();
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public String getPreviousPrice() {
        return previousPrice;
    }

    public String getChange() {
        return change;
    }

    private void calculateChange() {
        double previous = Double.parseDouble(previousPrice);
        double current = Double.parseDouble(price);
        double difference = current - previous;
        this.change = String.format("%.2f", difference);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
