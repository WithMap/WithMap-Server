package com.seoulapp.withmap.dao;

import com.seoulapp.withmap.model.Restroom;

public interface RestroomDao {

	void insert(Restroom restroom);

	void update(Restroom restroom);

	void delete(int id);

	Restroom getRestroom(int id);

}
