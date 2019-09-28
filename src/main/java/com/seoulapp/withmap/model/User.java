package com.seoulapp.withmap.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class User {
	
	private int id;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
	
	public boolean matchPassword(String password, PasswordEncoder bCryptPasswordEncoder) {
        return bCryptPasswordEncoder.matches(password, this.password);
    }
}
