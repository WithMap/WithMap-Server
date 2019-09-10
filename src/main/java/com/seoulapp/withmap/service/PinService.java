package com.seoulapp.withmap.service;

import java.util.List;

import javax.validation.Valid;

import com.seoulapp.withmap.model.Pin;

public interface PinService {

	List<Pin> getPins(final double latitude, final double longitude, final int radius);
	
	Pin getPinById(final int id);
	
	void savePin(final Pin pin);
	
	void updatePin(final Pin pin);
	
	void deletePin(final int id);

	
}
