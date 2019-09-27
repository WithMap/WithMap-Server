package com.seoulapp.withmap.dao;

import com.seoulapp.withmap.model.pin.detail.Restroom;

public interface RestroomDao {

	void insert(Restroom restroom);

	void update(Restroom restroom);

	void delete(int id);

	Restroom get(int id);

}
