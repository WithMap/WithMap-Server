package com.seoulapp.withmap.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.model.pin.Pin;
import com.seoulapp.withmap.model.pin.PinView;

public interface PinService {

	void savePin(final String token, final Pin pin, final MultipartFile[] images,
			final Map<String, String> detailContents) throws IOException;

	void updatePin(final int id, final Pin pin, final MultipartFile[] images,
			final Map<String, String> detailContents) throws IOException;

	void deletePin(final String token, final int id);

	void likePin(final String token, final int pinId);

	void reportPin(final String token, final int pinId);

	List<Pin> getPins(final double latitude, final double longitude);

	List<Pin> getUserPins(String token);

	PinView getPinById(final String token, final int id);

}
