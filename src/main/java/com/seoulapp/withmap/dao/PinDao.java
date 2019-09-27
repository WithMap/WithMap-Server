package com.seoulapp.withmap.dao;

import java.util.List;

import com.seoulapp.withmap.model.pin.Pin;

public interface PinDao {

	List<Pin> getPins(final double latitude, final double longitude, final double radius);
	List<Pin> getPins(final int userId);
	Pin get(final int id);
	int insert(final Pin pin);
	void update(final Pin pin);
	void updateLikeCount(final int id);
	void delete(final int id);
}
