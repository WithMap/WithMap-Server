package com.seoulapp.withmap.service;

import com.seoulapp.withmap.model.Overlap;
import com.seoulapp.withmap.model.Token;
import com.seoulapp.withmap.model.User;
import com.seoulapp.withmap.model.log.LogView;

public interface UserService {

	User getUserByToken(String token);
	
	// 회원 가입
	void signUp(User user);
	
	Overlap checkQueryExist(final String content, final String query);
	
	// 로그인(Token 반환)
	Token signIn(String email, String password);
	
	boolean isValidateToken(String token);
	
	int findIdByToken(String token);
	
	// user의 모든 로그 조회
	LogView getLogsByUserId(int userId);
}