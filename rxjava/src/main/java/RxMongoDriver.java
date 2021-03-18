
import com.mongodb.rx.client.*;
import org.bson.Document;
import rx.Observable;

public class RxMongoDriver {

    private static final MongoClient client = MongoClients.create("mongodb://localhost:27017");
    private static final MongoDatabase DATABASE = client.getDatabase("shop");
    private static final MongoCollection<Document> USER_COLLECTION = DATABASE.getCollection("users");
    private static final MongoCollection<Document> PRODUCT_COLLECTION = DATABASE.getCollection("products");

    public static void main(String[] args) {

    }

    public static Observable<Success> addUser(User user) {
        return USER_COLLECTION.insertOne(user.toDoc());
    }

    public static Observable<Success> addProduct(Product product) {
        return PRODUCT_COLLECTION.insertOne(product.toDoc());
    }

    public static Observable<User> getUsers() {
        return USER_COLLECTION.find().toObservable().map(User::new);
    }

    public static Observable<Product> getProducts() {
        return PRODUCT_COLLECTION.find().toObservable().map(Product::new);
    }

    public static Observable<Success> drop() {
        return DATABASE.drop();
    }

}