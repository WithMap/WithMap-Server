package com.seoulapp.withmap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.dao.PinDao;
import com.seoulapp.withmap.dao.PinImageDao;
import com.seoulapp.withmap.exception.NotFoundException;
import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.model.PinImage;
import com.seoulapp.withmap.model.PinView;
import com.seoulapp.withmap.model.error.ErrorType;
import com.seoulapp.withmap.service.GCPStorageService;
import com.seoulapp.withmap.service.PinService;

@Service
public class PinServiceImpl implements PinService {

	
	@Autowired
	private GCPStorageService gcpStorageService;
	
	@Autowired
	private PinDao pinDao;

	@Autowired
	private PinImageDao pinImageDao;

	@Override
	public List<Pin> getPins(final double latitude, final double longitude, final int radius) {
		
		return pinDao.getPins(latitude, longitude, radius);
	}

	@Override
	public PinView getPinById(final int id) {
		Pin pin = pinDao.get(id);
		
		if(pin == null)
			throw new NotFoundException(ErrorType.NOT_FOUND, "존재하지 않는 핀입니다.");
		
		return new PinView(pin, pinImageDao.getAll(id));
	}

	@Override
	public void savePin(final Pin pin, final MultipartFile[] images) {
		pinDao.insert(pin);
		
		List<PinImage> pinImages = getPinImages(images, pin.getId(), pin.getState());
		pinImageDao.insert(pinImages);
		
	}

	@Override
	public void updatePin(final Pin pin, final MultipartFile[] images) {

		pinDao.update(pin);

		List<PinImage> pinImages = getPinImages(images, pin.getId(), pin.getState());
		pinImageDao.insert(pinImages);

	}

	@Override
	public void deletePin(final int id) {
		pinDao.delete(id);
	}

	
	private List<PinImage> getPinImages(MultipartFile[] images, int pinId, int state) {
		
		List<PinImage> pinImages = new ArrayList<PinImage>();
		for (MultipartFile image : images) {
			if (images.length != 0) {
				String url = gcpStorageService.upload(image);

				PinImage pinImage = PinImage.builder().pinId(pinId).state(state).imagePath(url).build();
				pinImages.add(pinImage);
			}
		}
		
		return pinImages;
	}
}
