import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import product.Product;
import product.ProductDao;
import servlet.AbstractProductServlet;
import servlet.GetProductsServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GetProductsServletTest {

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
    public void setUp() throws SQLException, IOException {
        MockitoAnnotations.initMocks(this);
        servlet = new GetProductsServlet(productDao);
        when(productDao.getProducts())
                .thenReturn(Arrays.asList(
                        new Product("water", 30),
                        new Product("beer", 228)
                ));


        when(servletResponse.getWriter())
                .thenReturn(printer);
    }

    @Test
    public void getProductServletTest() {

        servlet.doGet(servletRequest, servletResponse);

        printer.flush();

        assertThat(stringWriter.toString()).isEqualToIgnoringNewLines(
                "<html><body>" +
                        "water\t30</br>" +
                        "beer\t228</br>" +
                        "</body></html>"
        );
    }

}