package moneyexchange.Trading_Swap;

import moneyexchange.CurrencyName;
import java.io.Serializable;

public class SwapRequest implements Serializable {
    private CurrencyName sourceCurr;
    private CurrencyName targetCurr;
    private double exchangeRate;

    public SwapRequest(CurrencyName sourceCurr, CurrencyName targetCurr) {
        this.sourceCurr = sourceCurr;
        this.targetCurr = targetCurr;
    }

    public CurrencyName getSourceCurr() {
        return sourceCurr;
    }

    public CurrencyName getTargetCurr() {
        return targetCurr;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
