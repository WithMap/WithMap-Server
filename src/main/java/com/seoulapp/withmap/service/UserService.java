package com.seoulapp.withmap.service;

import com.seoulapp.withmap.model.User;

public interface UserService {

	User getUser(int userId);

	// 회원 가입
	void signUp(User user);

	// 로그인(Token 반환)
	String signIn(int userId, String password);
}