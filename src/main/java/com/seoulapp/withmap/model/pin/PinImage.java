package com.seoulapp.withmap.model.pin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seoulapp.withmap.model.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PinImage extends Dto {

	@JsonIgnore
	private int pinId;
	
	private String imagePath;
	
	private boolean state;
}
