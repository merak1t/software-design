import java.io.IOException;
import java.util.Currency;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MannysConverterAPITest {
    private static final Currency U_S_DOLLARS
            = Currency.getInstance(Locale.US);
    private static final double TEST_DELTA = 0.01;

    @Test
    public void testUSDtoXCD() throws IOException {
        Currency eastCaribDollars = Currency.getInstance("XCD");
        double expected = 2.7;
        double actual = 0;
        actual = MannysConverterAPI.rate(U_S_DOLLARS,
                eastCaribDollars);
        assertEquals(expected, actual, TEST_DELTA);
    }

    @Test
    public void testXCDtoUSD() throws IOException {
        Currency eastCaribDollars = Currency.getInstance("XCD");
        double expected = 0.37;
        double actual = MannysConverterAPI.rate(eastCaribDollars,
                U_S_DOLLARS);
        assertEquals(expected, actual, TEST_DELTA);
    }

    @Test
    public void testRate() throws IOException {
        System.out.println("rate");
        Currency yen = Currency.getInstance(Locale.JAPAN);
        System.out.println(Currency.getInstance("RUB").toString());
        double dollarsToYenRate
                = MannysConverterAPI.rate(U_S_DOLLARS, yen);
        System.out.println(MannysConverterAPI.rate(U_S_DOLLARS, Currency.getInstance("RUB")));
        double yenToDollarsRate
                = MannysConverterAPI.rate(yen, U_S_DOLLARS);
        System.out.println("Dollars to yen rate is said to be "
                + dollarsToYenRate);
        System.out.println("Yen to dollars rate is said to be "
                + yenToDollarsRate);
        double expected = 1.0;
        double actual = dollarsToYenRate * yenToDollarsRate;
        assertEquals(expected, actual, TEST_DELTA);
    }
}