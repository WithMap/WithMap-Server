package com.seoulapp.withmap.api;

import java.io.IOException;
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
	@GetMapping("/{id}")
	public ResponseEntity<PinView> getPinById(@RequestHeader("Authorization") String token,
			@PathVariable("id") final int id) {
		PinView pinView = pinService.getPinById(token, id);
		return new ResponseEntity<PinView>(pinView, HttpStatus.OK);
	}

	@ApiOperation(value = "주위 모든 핀 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "인증토큰", required = true, dataType = "String", paramType = "header") })
	@GetMapping()
	public ResponseEntity<List<Pin>> getPins(@RequestParam(value = "latitude") final double latitude,
			@RequestParam(value = "longitude") double longitude,
			@RequestParam(value = "radius", required = false, defaultValue = "1000") final int radius) {
		List<Pin> pins = pinService.getPins(latitude, longitude, radius);
		return new ResponseEntity<List<Pin>>(pins, HttpStatus.OK);
	}

	@ResponseBody
	@ApiOperation(value = "핀 작성하기")
	@PostMapping()
	public ResponseEntity<Void> savePin(@RequestHeader("Authorization") String token, @RequestPart @Valid final Pin pin,
			@RequestPart("files") MultipartFile[] files, @RequestParam Map<String, String> detailContents) throws IOException {
		pinService.savePin(token, pin, files, detailContents);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@ResponseBody
	@ApiOperation(value = "핀 수정하기")
	@PutMapping("/{id}")
	public ResponseEntity<Void> updatePin(@PathVariable("id") final int id, @RequestBody @Valid final Pin pin,
			@RequestPart("files") MultipartFile[] files, @RequestParam Map<String, String> detailContents) throws IOException {
		pinService.updatePin(pin, files, detailContents);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value = "핀 삭제하기")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePin(@RequestHeader(value = "Authorization") final String token,
			@PathVariable("id") final int id) {
		pinService.deletePin(token, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value = "핀 추천")
	@PutMapping("/{id}/like")
	public ResponseEntity<Void> likePin(@RequestHeader(value = "Authorization") final String token,
			@PathVariable("id") final int id) {
		pinService.likePin(token, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value = "핀 신고")
	@PutMapping("/{id}/report")
	public ResponseEntity<Void> reportPin(@RequestHeader(value = "Authorization") final String token,
			@PathVariable("id") final int id) {
		pinService.reportPin(token, id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
