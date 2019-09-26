package com.seoulapp.withmap.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seoulapp.withmap.dao.UserDao;
import com.seoulapp.withmap.exception.BadRequestException;
import com.seoulapp.withmap.exception.ExpiredTokenException;
import com.seoulapp.withmap.exception.NotFoundException;
import com.seoulapp.withmap.exception.NotValidTokenException;
import com.seoulapp.withmap.exception.UnAuthenticationException;
import com.seoulapp.withmap.model.Overlap;
import com.seoulapp.withmap.model.Token;
import com.seoulapp.withmap.model.User;
import com.seoulapp.withmap.model.error.ErrorType;
import com.seoulapp.withmap.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	// private static final String JWT_SECRET = "${spring.jwt.secret}";
	// private static final String JWT_ISSUER = "${spring.jwt.issuer}";
	private static final String secret = "SECRETKEY";
	private static final String issuer = "WITHMAP";

	@Override
	public User getUserByToken(String token) {
		int userId = findIdByToken(token);
		return userDao.get(userId);
	}

	@Override
	public void signUp(User user) {
		user.setPoint(5000);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userDao.insert(user);
	}
	
	@Override
	public Overlap checkQueryExist(final String content, final String query) {
		
		boolean isOverlapped;
		
		if(content.equals("email")) {
			isOverlapped = userDao.isExistEmail(query);
		} else if(content.equals("name")) {
			isOverlapped = userDao.isExistName(query);
		} else {
			throw new BadRequestException(ErrorType.BAD_REQUEST, "유효한 중복확인 요청 형식이 아닙니다.");
		}
			
		return new Overlap(isOverlapped);
	}

	@Override
	public Token signIn(String email, String password) {
		User user = userDao.get(email);

		if (user == null || !user.matchPassword(password, bCryptPasswordEncoder)) {
			throw new UnAuthenticationException(ErrorType.UNAUTHENTICATED, "아이디나 비밀번호가 일치하지 않습니다.");
		}

		return new Token(createToken(user));

	}
	

	@Override
	public boolean isValidateToken(final String token) {
		if (token == null)
			throw new BadRequestException(ErrorType.BAD_REQUEST, "유효하지 않은 토큰 인증 요청입니다.");

		try {
			Jws<Claims> jws = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
			String email = jws.getBody().getSubject();
			Date expireDate = jws.getBody().getExpiration();

			return isExistedUser(email) && !isTokenExpired(expireDate);
		} catch (ExpiredJwtException e) {
			throw new ExpiredTokenException(ErrorType.UNAUTHORIZED, "만료된 토큰입니다.");
		} catch (Exception e) {
			throw new NotValidTokenException(ErrorType.UNAUTHORIZED, "유효하지 않은 형식의 토큰입니다.");
		}
	}
	
	@Override
	public int findIdByToken(final String token) {
		Jws<Claims> jws = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
		String email = jws.getBody().getSubject();
		
		User user = userDao.get(email);
		if(user == null)
			throw new NotFoundException(ErrorType.NOT_FOUND, "해당 유저가 존재하지 않습니다.");
		
		
		return user.getId();
	}
	
	private String createToken(final User user) {
		DateTime expireTime = DateTime.now().plusWeeks(1);

		String email = user.getEmail();
		String nickname = user.getName();

		DateTime currentTime = DateTime.now();

		String token = Jwts.builder().setSubject(email).claim("nickname", nickname).setIssuer(issuer)
				.setIssuedAt(currentTime.toDate()).setExpiration(expireTime.toDate())
				.signWith(SignatureAlgorithm.HS256, generateKey()).compact();

		return token;
	}

	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = secret.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (log.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		return key;
	}

	private boolean isTokenExpired(final Date expireDate) {
		final DateTime expiration = new DateTime(expireDate);
		return expiration.isBefore(DateTime.now());
	}

	private boolean isExistedUser(final String email) {
		return userDao.get(email) != null;
	}


}
