import io.netty.handler.codec.http.QueryStringDecoder;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RxNettyHttpServer {
    private final static HashMap<String, Currency> stringCurrencyHashMap = new HashMap<>() {{
        put("RUB", Currency.getInstance("RUB"));
        put("USD", Currency.getInstance("USD"));
        put("EUR", Currency.getInstance("EUR"));
    }};

    public static void main(final String[] args) {
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {

                    String query = req.getDecodedPath().substring(1);
                    Map<String, List<String>> parameters = new QueryStringDecoder(req.getUri()).parameters();

                    Observable<String> response;

                    switch (query) {
                        case "register" -> {
                            System.out.println("register");
                            response = RxMongoDriver
                                    .getUsers()
                                    .exists(user -> user.getLogin().equals(parameters.get("login").get(0)))
                                    .flatMap(exists -> {
                                                String login = parameters.get("login").get(0);
                                                if (login.isEmpty()) {
                                                    return Observable.just("Empty login!");
                                                }
                                                if (exists) {
                                                    return Observable.just("User with login " + login
                                                            + " is already registered!");
                                                } else {
                                                    String currencyStr = parameters.get("currency").get(0);
                                                    if (!stringCurrencyHashMap.containsKey(currencyStr)) {
                                                        return Observable.just("Invalid currency: " + currencyStr);
                                                    }
                                                    User user = new User(login, stringCurrencyHashMap.get(currencyStr));
                                                    return RxMongoDriver
                                                            .addUser(user)
                                                            .map(success -> user.toString() +
                                                                    " successfully registered.");
                                                }
                                            }
                                    );
                        }
                        case "add" -> {
                            response = RxMongoDriver
                                    .getProducts()
                                    .exists(product -> product.getName().equals(parameters.get("name").get(0)))
                                    .flatMap(exists -> {
                                                String name = parameters.get("name").get(0);
                                                if (exists) {
                                                    return Observable.just("Product with name " + name
                                                            + " is already added!");
                                                } else {
                                                    String currencyStr = parameters.get("currency").get(0);
                                                    try {
                                                        double price = Double.parseDouble(parameters.get("price").get(0));
                                                        if (price < 0) {
                                                            return Observable.just("Negative price is passed!");
                                                        }
                                                        if (!stringCurrencyHashMap.containsKey(currencyStr)) {
                                                            return Observable.just("Invalid currency: " + currencyStr);
                                                        }
                                                        Currency currency = stringCurrencyHashMap.get(currencyStr);
                                                        Product product = new Product(name, price, currency);
                                                        return RxMongoDriver
                                                                .addProduct(product)
                                                                .map(success -> product.toString() +
                                                                        " successfully added.");
                                                    } catch (NumberFormatException e) {
                                                        return Observable.just("Invalid price is passed!");
                                                    } catch (IllegalArgumentException e) {
                                                        return Observable.just("Invalid currency: " + currencyStr);
                                                    }
                                                }
                                            }
                                    );
                        }
                        case "show" -> {
                            response = RxMongoDriver
                                    .getUsers()
                                    .filter(user -> user.getLogin().equals(parameters.get("login").get(0)))
                                    .firstOrDefault(new User("", "RUB"))
                                    .flatMap(user -> {
                                                if (user.getLogin().isEmpty()) {
                                                    return Observable.just("Invalid login is passed!");
                                                } else {
                                                    Currency currency = user.getCurrency();
                                                    return RxMongoDriver
                                                            .getProducts()
                                                            .map(product -> Product.pretty(product, currency));
                                                }
                                            }
                                    );
                        }
                        case "drop" -> {
                            response = RxMongoDriver
                                    .drop()
                                    .map(success -> "Successfully dropped");
                        }
                        default -> {
                            response = null;
                        }
                    }

                    return resp.writeString(response);
                })
                .awaitShutdown();
    }

}