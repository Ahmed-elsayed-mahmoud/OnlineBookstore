package main;
import java.sql.Connection;
import java.sql.Statement;


public class Customer extends User{

	public Customer(Connection con, Statement st, String customerName) {
		super(con, st, customerName);
	}

	
}
