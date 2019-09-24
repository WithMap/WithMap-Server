package com.seoulapp.withmap.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pin extends Dto {

	private int id;

	private int userId;

	private String unimprovedName;

	private String improvedName;

	private PinType type;

	private double latitude;

	private double longitude;

	private String address;

	private boolean state;

	private int like;

	public int getType() {
		return type.intValue();
	}

}
