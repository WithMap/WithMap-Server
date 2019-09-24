package com.seoulapp.withmap.model;

public enum PinType {
	OBSTACLE(0), CURB(1), DIRTROAD(2), NARROWROAD(3), RESTROOM(4), RESTAURANT(5);
	
	private final int value;

	PinType(int value) {
		this.value = value;
	}

	public int intValue() {
		return value;
	}

	public static PinType valueOf(int value) {
		switch (value) {
		case 0:
			return OBSTACLE;
		case 1:
			return CURB;
		case 2:
			return DIRTROAD;
		case 3:
			return NARROWROAD;
		case 4:
			return RESTROOM;
		case 5:
			return RESTAURANT;
		default:
			throw new AssertionError("unknow value  :" + value);
		}
	}
}
