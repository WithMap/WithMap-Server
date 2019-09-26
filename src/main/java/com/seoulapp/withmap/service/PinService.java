package com.seoulapp.withmap.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.model.PinView;

public interface PinService {

	void savePin(final String token, final Pin pin, final MultipartFile[] images,
			final Map<String, String> detailContents);

	void updatePin(final String token, final Pin pin, final MultipartFile[] images,
			final Map<String, String> detailContents);

	void deletePin(final String token, final int id);

	String imageTest(MultipartFile file);

	void likePin(final String token, final int pinId);

	void reportPin(final String token, final int pinId);

	List<Pin> getPins(final double latitude, final double longitude, final int radius);

	List<Pin> getUserPins(String token);

	PinView getPinById(final String token, final int id);

}
