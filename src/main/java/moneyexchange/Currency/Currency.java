package moneyexchange.Currency;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import moneyexchange.CSVToArray;
import moneyexchange.CurrencyName;
import moneyexchange.PriceVsTime;
import moneyexchange.TimePeriod;

public class Currency {
    public final int pos;
    protected CSVToArray data;
    protected String[][] str;
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime time = LocalTime.now();

    protected CurrencyName name;
    protected String nameString = "USD";

    public static int currentIndex = 1;
    protected float currentValue;
    private float previousValue;

    private int maxLastIndex = 1;
    protected float maxValue = 0;


    private int minLastIndex = 1;
    protected float minValue = Float.MAX_VALUE;

    protected float change;

    protected String dateOfIssue;

    public Currency(CurrencyName currencyName){
        switch (currencyName) {
            case USD:
                pos = 2;
                break;
            case EUR:
                pos = 3;
                break;
            case TOMAN:
                pos = 4;
                break;
            case YEN:
                pos = 5;
                break;
            case GBP:
                pos = 6;
                break;
            default:
                pos = -1;
                break;
        }

        this.name = currencyName;
        setNameString();
        data = new CSVToArray("/data/currency_prices.csv");
        str = data.getDataArray(TimePeriod.ONE_MINUTE);
        dateOfIssue = str[1][0] + " " + str[1][1];

        updateData(time);
    }

    private void setNameString(){
        switch (name) {
            case USD:
                nameString = "USD";
                break;
            case EUR:
                nameString = "EUR";
                break;
            case TOMAN:
                nameString = "TOMAN";
                break;
            case YEN:
                nameString = "YEN";
                break;
            case GBP:
                nameString = "GBP";
                break;
        }
    }


    protected float getMax(String timeString) {

        for (int i = maxLastIndex; i < str.length; i++) {
            timeString = time.format(timeFormatter);

            if (PriceVsTime.compareTime(timeString, str[i][1]) <= 0) break;

            float value = Float.parseFloat(str[i][pos]);
            if (value > maxValue) {
                maxValue = value;
                maxLastIndex = i;
            }
        }

        return maxValue;
    }

    protected float getMin(String timeString) {

        for (int i = minLastIndex; i < str.length; i++) {
            timeString = time.format(timeFormatter);

            if (PriceVsTime.compareTime(timeString, str[i][1]) <= 0) break;

            float value = Float.parseFloat(str[i][pos]);
            if (value < minValue) {
                minValue = value;
                minLastIndex = i;
            }
        }

        return minValue;
    }

    protected float getCurrentValue(String timeString){

        for (int i = currentIndex; i < str.length; i++){
            timeString = time.format(timeFormatter);

            if (PriceVsTime.compareTime(timeString, str[i][1]) == 0){
                this.currentValue = Float.parseFloat(str[i][pos]);
                currentIndex = i;
                break;
            }
        }

        return currentValue;
    }

    protected float getChange(String timeString) {
        float currentValue = 0;
        float previousValue = 0;
        boolean foundCurrent = false;
        boolean foundPrevious = false;
        String stringTime;

        for (int i = 1; i < str.length; i++) {
            stringTime = time.format(timeFormatter);

            if (PriceVsTime.compareTime(stringTime, str[i][1]) == 0) {
                currentValue = Float.parseFloat(str[i][pos]);
                foundCurrent = true;
            }

            if (i > 1) {
                previousValue = Float.parseFloat(str[i - 1][pos]);
                foundPrevious = true;
            }

            if (foundCurrent && foundPrevious) {
                break;
            }
        }

        if (foundCurrent && foundPrevious) {
            return ((currentValue - previousValue) / previousValue) * 100;
        } else {
            throw new IllegalStateException("Required time values not found in the data.");
        }
    }

    public void updateData(LocalTime time){
        String timeString = time.format(timeFormatter);
        minValue = getMin(timeString);
        maxValue = getMax(timeString);
        currentValue = getCurrentValue(timeString);
        change = getChange(timeString);

        System.out.println(timeString + " " + currentValue + " " +  maxValue + " " + minValue + " " + change);
    }
}
