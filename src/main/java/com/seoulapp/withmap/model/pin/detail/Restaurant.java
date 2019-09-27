package com.seoulapp.withmap.model.pin.detail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Restaurant {
	
	private int id;
	
	private String name;
	
	private String number;
	
	private Classification classification;
	
	private String site;
	
	private String useableTime;
	
	private String comment;
	
	public int getClassification() {
		return classification.intValue();
	}

}
