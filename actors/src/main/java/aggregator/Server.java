package aggregator;

import javafx.util.Pair;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Server {

    private static int timeout_ms;
    private static boolean isDelayed;

    public Server() {
        Server.timeout_ms = 100;
        Server.isDelayed = false;
    }

    public void setTimeout(int timeout_ms) {
        Server.timeout_ms = timeout_ms;
    }

    public void setSpeed(boolean delay) {
        Server.isDelayed = delay;
    }

    public static JSONObject process(URL url) {
        long timeStart = System.currentTimeMillis();
        Map<String, String> queries = new HashMap<>();
        for (String query : url.getQuery().split("&")) {
            String[] values = query.split("=");
            queries.put(values[0], values[1]);
        }
        String host = url.getHost();
        JSONObject json = new JSONObject();
        String search = queries.get("query");
        for (int i = 0; i < Integer.parseInt(queries.get("number")); i++) {
            Pair<String, String> response = generateResponse(host, search);
            assert response != null;
            json.put(response.getKey(), response.getValue());
        }
        if (!host.equals("yandex.ru") || !isDelayed) {
            while (System.currentTimeMillis() < timeStart + timeout_ms) {
            }
        }
        return json;
    }

    private static Pair<String, String> generateResponse(String host, String query) {
        switch (host) {
            case "yandex.ru" -> {
                return new Pair<>(generateSite(7, 0.3, 0.1),
                        query + " " + generateText(5) + " " + query + " " + generateText(5));
            }
            case "google.com" -> {
                return new Pair<>(generateSite(6, 0.7, 0.5),
                        generateText(7) + " " + query + " " + generateText(3) + " " + query + " " + generateText(2));
            }
            case "bing.com" -> {
                return new Pair<>(generateSite(8, 0.9, 0.2),
                        generateText(3) + " " + query + " " + generateText(3) + " " + query);
            }
            default -> {
                return null;
            }
        }
    }

    private static String generateSite(int length, double typeSiteProb, double typeNameProb) {
        var random = new Random();
        double com = random.nextDouble();
        String siteName = "https://" + generateAlphanumeric(length, typeNameProb);
        return com < typeSiteProb ? siteName + ".com" : siteName + ".ru";
    }

    private static String generateText(int words) {
        var stringBuilder = new StringBuilder();
        var random = new Random();
        for (int i = 0; i < words; i++) {
            stringBuilder.append(generateAlphanumeric(random.nextInt(10) + 1, 0)).append(" ");
        }
        return stringBuilder.toString().trim();
    }

    private static String generateAlphanumeric(int length, double typeProb) {
        var stringBuilder = new StringBuilder();
        var random = new Random();
        for (int i = 0; i < length; i++) {
            double num = random.nextDouble();
            if (num < typeProb) {
                stringBuilder.append((char) (random.nextInt(10) + '0'));
            } else {
                stringBuilder.append((char) (random.nextInt(26) + 'a'));
            }
        }
        return stringBuilder.toString();
    }

}