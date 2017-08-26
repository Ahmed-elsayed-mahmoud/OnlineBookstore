package main;

import java.util.ArrayList;

public class Authors {
	private String ISBN ;
	private String authorName;
	ArrayList <String> properities ;
	
	public Authors() {
		properities = new ArrayList<String>();
	}
	
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public ArrayList<String> getProperities(){
		properities.add(ISBN);
		properities.add(authorName);
		return properities;	
	}	

}
