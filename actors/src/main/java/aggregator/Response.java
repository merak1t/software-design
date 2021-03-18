package aggregator;

import java.net.URL;

public class Response {

    private final SearchEngines engine;
    private final URL url;
    private final String header;

    public Response(SearchEngines engine, URL url, String header) {
        this.engine = engine;
        this.url = url;
        this.header = header;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        switch (engine.toString()) {
            case "yandex.ru" -> {
                res.append(Utils.ANSI_YANDEX);
            }
            case "google.com" -> {
                res.append(Utils.ANSI_GOOGLE);
            }
            case "bing.com" -> {
                res.append(Utils.ANSI_BING);
            }
            default -> {
                res.append(Utils.ANSI_BLACK);
            }
        }
        res.append("From ").append(engine.toString())
                .append(": ").append(url.toString())
                .append(" ").append(header)
                .append(Utils.ANSI_RESET);
        return res.toString();
    }

}
