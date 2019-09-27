package com.seoulapp.withmap.model;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dto {
	private DateTime crtDate;
	private DateTime updDate;
	
	public String getCrtDate() {
		return crtDate.toString();
	}
	
	public String getUpdDate() {
		return updDate.toString();
	}

}

