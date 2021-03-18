import java.io.IOException;
import java.util.Currency;

public class Price {

    private final Currency currency;

    private final double value;

    Price(double val, Currency cur) {
        this.value = val;
        currency = cur;
    }

    public double getValue() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }


    public double convert(Currency toCurrency) {
        try {
            var rate = MannysConverterAPI.rate(this.currency, toCurrency);
            return this.value * rate;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }

}