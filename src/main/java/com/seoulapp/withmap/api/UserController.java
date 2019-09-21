package com.seoulapp.withmap.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seoulapp.withmap.model.User;
import com.seoulapp.withmap.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/withmap/users")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "나의 정보조회", authorizations = { @Authorization(value = "apiKey") })
	@GetMapping("/myinfo")
	public ResponseEntity<User> getMyInfo(@RequestHeader(value = "Authorization") final String token) {
		User user = userService.getUserByToken(token);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "회원가입")
	@PostMapping("")
	public ResponseEntity<Void> signUp(@RequestBody @Valid User user) {
		userService.signUp(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
