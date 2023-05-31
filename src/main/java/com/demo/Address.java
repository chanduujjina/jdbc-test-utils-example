package com.demo;

import java.io.Serializable;

public class Address implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Address [aid=" + aid + ", addName=" + addName + ", addType=" + addType + "]";
	}

	public Address(int aid, String addName, String addType) {
		super();
		this.aid = aid;
		this.addName = addName;
		this.addType = addType;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	private int aid;
	
	private String addName;
	
	private String addType;

}
