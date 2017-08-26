package main;
import java.util.ArrayList;

public class Publisher {
	private String pubName;
	public String getPubName() {
		return pubName;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public void setProperities(ArrayList<String> properities) {
		this.properities = properities;
	}

	private String address;
	private String phone;
	ArrayList<String> properities;

	public Publisher() {
		properities = new ArrayList<String>();
	}

	public void setPubName(String pubName) {
		this.pubName = pubName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public ArrayList<String> getProperities() {
		properities.add(pubName);
		properities.add(address);
		properities.add(phone);

		return properities;
	}

}
