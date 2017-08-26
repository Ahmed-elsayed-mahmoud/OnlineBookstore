package main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class User extends Query {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private String userName;
	private String query;

	public User(Connection con, Statement st, String name) {
		this.connection = con;
		this.statement = st;
		this.userName = name;
	}

	public String getName() {
		return userName;
	}

	public ResultSet showProfile() throws SQLException {
		query = "SELECT * FROM `customer` WHERE `Username` = '" + userName + "'";
		return statement.executeQuery(query);
	}

	public ResultSet searchBooks(Map<String, String> paramaters) throws SQLException {
		/* map of <name = value > */
		/* stock order relation */
		query = "SELECT * FROM `book` WHERE ";
		int counter = 0;

		// handle searching for integer
		for (Map.Entry<String, String> entry : paramaters.entrySet()) {
			query += entry.getKey().equals("Price") ? entry.getKey() + " = " + entry.getValue()
					: entry.getKey() + " = '" + entry.getValue() + "'";
			System.out.println(entry.getKey() + " " + entry.getValue());
			if (counter < paramaters.size() - 1) {
				query += " AND ";
			}
			counter++;
		}
		System.out.println(query);
		return statement.executeQuery(query);
	}

	public ResultSet getBooks() throws SQLException {
		query = "SELECT * FROM `book` ";

		return statement.executeQuery(query);
	}

	public boolean editProfile(ArrayList<String> param) throws SQLException {
		query = "UPDATE `customer` SET `Username`= '" + param.get(0) + "',`FName`= '" + param.get(1) + "',`LName`='"
				+ param.get(2) + "',`Email`= '" + param.get(3) + "',`Password`= '" + param.get(4) + "',`Telephone`= '"
				+ param.get(5) + "',`Ship_Address`= '" + param.get(6) + "',`Is_Manager`= " + param.get(7)
				+ ",`Is_Logged_In`= " + param.get(8) + " WHERE Username = '" + userName + "'";

		int affectedRows = statement.executeUpdate(query);
		return (affectedRows != 0);
	}

	public boolean addToShoppingCart(String ISBN, String quantity, String Price) throws SQLException {
		/* store in shoppingCart */
		query = "INSERT INTO `shopping_cart`(`ISBN`, `Username`, `Quantity`, `Price`) VALUES ( ";
		query += "'" + ISBN + "' , '" + userName + "' , '" + quantity + "' , '" + Price + "')";
		int affectedRows = statement.executeUpdate(query);
		return (affectedRows != 0);
	}
	// if same user order same book again and change in quantity

	public boolean updateShoppingCart(String ISBN, String newQuantity) throws SQLException {
		query = "UPDATE `shopping_cart` SET `Quantity` ='" + newQuantity + "' WHERE Username ='" + userName
				+ "' AND ISBN ='" + ISBN + "'";
		int affectedRows = statement.executeUpdate(query);
		return (affectedRows != 0);
	}

	public Map<String, ArrayList<String>> showShoppingCart() throws SQLException {
		Map<String, ArrayList<String>> cart = new LinkedHashMap<String, ArrayList<String>>();
		query = "SELECT  s.ISBN, s.Quantity, s.Price , b.Title " + "FROM `shopping_cart` AS s, `book` AS b "
				+ "WHERE s.ISBN=b.ISBN AND " + "s.Username= '" + userName + "'"
				+ "AND s.Date IS NULL AND s.Credit_Card_No IS NULL AND s.Expiry_Date IS NULL";
		resultSet = statement.executeQuery(query);
		while (resultSet.next()) {
			String ISBN = resultSet.getString("ISBN");
			ArrayList<String> value = new ArrayList<String>();
			value.add(resultSet.getString("Title"));
			value.add(resultSet.getString("Quantity"));
			value.add(resultSet.getString("Price"));
			cart.put(ISBN, value);
		}
		return cart;
	}

	public boolean removeItemFromShoppingCart(String ISBN) throws SQLException {
		query = "DELETE FROM `shopping_cart` WHERE `ISBN` = '" + ISBN
				+ "' AND `Date` IS NULL AND `Credit_Card_No` IS NULL AND `Expiry_Date` IS NULL ";
		int affectedRows = statement.executeUpdate(query);
		return (affectedRows != 0);

	}

	public Pair checkOutShoppingCart(String Date, String creditCardno, String expiryDate) {
		try {
			if (creditCardno == null || !validateDate(Date, expiryDate)) {
				return new Pair(false, 0.0);
			}
		} catch (ParseException e1) {
			return new Pair(false, 0.0);
		}
		try {
			connection.setAutoCommit(false);
			Savepoint savePt = connection.setSavepoint();

			query = "SELECT * FROM `shopping_cart` WHERE `Username` = '" + userName + "' AND `Credit_Card_No` is null";
			resultSet = statement.executeQuery(query);
			System.out.println(query);

			double totalPrice = 0.0;
			boolean checkOut = false;

			while (resultSet.next()) {
				int isbn = resultSet.getInt("ISBN");
				int quantity = resultSet.getInt("Quantity");
				double price = resultSet.getDouble("Price");
				int id = resultSet.getInt("Cart_Id");
				checkOut = true;
				totalPrice += price * quantity;
				query = "UPDATE `book_stock` SET `Left_Num`= `Left_Num` - " + quantity + ",`Sold_Num`= `Sold_Num` + "
						+ quantity + " WHERE `ISBN` = " + isbn;
				Statement newStatement = connection.createStatement();
				int affectedRows = newStatement.executeUpdate(query);
				if (affectedRows == 0) {
					System.out.println("ROLLBACK");
					connection.rollback(savePt);
					return new Pair(false, 0.0);
				}

				query = "UPDATE `shopping_cart` SET `Date`='" + Date + "',`Credit_Card_No`= " + creditCardno
						+ ",`Expiry_Date`= '" + expiryDate + "' WHERE `Cart_Id` = " + id;
				Statement dateStatement = connection.createStatement();
				dateStatement.executeUpdate(query);
			}
			connection.commit();
			connection.setAutoCommit(true);
			return new Pair(checkOut, totalPrice);
		} catch (SQLException e) {
			System.out.println(e);
			return new Pair(false, 0.0);
		}
	}

	public boolean validateDate(String d, String expiryDate) throws ParseException {
		if (d != null && expiryDate != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
			Date date = formatter.parse(d);
			Date ExpiryDate = formatter.parse(expiryDate);
			return !ExpiryDate.before(date);
		}
		return false;
	}

}
