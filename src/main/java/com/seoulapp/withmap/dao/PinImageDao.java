package com.seoulapp.withmap.dao;

import java.util.List;

import com.seoulapp.withmap.model.PinImage;

public interface PinImageDao {
	List<PinImage> getAll(final int pinId);
	void insert(final List<PinImage> pinImage);
}
