package moneyexchange.Trading_Exchange;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import moneyexchange.CurrencyName;
import moneyexchange.LoginSignupPage;

public class OrderProcessor {
    private BlockingQueue<Order> sellOrders = new LinkedBlockingQueue<>();
    private BlockingQueue<Order> buyOrders = new LinkedBlockingQueue<>();

    private static OrderProcessor instance;

    public OrderProcessor() {
    }

    public static synchronized OrderProcessor getInstance() {
        if (instance == null) {
            instance = new OrderProcessor();
        }
        return instance;
    }

    public void addOrder(Order order) {
        if (order.getCommand() == Order.Command.BUY) {
            matchOrder(order, sellOrders, buyOrders);
        } else {
            matchOrder(order, buyOrders, sellOrders);
        }
    }

    private void matchOrder(Order newOrder, BlockingQueue<Order> oppositeOrders, BlockingQueue<Order> sameOrders) {
        for (Order oppositeOrder : oppositeOrders) {
            if (isMatchingOrder(newOrder, oppositeOrder)) {
                executeTrade(newOrder, oppositeOrder);
                oppositeOrders.remove(oppositeOrder);
                return;
            }
        }

        // If no match found, add to the appropriate queue
        try {
            sameOrders.put(newOrder);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isMatchingOrder(Order newOrder, Order oppositeOrder) {
        // Check if the orders can be matched
        return newOrder.sourceCurrency == oppositeOrder.sourceCurrency &&
                newOrder.getTargetCurrency() == oppositeOrder.getTargetCurrency() &&
                newOrder.getSourcePrice() <= oppositeOrder.getTargetPrice();
    }


    private void executeTrade(Order buyOrder, Order sellOrder) {
        // // Step 1: Calculate the exchange rate
        double pairExchangeRate = buyOrder.getTargetPrice() / buyOrder.getSourcePrice();

        // Step 2: Calculate the equivalent amount in target currency
        int amount = Math.min(buyOrder.getAmount(), sellOrder.getAmount());
        double equalAmount = amount * pairExchangeRate;

        // // Step 3: Check if both buyer and seller have enough balance
        // if (!checkBalanceBuyer(buyOrder, equalAmount) || !checkBalanceSeller(sellOrder, amount)) {
        //     System.out.println("Insufficient balance for the trade");
        //     return;
        // }

        // // Step 4: Execute the trade
        // synchronized (this) {
        //     bankOperation(buyOrder, amount, sellOrder, equalAmount);
        // }

        // Update the remaining amount in the orders
        buyOrder.setAmount(buyOrder.getAmount() - amount);
        sellOrder.setAmount(sellOrder.getAmount() - amount);

        // // Remove orders if fully executed
        // if (buyOrder.getAmount() == 0) {
        //     buyOrders.remove(buyOrder);
        // }
        // if (sellOrder.getAmount() == 0) {
        //     sellOrders.remove(sellOrder);
        // }

        // // Log the executed trade
        // System.out.println("Trade executed: " + buyOrder + " <-> " + sellOrder);
    }

    // buyer should have enough target
    private boolean checkBalanceBuyer(Order order, double equalAmount){
        if (LoginSignupPage.database == null) {
            System.err.println("Database is null in checkBalanceBuyer");
            return false;
        }

        CurrencyName target = order.getTargetCurrency();

        switch (target) {
            case USD:
                return LoginSignupPage.database.hasUSD(order.username, equalAmount);
            case EUR:
                return LoginSignupPage.database.hasEUR(order.username, equalAmount);
            case TOMAN:
                return LoginSignupPage.database.hasTOMAN(order.username, equalAmount);
            case YEN:
                return LoginSignupPage.database.hasYEN(order.username, equalAmount);
            case GBP:
                return LoginSignupPage.database.hasGBP(order.username, equalAmount);
        }

        System.out.println("invalid currency!");
        return false;
    }

    // seller should have enough source
    private boolean checkBalanceSeller(Order order, double equalAmount){
        CurrencyName source = order.sourceCurrency;

        switch (source) {
            case USD:
                return LoginSignupPage.database.hasUSD(order.username, equalAmount);
            case EUR:
                return LoginSignupPage.database.hasEUR(order.username, equalAmount);
            case TOMAN:
                return LoginSignupPage.database.hasTOMAN(order.username, equalAmount);
            case YEN:
                return LoginSignupPage.database.hasYEN(order.username, equalAmount);
            case GBP:
                return LoginSignupPage.database.hasGBP(order.username, equalAmount);
        }

        System.out.println("invalid currency!");
        return false;
    }

    private void bankOperation(Order buyOrder, int amount, Order sellOrder, double equalAmount){
        CurrencyName source = sellOrder.sourceCurrency;
        CurrencyName target = buyOrder.getTargetCurrency();

        // get source currency from seller and give source currency to buyer
        switch (source) {
            case USD:
                LoginSignupPage.database.updateUSD(sellOrder.username, "admin", -amount);
                LoginSignupPage.database.updateUSD(buyOrder.username, "admin",  amount);
                break;
            case EUR:
                LoginSignupPage.database.updateEUR(sellOrder.username, "admin", -amount);
                LoginSignupPage.database.updateEUR(buyOrder.username, "admin", amount);
                break;
            case TOMAN:
                LoginSignupPage.database.updateTOMAN(sellOrder.username, "admin", -amount);
                LoginSignupPage.database.updateTOMAN(buyOrder.username, "admin", amount);
                break;
            case YEN:
                LoginSignupPage.database.updateYEN(sellOrder.username, "admin", -amount);
                LoginSignupPage.database.updateYEN(buyOrder.username, "admin", amount);
                break;
            case GBP:
                LoginSignupPage.database.updateGBP(sellOrder.username, "admin", -amount);
                LoginSignupPage.database.updateGBP(buyOrder.username, "admin", amount);
                break;
        }

        // give target currency to seller and get target currency from buyer
        switch (target) {
            case USD:
                LoginSignupPage.database.updateUSD(sellOrder.username, "admin", equalAmount);
                LoginSignupPage.database.updateUSD(buyOrder.username, "admin", -equalAmount);
                break;
            case EUR:
                LoginSignupPage.database.updateEUR(sellOrder.username, "admin", equalAmount);
                LoginSignupPage.database.updateEUR(buyOrder.username, "admin", -equalAmount);
                break;
            case TOMAN:
                LoginSignupPage.database.updateTOMAN(sellOrder.username, "admin", equalAmount);
                LoginSignupPage.database.updateTOMAN(buyOrder.username, "admin", -equalAmount);
                break;
            case YEN:
                LoginSignupPage.database.updateYEN(sellOrder.username, "admin", equalAmount);
                LoginSignupPage.database.updateYEN(buyOrder.username, "admin", -equalAmount);
                break;
            case GBP:
                LoginSignupPage.database.updateGBP(sellOrder.username, "admin", equalAmount);
                LoginSignupPage.database.updateGBP(buyOrder.username, "admin", -equalAmount);
                break;
        }
    }

    public BlockingQueue<Order> getBuyOrders() {
        return buyOrders;
    }

    public BlockingQueue<Order> getSellOrders() {
        return sellOrders;
    }
}
