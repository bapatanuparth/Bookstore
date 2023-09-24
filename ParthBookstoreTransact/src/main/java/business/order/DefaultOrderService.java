package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private OrderDao orderDao;

	private LineItemDao lineItemDao;

	private CustomerDao customerDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}
	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}


	}
	private Date getDate(String monthString, String yearString) {
		//return new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
		String dateString = monthString +"/"+yearString;
		try{
			return formatter.parse(dateString);
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
	}

	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber() {
		Random random= new Random();
		return random.nextInt(999999999);
	}

	private void validateCustomer(CustomerForm customerForm) {

    	String name = customerForm.getName();

		if (name == null || name.equals("") || name.length() > 45 || name.length() < 4) {
			throw new ApiException.ValidationFailure("name","Invalid name field");
		}

		String address = customerForm.getAddress();

		if (address == null || address.equals("") || address.length() > 45 || address.length() <4) {
			throw new ApiException.ValidationFailure("address","Invalid address field");
		}

		String phone = customerForm.getPhone();
		if (phone == null || phone.equals("") || phone.replaceAll("\\D","").length()!=10) {
			throw new ApiException.ValidationFailure("phone","Invalid phone field");
		}

		String email= customerForm.getEmail();
		if (email == null || email.trim().isEmpty()) {
			throw new ApiException.ValidationFailure("email", "Invalid email address");
		}
		Pattern emailPattern = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
		Matcher matcher = emailPattern.matcher(email);

		if (!matcher.matches()) {
			throw new ApiException.ValidationFailure("email", "Invalid email address");
		}

		String creditCardNumber = customerForm.getCcNumber();

		if (creditCardNumber == null || creditCardNumber.equals("")){
			throw new ApiException.ValidationFailure("ccNumber", "Invalid ccNumber field");
		}

		// Remove spaces and dashes from credit card number
		creditCardNumber = creditCardNumber.replaceAll("\\s+|-", "");

		// Check for credit card number length
		if ( creditCardNumber.length() < 14 || creditCardNumber.length() > 16) {
			throw new ApiException.ValidationFailure("ccNumber", "Credit card number should be between 14 and 16 digits");
		}
		// TODO: Validation checks for address, phone, email, ccNumber

		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.ValidationFailure("Please enter a valid expiration date");

		}
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {


	try{
		if (ccExpiryMonth == null || ccExpiryYear == null) {
			return true;
		}
			int expiryMonth = Integer.parseInt(ccExpiryMonth);
			int expiryYear = Integer.parseInt(ccExpiryYear);

	// Create a YearMonth object for the expiry date
			YearMonth expiryYearMonth = YearMonth.of(expiryYear, expiryMonth);

	// Get the current year and month
			YearMonth currentYearMonth = YearMonth.now();

	// Check if the expiry date is before the current date
			return expiryYearMonth.isBefore(currentYearMonth);
		}catch(Exception e){
			return true;
		}

	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.ValidationFailure("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() <= 0 || item.getQuantity() > 99) {
				throw new ApiException.ValidationFailure("Invalid quantity of item");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());

			if (databaseBook.getPrice() != item.getBookForm().getPrice()) {
				throw new ApiException.ValidationFailure("Invalid price for book " + item.getBookId());
			}

			// Check if the item's category matches the category from the database
			if (databaseBook.getCategoryId() != item.getBookForm().getCategoryId()) {
				throw new ApiException.ValidationFailure("Invalid category for book " + item.getBookId());
			}

		});
	}

}
