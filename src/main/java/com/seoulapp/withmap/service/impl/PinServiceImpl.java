package com.seoulapp.withmap.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.dao.LikeLogDao;
import com.seoulapp.withmap.dao.PinDao;
import com.seoulapp.withmap.dao.PinImageDao;
import com.seoulapp.withmap.dao.ReportLogDao;
import com.seoulapp.withmap.dao.RestroomDao;
import com.seoulapp.withmap.dao.RoadDao;
import com.seoulapp.withmap.exception.AlreadyExistException;
import com.seoulapp.withmap.exception.NoContentException;
import com.seoulapp.withmap.exception.NotFoundException;
import com.seoulapp.withmap.exception.UnAuthorizedException;
import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.model.PinImage;
import com.seoulapp.withmap.model.PinType;
import com.seoulapp.withmap.model.PinView;
import com.seoulapp.withmap.model.Restroom;
import com.seoulapp.withmap.model.Road;
import com.seoulapp.withmap.model.error.ErrorType;
import com.seoulapp.withmap.model.log.LikeLog;
import com.seoulapp.withmap.model.log.ReportLog;
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

	@Autowired
	private RoadDao roadDao;

	@Autowired
	private LikeLogDao likeLogDao;

	@Autowired
	private ReportLogDao reportLogDao;
	
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
	public PinView getPinById(final String token, final int id) {
		Pin pin = pinDao.get(id);

		if (pin == null)
			throw new NotFoundException(ErrorType.NOT_FOUND, "존재하지 않는 핀입니다.");

		PinView pinView = new PinView(pin, pinImageDao.getAll(id));

		// 자신의 글인지 확인
		int userId = userService.findIdByToken(token);
		Integer pinAuthorId = pin.getUserId();
		if (pinAuthorId != null) {
			pinView.setMine(userId == pinAuthorId.intValue());
		}

		// 이미 추천한 글인지 확인
		List<LikeLog> likes = likeLogDao.getList(userId);
		if (likes != null) {
			boolean isRecommended = likes.stream().map(LikeLog::getPinId).anyMatch(p -> p == id);
			pinView.setRecommended(isRecommended);
		}

		// type이 restroom 일 경우 restroom 정보 추가
		if (PinType.RESTROOM.match(pin.getType()))
			pinView.setDetailContents(restroomDao.getRestroom(id));

		return pinView;
	}

	@Override
	@Transactional
	public void savePin(final String token, final Pin pin, final MultipartFile[] images,
			final Map<String, String> detailContents) throws IOException {
		int userId = userService.findIdByToken(token);

		pin.setUserId(userId);
		pin.setLikeCount(0);
		pinDao.insert(pin);

		if (images != null && images.length != 0)
			pinImageDao.insert(getPinImages(images, pin.getId(), pin.isState()));

		int pinId = pin.getId();
		PinType pinType = PinType.valueOf(pin.getType());
		setDetailContents(pinId, pinType, detailContents, "SAVE");
	}


	@Override
	@Transactional
	public void updatePin(final Pin pin, final MultipartFile[] images,
			final Map<String, String> detailContents) throws IOException {

		pinDao.update(pin);

		if (images != null && images.length != 0) {
			List<PinImage> pinImages = getPinImages(images, pin.getId(), pin.isState());
			pinImageDao.insert(pinImages);
		}
		
		int pinId = pin.getId();
		PinType pinType = PinType.valueOf(pin.getType());
		setDetailContents(pinId, pinType, detailContents, "UPDATE");
	}

	@Override
	@Transactional
	public void deletePin(final String token, final int id) {

		// 요청자에게 삭제 권한이 존재하는지 확인
		int userId = userService.findIdByToken(token);
		if (userId != id)
			throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "pin 삭제 권한이 없습니다.");

		pinDao.delete(id);
	}



	@Override
	@Transactional
	public void likePin(String token, int pinId) {

		int userId = userService.findIdByToken(token);

		// 로그 확인 후 중복 확인 체크
		List<LikeLog> likeLogs = likeLogDao.getList(userId);
		for (LikeLog log : likeLogs) {
			if (log.getPinId() == pinId)
				throw new AlreadyExistException(ErrorType.CONFLICT, "이미 추천한 PIN 입니다.");
		}

		// 로그 추가
		LikeLog likeLog = new LikeLog();
		likeLog.setPinId(pinId);
		likeLog.setUserId(userId);

		likeLogDao.insert(likeLog);

		// 좋아요
		pinDao.updateLikeCount(pinId);

	}

	@Override
	@Transactional
	public void reportPin(String token, int pinId) {
		int userId = userService.findIdByToken(token);

		// 로그 확인 후 중복 확인 체크
		List<ReportLog> reportLogs = reportLogDao.getList(userId);
		for (ReportLog log : reportLogs) {
			if (log.getPinId() == pinId)
				throw new AlreadyExistException(ErrorType.CONFLICT, "이미 신고한 PIN 입니다.");
		}

		// 로그 추가
		ReportLog reportLog = new ReportLog();
		reportLog.setPinId(pinId);
		reportLog.setUserId(userId);

		reportLogDao.insert(reportLog);
	}
	
	private List<PinImage> getPinImages(MultipartFile[] images, int pinId, boolean state) throws IOException {
		
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

	private void setDetailContents(int pinId, PinType pinType, Map<String, String> detailContents, String method) {
		switch (pinType) {
		case OBSTACLE:
		case CURB:
		case DIRTROAD:
		case NARROWROAD:
			Road road = new Road();
			road.setId(pinId);
			road.setComment(detailContents.get("comment"));

			// ㅠㅠ구리지만....
			if(method.equals("SAVE"))
				roadDao.insert(road);
			else if(method.equals("UPDATE"))
				roadDao.update(road);
			break;
		case RESTROOM:
			Restroom restroom = new Restroom();

			restroom.setId(pinId);
			restroom.setUseableTime(detailContents.get("useableTime"));
			restroom.setDepartmentNumber(detailContents.get("departmentNumber"));

			if(method.equals("SAVE"))
				restroomDao.insert(restroom);
			else if(method.equals("UPDATE"))
				restroomDao.update(restroom);
			break;
		case RESTAURANT:
			break;
		}
		
	}

}
