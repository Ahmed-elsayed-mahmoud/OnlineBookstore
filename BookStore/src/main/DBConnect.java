package main;

import java.sql.*;

public class DBConnect {
	private Connection connection;
	private Statement statement;

	public DBConnect() {

	}

	public boolean connect() {
		String url="jdbc:mysql://localhost:3306/bookstore";
		String userName="root";
		String password="";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, userName, password);
			statement = connection.createStatement();
			return true;
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex);
		}
		return false;
	}

	public Connection getConnection() {
		return connection;
	}

	public Statement getStatement() {
		return statement;
	}
}
