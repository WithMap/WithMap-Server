package com.seoulapp.withmap.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Pin extends Dto {

	private int id;

	private int userId;

	private String unimproveName;

	private String improveName;

	private PinType type;

	private double latitude;

	private double longitude;

	private String address;

	private String comment;

	private boolean state;

	private int like;

	public int getType() {
		return type.intValue();
	}

}
