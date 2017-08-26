package main;
import java.sql.*;
import java.util.ArrayList;

public class Engine {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {

		DBConnect db = new DBConnect();
		boolean connected = db.connect();
		if (connected) {
			System.out.println("coneected******");
			LogIn log = new LogIn(db.getStatement());
			log.LoginIn("2048", "ahmed", 1);
		}
		Book b = new Book();
		b.setISBN("2");
		b.setTitle("Demo2");
		b.setCatgory("Science");
		b.setPrice("2000");
		b.setPubYear("2015");
		b.setPubName("El-Nasr2");

		Publisher p = new Publisher();
		p.setPubName("El-Nasr2");
		p.setAddress("Mahtet Masr");
		p.setPhone("4390498");

		ArrayList<Authors> authors = new ArrayList<Authors>();
		Authors a = new Authors();
		a.setISBN("2");
		a.setAuthorName("Naguib Mahfouz");
		authors.add(a);

		a = new Authors();
		a.setISBN("2");
		a.setAuthorName("Mohamed Shawky");
		authors.add(a);

		/*
		Manager manager = new Manager(db.getConnection(), db.getStatement(), "Mrmr");
		// System.out.println(manager.promoteCustomers("Adele"));
		// manager.addNewBook(b, p, authors, "10");
		ResultSet rs = manager.getOrders();
		while(rs.next()) {
			System.out.println(rs.getInt("ISBN"));
		}
		
		*/
		User customer = new Customer(db.getConnection(), db.getStatement(), "ahmed");
		Pair x = customer.checkOutShoppingCart("2020-05-09", "123-456-789", "2019-11-03");
		System.out.println(x.getFirst() + "::: " + x.getSecond());
		
		/*
		 * SignUp up = new SignUp(db.getStatement()); ArrayList<String> list =
		 * new ArrayList<String>(); list.add("Mayar"); list.add("mero");
		 * list.add("GAmal"); list.add("mayar.gama@hotmail.com");
		 * list.add("Gamal2010"); list.add("01212131457");
		 * list.add("Wabour El MAyha"); list.add("0"); list.add("0"); // is
		 * logid in // test for edit profile try { up.addUser(list);
		 * //user.setName("Mayar"); //System.out.println(
		 * user.editProfile(list)); /*ResultSet rs = user.showProfile();
		 * while(rs.next()) { String s1 = rs.getString("Username"); String s2 =
		 * rs.getString("FName"); String s3 = rs.getString("LName"); String s4=
		 * rs.getString("Email"); String s5 = rs.getString("Password"); String
		 * s6 = rs.getString("Telephone"); String s7 =
		 * rs.getString("Ship_Address"); String s8 = rs.getString("Is_Manager");
		 * String s9 = rs.getString("Is_Logged_In");
		 * 
		 * System.out.println(s1 + " : " + s2 + " : " + s3 + " : " + s4 + " : "
		 * + s5 + " : " + s6 + " : " + s7+ " : " + s8 + " : " + s9 );
		 * 
		 * } } catch (SQLException e) { e.printStackTrace(); }
		 */

		// test for searchbook
		/*
		 * Map<String, String> paramaters =new LinkedHashMap<String, String>();
		 * paramaters.put("Pub_Year", "2015"); paramaters.put("Category",
		 * "Science"); ResultSet rs = user.searchBooks(paramaters);
		 * while(rs.next()) { String s1 = rs.getString("ISBN"); String s2 =
		 * rs.getString("Title"); String s3 = rs.getString("Category"); String
		 * s4= rs.getString("Price"); String s5 = rs.getString("Pub_Year");
		 * String s6 = rs.getString("Pub_Name");
		 * 
		 * System.out.println(s1 + " : " + s2 + " : " + s3 + " : " + s4 + " : "
		 * + s5 + " : " + s6 );
		 * 
		 * }
		 */

		// test add to shopping cart
		// addToShoppingCart(String ISBN, String quantity, String Price)
		// user.setName("Shery");
		// System.out.println(user.addToShoppingCart("1","2" , "1000" ));
		// System.out.println(user.addToShoppingCart("2","2" , "3000" ));
		// System.out.println(user.addToShoppingCart("3","3" , "2000" ));
		// test add to shopping cart
		/*
		 * Map <String,Pair>m= user.showShoppingCart(); for (Entry <String,Pair>
		 * entry : m.entrySet()){ System.out.println(entry.getKey()+" : "+
		 * entry.getValue().getFirst() +" :: "+ entry.getValue().getSecond()); }
		 */

		// System.out.println(user.getTotalPrice());
		// System.out.println(user.removeItemFromShoppingCart("2"));
		// System.out.println(user.updateShoppingCart("2","15"));
	}

}
