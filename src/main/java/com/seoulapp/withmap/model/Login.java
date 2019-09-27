package com.seoulapp.withmap.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
}
