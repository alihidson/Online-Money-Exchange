package moneyexchange.Trading_Exchange;

import moneyexchange.CurrencyName;
import moneyexchange.HomePage;
import moneyexchange.LoginSignupPage;

public class Order {
    public final String name;
    public final String username;
    public final CurrencyName sourceCurrency;
    public double targetPrice = 1; // it is read by a file and at the time of make a deal it will be updated

    public enum Command {
        BUY, SELL
    }

    private Command command;
    private CurrencyName targetCurrency;
    private double sourcePrice;
    private int amount;

    // used when create a new order
    public Order(Command command, double sourcePrice, CurrencyName targetCurrency, int amount) {
        name = LoginSignupPage.firstNameProf;
        username = LoginSignupPage.UserName;

        switch (HomePage.identifyCurr) {
            case 2:
                sourceCurrency = CurrencyName.USD;
                break;
            case 3:
                sourceCurrency = CurrencyName.EUR;
                break;

            case 4:
                sourceCurrency = CurrencyName.TOMAN;
                break;

            case 5:
                sourceCurrency = CurrencyName.YEN;
                break;
            case 6:
                sourceCurrency = CurrencyName.GBP;
                break;

            default:
                sourceCurrency = CurrencyName.USD;
                break;
        }

        this.command = command;
        this.sourcePrice = sourcePrice;
        this.targetCurrency = targetCurrency;
        this.amount = amount;

    }

    // used when create a new order based on Server output
    public Order(String name, String username, CurrencyName sourceCurrency, Command command, double sourcePrice, CurrencyName targetCurrency, int amount){
        this.name = name;
        this.username = username;
        this.sourceCurrency = sourceCurrency;

        this.command = command;
        this.sourcePrice = sourcePrice;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return username + "," + name + "," + command + "," + sourceCurrency + "," + sourcePrice + "," + targetCurrency + "," + targetPrice + "," + amount;
    }

    public static Order fromString(String orderString) {
        String[] parts = orderString.split(",");
        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid order string: " + orderString);
        }

        String username = parts[0];
        String name = parts[1];
        Command command = Command.valueOf(parts[2]);
        CurrencyName sourceCurrency = CurrencyName.valueOf(parts[3]);
        double sourcePrice = Double.parseDouble(parts[4]);
        CurrencyName targetCurrency = CurrencyName.valueOf(parts[5]);
        double targetPrice = Double.parseDouble(parts[6]);
        int amount = Integer.parseInt(parts[7]);

        return new Order(name, username, sourceCurrency, command, sourcePrice, targetCurrency, amount);
    }

    public void setTargetCurrency(CurrencyName targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }

    public double getSourcePrice() {
        return sourcePrice;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public Command getCommand() {
        return command;
    }

    public int getAmount() {
        return amount;
    }

    public CurrencyName getTargetCurrency() {
        return targetCurrency;
    }

    public String getSourceCurrencyString() {
        String str = null;
        switch (sourceCurrency) {
            case USD:
                str = "USD";
                break;
            case EUR:
                str = "EUR";
                break;
            case TOMAN:
                str = "TOMAN";
                break;
            case YEN:
                str = "YEN";
                break;
            case GBP:
                str = "GBP";
                break;
        }
        return str;
    }
}