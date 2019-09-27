package com.seoulapp.withmap.dao;

import com.seoulapp.withmap.model.Restaurant;

public interface RestaurantDao {
	
	void insert(Restaurant restaurant);

	void update(Restaurant restaurant);

	void delete(int id);

	Restaurant get(int id);

}
