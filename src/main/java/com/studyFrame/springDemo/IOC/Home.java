package com.studyFrame.springDemo.IOC;

public class Home {
	private String address;

	public Home(String address) {
		this.address = address;
	}

	public Home(){}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Home{" +
				"address='" + address + '\'' +
				'}';
	}
}
