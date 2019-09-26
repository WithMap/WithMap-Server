package com.seoulapp.withmap.model;

import org.joda.time.DateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dto {
	private DateTime crtDate;
	private DateTime updDate;
	
	public String getCrtDate() {
		return crtDate.toString("yyyy-MM-dd hh:mm:ss");
	}
	
	public String getUpdDate() {
		return crtDate.toString("yyyy-MM-dd hh:mm:ss");
	}
}

