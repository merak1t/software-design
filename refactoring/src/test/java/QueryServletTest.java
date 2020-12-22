
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import product.Product;
import product.ProductDao;
import servlet.AbstractProductServlet;
import servlet.QueryServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class QueryServletTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private HttpServletRequest servletRequest;

    @Mock
    private HttpServletResponse servletResponse;

    private AbstractProductServlet servlet;
    StringWriter stringWriter = new StringWriter();
    PrintWriter printer = new PrintWriter(stringWriter);

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);
        servlet = new QueryServlet(productDao);

        when(servletResponse.getWriter())
                .thenReturn(printer);
    }

    @Test
    public void findMaxPriceTest() throws SQLException {
        when(servletRequest.getParameter("command"))
                .thenReturn("max");
        when(productDao.findMaxPriceProduct())
                .thenReturn(Optional.of(new Product("beer", 228)));

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "<h1>Product with max price: </h1>" +
                        "beer\t228</br>" +
                        "</body></html>"
        );
    }

    @Test
    public void findMinPriceTest() throws SQLException {
        when(servletRequest.getParameter("command"))
                .thenReturn("min");
        when(productDao.findMinPriceProduct())
                .thenReturn(Optional.of(new Product("beer", 228)));


        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "<h1>Product with min price: </h1>" +
                        "beer\t228</br>" +
                        "</body></html>"
        );
    }

    @Test
    public void getPriceSumTest() throws SQLException {
        when(servletRequest.getParameter("command"))
                .thenReturn("sum");
        when(productDao.getPricesSum())
                .thenReturn(Long.valueOf(666));
        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "Summary price: " +
                        "666" +
                        "</body></html>"
        );
    }

    @Test
    public void getCountTest() throws SQLException {
        when(servletRequest.getParameter("command"))
                .thenReturn("count");
        when(productDao.getProductsCount())
                .thenReturn(2);
        servlet.doGet(servletRequest, servletResponse);

        printer.flush();
        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "Number of products: " +
                        "2" +
                        "</body></html>"
        );
    }
}