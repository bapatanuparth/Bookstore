
package business;

import business.book.Book;
import business.book.BookDao;
import business.book.BookDaoJdbc;
import business.category.CategoryDao;
import business.category.CategoryDaoJdbc;
import business.customer.CustomerDao;
import business.customer.CustomerDaoJdbc;
import business.order.*;

import java.util.List;

public class ApplicationContext {

    // TODO Add field and complete the getter for bookDao

    private CategoryDao categoryDao;

    private BookDao bookDao;

    private OrderService orderService;

    private OrderDao orderDao;

    private LineItemDao lineItemDao;

    private CustomerDao customerDao;

    public static ApplicationContext INSTANCE = new ApplicationContext();

    private ApplicationContext() {
        categoryDao = new CategoryDaoJdbc();
        bookDao= new BookDaoJdbc() ;
        orderService = new DefaultOrderService();

        lineItemDao= new LineItemDaoJdbc();
        customerDao= new CustomerDaoJdbc();
        orderDao= new OrderDaoJdbc();
        ((DefaultOrderService)orderService).setBookDao(bookDao);
        ((DefaultOrderService)orderService).setOrderDao(orderDao);
        ((DefaultOrderService)orderService).setCustomerDao(customerDao);
        ((DefaultOrderService)orderService).setLineItemDao(lineItemDao);

    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public BookDao getBookDao() { return bookDao; }

    public OrderService getOrderService(){return orderService;}
}
