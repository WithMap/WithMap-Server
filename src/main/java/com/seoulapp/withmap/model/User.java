package com.seoulapp.withmap.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class User {
	
	private int id;
	
	@NotBlank
    @Size(min = 8)
	private String password;
	
	@NotBlank
	private String name;
	
	@Email
	private String email;
	
	private char gender;
	private int year;
	private char disable;
	private int point;
}
