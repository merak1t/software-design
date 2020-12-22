package servlet;

import product.ProductDao;
import product.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static product.ProductHTML.printInfoHTML;
import static product.ProductHTML.printProductHTML;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final ProductDao productDao;

    public QueryServlet(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            try {
                Optional<Product> maxPriceProduct = productDao.findMaxPriceProduct();
                printProductHTML(maxPriceProduct,"Product with maximal price: ", response.getWriter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                Optional<Product> minPriceProduct = productDao.findMinPriceProduct();
                printProductHTML(minPriceProduct,"Product with minimal price: ", response.getWriter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                long summaryPrice = productDao.getPricesSum();
                printInfoHTML(summaryPrice,"Summary price: ", response.getWriter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                int count = productDao.getProductsCount();
                printInfoHTML(count,"Products count: ", response.getWriter());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}