package com.seoulapp.withmap.api;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.model.PinView;
import com.seoulapp.withmap.service.PinService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/withmap/pins")
public class PinController {

	@Autowired
	private PinService pinService;

	@ApiOperation(value = "핀 상세조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "인증토큰", required = true, dataType = "String", paramType = "header") })
	@GetMapping("/{id}")
	public ResponseEntity<PinView> getPinById(@PathVariable("id") final int id) {
		PinView pinView = pinService.getPinById(id);
		return new ResponseEntity<PinView>(pinView, HttpStatus.OK);
	}

	@ApiOperation(value = "주위 모든 핀 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "인증토큰", required = true, dataType = "String", paramType = "header") })
	@GetMapping()
	public ResponseEntity<List<Pin>> getPins(@RequestParam(value = "latitude") final double latitude,
			@RequestParam(value = "longitude") double longitude,
			@RequestParam(value = "radius", required = false, defaultValue = "100") final int radius) {
		List<Pin> pins = pinService.getPins(latitude, longitude, radius);
		return new ResponseEntity<List<Pin>>(pins, HttpStatus.OK);
	}

	@ResponseBody
	@ApiOperation(value = "핀 작성하기")
	@PostMapping()
	public ResponseEntity<Void> savePin(@RequestHeader("Authorization") String token,
			@RequestPart("pin") @Valid final Pin pin, @RequestPart("file") MultipartFile[] images,
			@RequestParam Map<String, String> detailContents) {
		pinService.savePin(token, pin, images, detailContents);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "핀 수정하기")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "인증토큰", required = true, dataType = "String", paramType = "header") })
	@PutMapping("/{id}")
	public ResponseEntity<Void> updatePin(@RequestHeader(value = "Authorization") final String token,
			@PathVariable("id") final int id, @RequestBody @Valid final Pin pin, MultipartFile[] images) {
		pinService.updatePin(token, pin, images);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value = "핀 삭제하기")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "인증토큰", required = true, dataType = "String", paramType = "header") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePin(@RequestHeader(value = "Authorization") final String token,
			@PathVariable("id") final int id) {
		pinService.deletePin(token, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@ResponseBody
	@PostMapping("/test")
	public void imageTest(@RequestPart("file") MultipartFile file) {
		pinService.imageTest(file);
	}
}
