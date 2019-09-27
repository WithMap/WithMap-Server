package com.seoulapp.withmap.dao;

import com.seoulapp.withmap.model.pin.detail.Road;

public interface RoadDao {

	void insert(Road road);
	void update(Road road);
	void delete(int id);
	Road get(int id);
	
}
