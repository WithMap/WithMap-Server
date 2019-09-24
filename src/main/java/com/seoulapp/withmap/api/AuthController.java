package com.seoulapp.withmap.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seoulapp.withmap.model.Login;
import com.seoulapp.withmap.model.Token;
import com.seoulapp.withmap.service.UserService;

import io.swagger.annotations.ApiOperation;

@RequestMapping(value = "/withmap/signin")
@RestController
public class AuthController {
	
	@Autowired
	private UserService userService;

    @PostMapping()
    @ApiOperation(value = "로그인")
    public ResponseEntity<Token> signIn(@RequestBody @Valid Login login) {
        Token token = userService.signIn(login.getEmail(), login.getPassword());
        return new ResponseEntity<Token>(token, HttpStatus.OK);
    }
    
    @GetMapping("/test")
    public String test() {
    	return "test";
    }

}
