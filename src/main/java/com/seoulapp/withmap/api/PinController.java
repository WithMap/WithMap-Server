package com.seoulapp.withmap.api;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.service.PinService;

@RestController
@RequestMapping("/withmap/pins")
public class PinController {

	@Autowired
	private PinService pinService;

	@GetMapping("/{id}")
	public ResponseEntity<Pin> getPinById(@PathVariable("id") final int id) {
		Pin pin = pinService.getPinById(id);
		return new ResponseEntity<Pin>(pin, HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<List<Pin>> getPins(@RequestParam(value = "latitude") final double latitude,
			@RequestParam(value = "longitude") double longitude,
			@RequestParam(value = "radius", required = false, defaultValue = "10") final int radius) {
		List<Pin> pins = pinService.getPins(latitude, longitude, radius);
		return new ResponseEntity<List<Pin>>(pins, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<Void> savePin(@RequestBody @Valid final Pin pin) {
		pinService.savePin(pin);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updatePin(@PathVariable("id") final int id, @RequestBody @Valid final Pin pin) {
		pinService.updatePin(pin);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePin(@PathVariable("id") final int id) {
		pinService.deletePin(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}