
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import product.Product;
import product.ProductDao;
import servlet.AbstractProductServlet;
import servlet.AddProductServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddProductServletTest {

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
        servlet = new AddProductServlet(productDao);
        when(servletRequest.getParameter("name"))
                .thenReturn("beer");
        when(servletRequest.getParameter("price"))
                .thenReturn("228");


        when(servletResponse.getWriter())
                .thenReturn(printer);
    }


    @Test
    public void addProductServletTest() throws SQLException {

        servlet.doGet(servletRequest, servletResponse);

        ArgumentCaptor<Product> product = ArgumentCaptor.forClass(Product.class);
        verify(productDao).insert(product.capture());

        printer.flush();
        assertThat(stringWriter.toString()).isEqualTo("OK" + System.lineSeparator());
        assertThat(product.getValue()).isNotEqualTo(new Product("water", 30));
        assertThat(product.getValue()).isEqualTo(new Product("beer", 228));

    }

}
