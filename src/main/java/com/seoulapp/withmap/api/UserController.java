package com.seoulapp.withmap.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seoulapp.withmap.model.User;
import com.seoulapp.withmap.service.UserService;

@RestController
@RequestMapping("/withmap/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") int userId) {
		return userService.getUser(userId);
	}
	
	@GetMapping("")
	public String signIn(int id, String password) {
		return userService.signIn(id, password);
	}

	// TODO 반환 형으로 성공/실패 를 알려주는게 나은지
	@PutMapping("")
	public void signUp(@ModelAttribute User user) {
		userService.signUp(user);
	}
	
	

}
