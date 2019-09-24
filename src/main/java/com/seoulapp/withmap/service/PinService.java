package com.seoulapp.withmap.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.model.PinView;

public interface PinService {

	List<Pin> getPins(final double latitude, final double longitude, final int radius);
	
	List<Pin> getUserPins(String token);
	
	PinView getPinById(final int id);
	
	void savePin(final String token, final Pin pin, final MultipartFile[] images);
	
	void updatePin(final String token, final Pin pin, final MultipartFile[] images);
	
	void deletePin(final String token, final int id);

	
}
