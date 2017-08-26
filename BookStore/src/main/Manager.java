package main;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;

public class Manager extends User {
	private Statement statement;
	private String query;
	private Set<Integer> setIndex;
	private Connection connection;

	public Manager(Connection con, Statement st, String name) {
		super(con, st, name);
		this.statement = st;
		this.connection = con;
	}

	public void addNewBook(Book book, Publisher publisher, ArrayList<Authors> authors, String threshold) {
		Savepoint savePt = null;
		try {
			connection.setAutoCommit(false);
			savePt = connection.setSavepoint();
			// Add Book
			setIndex = new HashSet<Integer>();
			setIndex.add(0);
			setIndex.add(3);
			setIndex.add(4);
			query = "INSERT INTO `book`(`ISBN`, `Title`, `Category`, `Price`, `Pub_Year`) VALUES ("
					+ this.createQuery(new ArrayList<String>(book.getProperities().subList(0, 5)), setIndex) + ")";
			statement.executeUpdate(query);

			// Add Publisher
			setIndex = new HashSet<Integer>();
			query = "INSERT INTO `publisher` VALUES (" + this.createQuery(publisher.getProperities(), setIndex) + ")";
			statement.executeUpdate(query);

			query = "UPDATE `book` SET `Pub_Name`= '" + publisher.getPubName() + "' WHERE ISBN = " + book.getISBN();
			statement.executeUpdate(query);

			// Add Authors
			setIndex = new HashSet<Integer>();
			setIndex.add(0);

			for (int i = 0; i < authors.size(); i++) {
				query = "INSERT INTO `author` VALUES (" + this.createQuery(authors.get(i).getProperities(), setIndex)
						+ ")";
				statement.executeUpdate(query);
			}

			// Add Book in Book Stock
			setIndex = new HashSet<Integer>();
			setIndex.add(0);
			setIndex.add(1);
			setIndex.add(2);
			setIndex.add(3);
			ArrayList<String> book_stock = new ArrayList<String>();
			book_stock.add(book.getProperities().get(0));
			book_stock.add(threshold);
			book_stock.add("0");
			book_stock.add("0");
			// extend the trigger to be on insert [Ahmed]
			query = "INSERT INTO `book_stock` VALUES (" + this.createQuery(book_stock, setIndex) + ")";
			statement.executeUpdate(query);

			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException ex) {
			try {
				connection.rollback(savePt);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean promoteCustomers(String customerName) {
		query = "UPDATE `customer` SET `Is_Manager` = 1 WHERE `Username` = '" + customerName + "'";
		int affectedRows = 0;
		try {
			affectedRows = statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return (affectedRows != 0);
	}

	public ResultSet getNeeds() throws SQLException {
		query = "SELECT * FROM `needs_orders` WHERE Is_Need = 1";
		return statement.executeQuery(query);
	}

	public ResultSet getOrders() throws SQLException {
		query = "SELECT * FROM `needs_orders` WHERE Is_Need = 0";
		return statement.executeQuery(query);
	}

	public boolean needToOrder(String ISBN) throws SQLException {
		// convert is Need to 0
		query = "UPDATE `needs_orders` SET `Is_Need`= 0 WHERE `ISBN`= " + ISBN;
		int affectedRows = statement.executeUpdate(query);
		return (affectedRows != 0);
	}

	// confirm order for one book at a time
	public boolean confirmOrder(String ISBN) throws SQLException {
		// delete && update stock
		query = "DELETE FROM `needs_orders` WHERE `Is_Need`= 0 AND `ISBN` = " + ISBN;
		int affectedRows = statement.executeUpdate(query);
		return (affectedRows != 0);
	}

	// report
	protected ResultSet totalSales() throws SQLException {
		query = "SELECT ISBN, SUM(Quantity) AS TotalQuantity FROM shopping_cart s WHERE Date IS NOT NULL AND s.Date >= date_sub(current_date, INTERVAL 1 MONTH) GROUP BY ISBN ORDER BY SUM(Quantity) DESC";
		return statement.executeQuery(query);
	}

	protected ResultSet getTopFiveCustomers() throws SQLException {
		query = "SELECT Username, SUM(Quantity) AS TotalQuantity FROM shopping_cart WHERE Date IS NOT NULL AND Date >= date_sub(current_date, INTERVAL 3 MONTH) GROUP BY Username ORDER BY SUM(Quantity) DESC LIMIT 5";
		return statement.executeQuery(query);
	}

	protected ResultSet getTopTenSalingBooks() throws SQLException {
		query = "SELECT b.ISBN, b.Title, b.Pub_Name, SUM(Quantity) AS TotalQuantity FROM shopping_cart s, book b WHERE s.Date IS NOT NULL AND s.Date >= date_sub(current_date, INTERVAL 3 MONTH) AND s.ISBN = b.ISBN GROUP BY s.ISBN ORDER BY SUM(Quantity) DESC LIMIT 10";
		return statement.executeQuery(query);
	}
}
