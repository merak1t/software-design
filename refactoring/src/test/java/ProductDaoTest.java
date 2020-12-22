
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import product.Product;
import product.ProductDao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static product.Utils.createTables;
import static product.Utils.dropTables;

public class ProductDaoTest {

    private static List<Product> productList;

    private ProductDao productDao;

    @Before
    public void setUp() throws SQLException {
        productList = Arrays.asList(
                new Product("beer", 300),
                new Product("water", 30),
                new Product("cola", 90)
        );
        productDao = new ProductDao();
        createTables();
    }

    @After
    public void cleanUp() throws SQLException {
        dropTables();
    }

    private void addProducts(List<Product> products) throws SQLException {
        for (Product product : products) {
            productDao.insert(product);
        }
    }

    @Test
    public void getFromEmptyDatabaseTest() throws SQLException {
        assertThat(productDao.getProducts()).isEmpty();
    }

    @Test
    public void findFromEmptyDatabaseTest() throws SQLException {
        assertThat(productDao.findMinPriceProduct()).isEmpty();
    }

    @Test
    public void insertTest() throws SQLException {
        addProducts(productList);
        assertThat(productDao.getProducts()).containsExactlyInAnyOrder(productList.toArray(new Product[3]));
    }

    @Test
    public void findMaxPriceProductTest() throws SQLException {
        addProducts(productList);
        Optional<Product> product = productDao.findMaxPriceProduct();
        assertThat(product).isPresent();
        assertThat(product.get()).isEqualTo(new Product("beer", 300));
    }

    @Test
    public void findMinPriceProductTest() throws SQLException {
        addProducts(productList);
        Optional<Product> product = productDao.findMinPriceProduct();
        assertThat(product).isPresent();
        assertThat(product.get()).isEqualTo(new Product("water", 30));
    }

    @Test
    public void getPricesSumTest() throws SQLException {
        addProducts(productList);
        assertThat(productDao.getPricesSum()).isEqualTo(420);
    }

    @Test
    public void getProductsCountTest() throws SQLException {
        addProducts(productList);
        assertThat(productDao.getProductsCount()).isEqualTo(3);
    }

}
