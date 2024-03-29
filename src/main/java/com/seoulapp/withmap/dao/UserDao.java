package com.seoulapp.withmap.dao;

import com.seoulapp.withmap.model.User;

public interface  UserDao {

	void insert(User user);
	void update(User user);
	User get(int userId);
	User get(String email);
	boolean isExistEmail(String email);
	boolean isExistName(String name);
}
