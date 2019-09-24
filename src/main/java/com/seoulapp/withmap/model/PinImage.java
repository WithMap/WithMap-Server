package com.seoulapp.withmap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PinImage {

	@JsonIgnore
	private int pinId;
	
	private String imagePath;
	
	private boolean state;
}
