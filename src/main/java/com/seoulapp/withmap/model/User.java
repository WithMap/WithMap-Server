package com.seoulapp.withmap.model;

import org.joda.time.DateTime;

import lombok.Data;

@Data
public class User {
	
	private int id;
	private String password;
	private String name;
	private String email;
	private char gender;
	private int year;
	private char disable;
	private int point;
	private DateTime crtDate;
	private DateTime updDate;

}
