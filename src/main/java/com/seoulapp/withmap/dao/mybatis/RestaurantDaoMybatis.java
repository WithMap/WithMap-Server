package com.seoulapp.withmap.dao.mybatis;

import com.seoulapp.withmap.dao.RestaurantDao;
import com.seoulapp.withmap.model.pin.detail.Restaurant;

public class RestaurantDaoMybatis extends CommonDaoSupport implements RestaurantDao{

	@Override
	public void insert(Restaurant restaurant) {
		getSqlSession().insert("com.seoulapp.withmap.restaurant.insert", restaurant);
	}		

	@Override
	public void update(Restaurant restaurant) {
		getSqlSession().update("com.seoulapp.withmap.restaurant.update", restaurant);
	}

	@Override
	public void delete(int id) {
		getSqlSession().delete("com.seoulapp.withmap.restaurant.delete", id);
	}

	@Override
	public Restaurant get(int id) {
		return getSqlSession().selectOne("com.seoulapp.withmap.restaurant.select", id);
	}

}
