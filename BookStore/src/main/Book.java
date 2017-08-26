package main;
import java.util.ArrayList;

public class Book {
	private String ISBN;

	public String getISBN() {
		return ISBN;
	}

	public String getTitle() {
		return title;
	}

	public String getCatgory() {
		return catgory;
	}

	public String getPrice() {
		return price;
	}

	public String getPubYear() {
		return pubYear;
	}

	public String getPubName() {
		return pubName;
	}

	public void setProperities(ArrayList<String> properities) {
		this.properities = properities;
	}

	private String title;
	private String catgory;
	private String price;
	private String pubYear;
	private String pubName;
	ArrayList<String> properities;

	public Book() {
		properities = new ArrayList<String>();
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setCatgory(String catgory) {
		this.catgory = catgory;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setPubYear(String pubYear) {
		this.pubYear = pubYear;
	}

	public void setPubName(String pubName) {
		this.pubName = pubName;
	}

	public ArrayList<String> getProperities() {
		properities.add(ISBN);
		properities.add(title);
		properities.add(catgory);
		properities.add(price);
		properities.add(pubYear);
		properities.add(pubName);
		return properities;
	}

}
