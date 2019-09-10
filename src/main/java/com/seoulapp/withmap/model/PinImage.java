package com.seoulapp.withmap.model;

import com.seoulapp.withmap.model.Pin.PinBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PinImage {

	private int pinId;
	
	private String imagePath;
	
	private int state;
}
