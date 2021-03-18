import java.io.InputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Currency;
import java.util.Scanner;
class MannysConverterAPI {
    private static final String API_KEY
            = "YOUR_API_KEY_HERE";
    private static final String USER_AGENT_ID = "Java/"
            + System.getProperty("java.version");

    static double rate(Currency from, Currency to)
            throws IOException {
        String queryPath
                = "https://free.currconv.com/api/v7/convert?q="
                + from.getCurrencyCode() + "_"
                + to.getCurrencyCode()
                + "&compact=ultra&apiKey=" + API_KEY;
        URL queryURL = new URL(queryPath);
        HttpURLConnection connection
                = (HttpURLConnection) queryURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", USER_AGENT_ID);
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) { // 200 is HTTP status OK
            InputStream stream
                    = (InputStream) connection.getContent();
            Scanner scanner = new Scanner(stream);
            String quote = scanner.nextLine();
            String number = quote.substring(quote.indexOf(':') + 1,
                    quote.indexOf('}'));
            return Double.parseDouble(number);
        } else {
            String excMsg = "Query " + queryPath
                    + " returned status " + responseCode;
            throw new RuntimeException(excMsg);
        }
    }
}