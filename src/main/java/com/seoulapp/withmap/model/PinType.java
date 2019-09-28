package com.seoulapp.withmap.model;

public enum PinType {
	OBSTACLE(1), CURB(2), DIRTROAD(3), NARROWROAD(4), RESTROOM(5), RESTAURANT(6);
	
	private final int value;

	PinType(int value) {
		this.value = value;
	}

	public int intValue() {
		return value;
	}
	
	public boolean match(PinType pinType) {
		return pinType == this; 
	}
	
	public boolean match(int pinTypeInt) {
		return pinTypeInt == this.intValue(); 
	}

	public static PinType valueOf(int value) {
		switch (value) {
		case 1:
			return OBSTACLE;
		case 2:
			return CURB;
		case 3:
			return DIRTROAD;
		case 4:
			return NARROWROAD;
		case 5:
			return RESTROOM;
		case 6:
			return RESTAURANT;
		default:
			throw new AssertionError("unknow value  :" + value);
		}
	}
}
