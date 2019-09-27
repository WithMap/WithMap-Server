package com.seoulapp.withmap.dao;

import com.seoulapp.withmap.model.pin.detail.Restaurant;

public interface RestaurantDao {
	
	void insert(Restaurant restaurant);

	void update(Restaurant restaurant);

	void delete(int id);

	Restaurant get(int id);

}
