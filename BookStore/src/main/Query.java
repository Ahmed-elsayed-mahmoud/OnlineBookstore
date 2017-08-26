package main;
import java.util.*;

public abstract class Query {

	public String createQuery(ArrayList<String> list, Set<Integer> set) {
		String query = "";
		for (int i = 0; i < list.size(); i++) {
			// Integer attribute
			if (set.contains(i)) {
				query +=  list.get(i);
			} 
			// String attribute
			else {
				query += "'" + list.get(i) + "'";
			}
			if(i != list.size() - 1) {
				query += ", ";
			}
		}
		return query;
	}
}
