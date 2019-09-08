package com.seoulapp.withmap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seoulapp.withmap.dao.UserDao;
import com.seoulapp.withmap.model.User;
import com.seoulapp.withmap.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public User getUser(int userId) {
		return userDao.get(userId);
	}

}
