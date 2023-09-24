package business.book;

public class Book {

	private long bookId;
	private String title;
	private String author;
	private String description;
	private int price;
	private boolean isPublic;
	private int rating;
	private boolean isFeatured;
	private long categoryId;


	public Book(long bookId, String title, String author, int price, boolean isPublic, long categoryId) {

		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.price = price;
		this.isPublic = isPublic;
		this.categoryId = categoryId;
	}

	public Book(long bookId, String title, String author, String description, int price, boolean isPublic, int rating, boolean isFeatured, long categoryId) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.description = description;
		this.price = price;
		this.isPublic = isPublic;
		this.rating = rating;
		this.isFeatured = isFeatured;
		this.categoryId = categoryId;
	}

	public long getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}

	public int getPrice() {
		return price;
	}
	public int getRating() {
		return rating;
	}

	public boolean getIsPublic() {
		return isPublic;
	}



	public boolean getIsFeatured() {
		return isFeatured;
	}

	public long getCategoryId() {
		return categoryId;
	}

	@Override
	public String toString() {
		return "Book{" +
				"bookId=" + bookId +
				", title='" + title + '\'' +
				", author='" + author + '\'' +
				", description='" + description + '\'' +
				", price=" + price +
				", isPublic=" + isPublic +
				", rating=" + rating +
				", isFeatured=" + isFeatured +
				", categoryId=" + categoryId +
				'}';
	}
}
