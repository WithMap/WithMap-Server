package com.seoulapp.withmap.model.pin.detail;

public enum Classification {

	RESTAURANT(0), CAFE(1), OTHER(2);
	
	private final int value;
	
	Classification(int value){
		this.value = value;
	}
	
	public int intValue() {
		return value;
	}
	
	public static Classification valueOf(int value) {
		switch (value) {
		case 0:
			return RESTAURANT;
		case 1:
			return CAFE;
		case 2:
			return OTHER;
		default:
			throw new AssertionError("unknow value  :" + value);
		}
	}
	
	

}
