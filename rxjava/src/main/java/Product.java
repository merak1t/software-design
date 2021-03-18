import org.bson.Document;

import java.util.Currency;


public class Product {

    private final String name;
    private final Price price;


    Product(String name, double price, Currency currency) {
        this.name = name;
        this.price = new Price(price, currency);
    }

    public Product(Document doc) {
        this(
                doc.getString("name"),
                doc.getDouble("price"),
                Currency.getInstance(doc.getString("currency"))
        );
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price.getValue();
    }

    public double getPrice(Currency cur) {
        return price.convert(cur);
    }


    public Document toDoc() {
        return new Document()
                .append("name", name)
                .append("price", price.getValue())
                .append("currency", price.getCurrency().toString());
    }

    @Override
    public String toString() {
        return String.format("Product name: %s, price: %.2f %s",
                name, price.getValue(), price.getCurrency());
    }

    public static String pretty(Product product, Currency currency) {
        return String.format("Product name: %s, price: %.2f %s\n",
                product.getName(), product.getPrice(currency), currency);
    }

}