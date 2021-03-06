package servlet;

import product.ProductDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static product.ProductHTML.printProductsHTML;


/**
 * @author akirakozov
 */
public class GetProductsServlet extends AbstractProductServlet {

    public GetProductsServlet(ProductDao productDao) {
        super(productDao);
    }

    @Override
    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        printProductsHTML(productDao.getProducts(), response.getWriter());
    }

}