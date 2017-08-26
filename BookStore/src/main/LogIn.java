package main;

import java.sql.*;

public class LogIn extends Query {
	private Statement statement;
	private ResultSet resultSet;
	private String query;
	public LogIn(Statement st) {
		this.statement = st;
		this.query = "";
	}

	// returns a boolean to indicate if the user is previously logged in.
	public boolean LoginIn(String userName, String password, int isManager) {
		query = "SELECT * FROM `customer` WHERE Username = '" + userName + "' AND Password = '" + password + "' AND Is_Manager = " + isManager;
		try {
			resultSet = statement.executeQuery(query);
			// User is previously registered
			if (resultSet.first()) {
				setIsLoginIn(userName, 1);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setIsLoginIn(String userName, int isLogin) {
		query = "UPDATE `customer` SET Is_Logged_In = " + isLogin + " WHERE Username = '" + userName + "'";
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void LogOut(String userName) {
		setIsLoginIn(userName, 0);
		// remove all items in the shopping cart
		query = "DELETE FROM `shopping_cart` WHERE `Username` = '" + userName + "' AND `Credit_Card_No` IS NULL";
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// check "Is_Manager"
	public boolean isManager(String userName) {
		query = "SELECT `Is_Manager` FROM `customer` WHERE Username = '" + userName + "'";
		try {
			resultSet = statement.executeQuery(query);
			if (resultSet.first()) {
				return (resultSet.getInt("Is_Manager") == 1) ? true : false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
