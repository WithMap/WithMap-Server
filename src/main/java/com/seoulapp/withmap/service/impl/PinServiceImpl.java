package com.seoulapp.withmap.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.dao.PinDao;
import com.seoulapp.withmap.dao.PinImageDao;
import com.seoulapp.withmap.dao.RestroomDao;
import com.seoulapp.withmap.exception.NoContentException;
import com.seoulapp.withmap.exception.NotFoundException;
import com.seoulapp.withmap.exception.UnAuthorizedException;
import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.model.PinImage;
import com.seoulapp.withmap.model.PinType;
import com.seoulapp.withmap.model.PinView;
import com.seoulapp.withmap.model.error.ErrorType;
import com.seoulapp.withmap.service.FileUploadService;
import com.seoulapp.withmap.service.PinService;
import com.seoulapp.withmap.service.UserService;

@Service
public class PinServiceImpl implements PinService {

	@Autowired
	private UserService userService;

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private PinDao pinDao;

	@Autowired
	private PinImageDao pinImageDao;

	@Autowired
	private RestroomDao restroomDao;

	@Override
	public List<Pin> getPins(final double latitude, final double longitude, final int radius) {

		List<Pin> pins = pinDao.getPins(latitude, longitude, radius);

		if (pins.isEmpty()) {
			throw new NoContentException(ErrorType.NO_CONTENT, "좌표 주위에 핀이 존재하지 않습니다.");
		}

		return pins;
	}

	@Override
	public List<Pin> getUserPins(String token) {
		int userId = userService.findIdByToken(token);

		List<Pin> pins = pinDao.getPins(userId);

		if (pins.isEmpty()) {
			throw new NoContentException(ErrorType.NO_CONTENT, "등록한 핀이 없습니다.");
		}
		return pins;
	}

	@Override
	public PinView getPinById(final int id) {
		Pin pin = pinDao.get(id);

		if (pin == null)
			throw new NotFoundException(ErrorType.NOT_FOUND, "존재하지 않는 핀입니다.");

		PinView pinView = new PinView(pin, pinImageDao.getAll(id));

		// type이 restroom 일 경우 restroom 정보 추가
		if (PinType.RESTROOM.match(pin.getType()))
			pinView.setDetailContents(restroomDao.getRestroom(id));

		System.out.println(pinView.getDetailContents());

		return pinView;
	}

	@Override
	public void savePin(final String token, final Pin pin, final MultipartFile[] images,
			final Map<String, String> detailContents) {
		int userId = userService.findIdByToken(token);

		pin.setUserId(userId);
		pin.setLikeCount(0);
		pinDao.insert(pin);
		
		if (images != null) {
			List<PinImage> pinImages = getPinImages(images, pin.getId(), pin.isState());
			pinImageDao.insert(pinImages);
		}
	}

	@Override
	public void updatePin(final String token, final Pin pin, final MultipartFile[] images) {

		// 요청자에게 수정 권한이 존재하는지 확인
		int id = userService.findIdByToken(token);
		if (id != pin.getId())
			throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "pin 수정 권한이 없습니다.");

		pinDao.update(pin);

		if (images != null) {
			List<PinImage> pinImages = getPinImages(images, pin.getId(), pin.isState());
			pinImageDao.insert(pinImages);
		}
	}

	@Override
	public void deletePin(final String token, final int id) {

		// 요청자에게 삭제 권한이 존재하는지 확인
		int userId = userService.findIdByToken(token);
		if (userId != id)
			throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "pin 삭제 권한이 없습니다.");

		pinDao.delete(id);
	}

	private List<PinImage> getPinImages(MultipartFile[] images, int pinId, boolean state) {

		List<PinImage> pinImages = new ArrayList<PinImage>();
		for (MultipartFile image : images) {
			if (images.length != 0) {
				String url = fileUploadService.upload(image);

				PinImage pinImage = PinImage.builder().pinId(pinId).state(state).imagePath(url).build();
				pinImages.add(pinImage);
			}
		}
		return pinImages;
	}

	@Override
	public String imageTest(MultipartFile file) {
		return fileUploadService.upload(file);
	}
}
