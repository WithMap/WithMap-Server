package com.seoulapp.withmap.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Pin extends Dto {

	private int id;

	private String name;

	private PinType type;

	private double latitude;
	
	private double longitude;
	
	private String address;
	
	private	String comment;

	private int state;
	
	private int like;

	private int userId;
}
