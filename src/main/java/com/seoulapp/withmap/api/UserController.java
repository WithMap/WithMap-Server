package com.seoulapp.withmap.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seoulapp.withmap.model.Overlap;
import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.model.User;
import com.seoulapp.withmap.service.PinService;
import com.seoulapp.withmap.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/withmap/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PinService pinService;

	@ApiOperation(value = "나의 정보조회")
	@GetMapping("/myinfo")
	public ResponseEntity<User> getMyInfo(@RequestHeader(value = "Authorization") final String token) {
		User user = userService.getUserByToken(token);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "내가 등록한 핀 조회")
	@GetMapping("/mypins")
	public ResponseEntity<List<Pin>> getMyPins(@RequestHeader(value = "Authorization") final String token) {
		List<Pin> pins = pinService.getUserPins(token);
		return new ResponseEntity<List<Pin>>(pins, HttpStatus.OK);
	}

	@ApiOperation(value = "회원가입")
	@PostMapping("")
	public ResponseEntity<Void> signUp(@RequestBody @Valid User user) {
		userService.signUp(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	// boolean으로 타입 캐스팅 되는지?
	@ApiOperation(value = "중복확인")
	@GetMapping("/{content}/check")
	public ResponseEntity<Overlap> checkQueryExist(@PathVariable(value = "content") String content,
			@RequestParam(value = "query") String query) {
		Overlap overlap = userService.checkQueryExist(content,query);
		return new ResponseEntity<Overlap>(overlap, HttpStatus.OK);
	}

}
