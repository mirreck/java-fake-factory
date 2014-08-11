package com.github.mirreck.bean.domain;

import java.util.Date;

/**
 * Created by thomas.decoster on 11/07/2014.
 */
public class Person {

	private String name;	
	private String firstName;
	private Date birthDate;
	private Address address;
	private EyeColor eyeColor;
	private HairColor hairColor;
	private int height;
	private int weight;
	private String job;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public EyeColor getEyeColor() {
		return eyeColor;
	}

	public void setEyeColor(EyeColor eyeColor) {
		this.eyeColor = eyeColor;
	}

	public HairColor getHairColor() {
		return hairColor;
	}

	public void setHairColor(HairColor hairColor) {
		this.hairColor = hairColor;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Person() {
	}

}