package main;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;

public class SignUp extends Query {
	private Statement statement;
	private Set<Integer> integerIndex;

	public SignUp(Statement st) {
		this.statement = st;
		integerIndex = new HashSet<Integer>();
		integerIndex.add(7);
	}

	// returns number of affected rows
	public int addUser(ArrayList<String> param) throws SQLException {
		String query = "INSERT INTO `customer` VALUES (" + this.createQuery(param, integerIndex) + ")";
		return statement.executeUpdate(query);
	}

}
