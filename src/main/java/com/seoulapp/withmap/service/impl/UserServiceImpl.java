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

	@Override
	public void signUp(User user) {
		user.setPoint(5000);
		userDao.insert(user);
	}

	@Override
	public String signIn(int userId, String password) {
		User user = getUser(userId);
		
		// 해당 userId 가 존재하는지 확인
		if(user == null)
			return "NotExistUser";
		
		// password가 일치하는지 확인 TODO 원래 토큰 반환이지만 현재는 그냥 userId를 반환 
		if(password.equals(user.getPassword()))
			return String.valueOf(userId);
		
		return "NotEqualPassword";
	}

}
