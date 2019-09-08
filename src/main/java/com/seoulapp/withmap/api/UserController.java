package com.seoulapp.withmap.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seoulapp.withmap.model.User;
import com.seoulapp.withmap.service.UserService;

@RestController
@RequestMapping("/withmap/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	public User getUser(@PathVariable("id") int Id) {
		return userService.getUser(Id);
	}

}
