package com.seoulapp.withmap.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seoulapp.withmap.dao.LikeLogDao;
import com.seoulapp.withmap.dao.PinDao;
import com.seoulapp.withmap.dao.PinImageDao;
import com.seoulapp.withmap.dao.ReportLogDao;
import com.seoulapp.withmap.dao.RestaurantDao;
import com.seoulapp.withmap.dao.RestroomDao;
import com.seoulapp.withmap.dao.RoadDao;
import com.seoulapp.withmap.exception.AlreadyExistException;
import com.seoulapp.withmap.exception.BadRequestException;
import com.seoulapp.withmap.exception.ForbiddenException;
import com.seoulapp.withmap.exception.NoContentException;
import com.seoulapp.withmap.exception.NotFoundException;
import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.model.PinImage;
import com.seoulapp.withmap.model.PinType;
import com.seoulapp.withmap.model.PinView;
import com.seoulapp.withmap.model.Restaurant;
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

	@Autowired
	private RestaurantDao restaurantDao;

	private static final double RADIUS = 300;

	@Override
	public List<Pin> getPins(final double latitude, final double longitude) {

		List<Pin> pins = pinDao.getPins(latitude, longitude, RADIUS);

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

		int userId = userService.findIdByToken(token);

		if (pin == null)
			throw new NotFoundException(ErrorType.NOT_FOUND, "존재하지 않는 핀입니다.");

		PinView pinView = new PinView(pin, pinImageDao.getAll(id));

		// 자신의 글인지 확인
		Integer pinAuthorId = pin.getUserId();
		if (pinAuthorId != null) {
			pinView.setMine(userId == pinAuthorId.intValue());
		}

		// 이미 추천한 글인지 확인
		List<LikeLog> likes = likeLogDao.getList(userId);
		Set<Integer> pins = likes.stream().map(l -> l.getPinId()).collect(Collectors.toSet());
		// 추천 로그가 없으면 자기 자신 추천 x
		pinView.setRecommended(false);
		pins.stream().filter(pinId -> pinId == id).peek(pinId -> pinView.setRecommended(true)).findFirst();

		// 세부 사항 추가 TODO : 식당 정보 추가
		switch (PinType.valueOf(pin.getType())) {
		case OBSTACLE:
		case CURB:
		case DIRTROAD:
		case NARROWROAD:
			Road road = roadDao.get(id);
			pinView.setDetailContents(road);
			break;
		case RESTROOM:
			Restroom restroom = restroomDao.get(id);
			pinView.setDetailContents(restroom);
			break;
		case RESTAURANT:
			Restaurant restaurant = restaurantDao.get(id);
			pinView.setDetailContents(restaurant);
			break;
		}

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

		// type 으로 분류
		switch (PinType.valueOf(pin.getType())) {
		case OBSTACLE:
		case CURB:
		case DIRTROAD:
		case NARROWROAD:
			break;
		case RESTROOM:
			Restroom restroom = new Restroom();

			restroom.setId(pin.getId());
			restroom.setUseableTime(detailContents.getOrDefault("useableTime", null));
			restroom.setDepartmentNumber(detailContents.getOrDefault("departmentNumber", null));

			restroomDao.insert(restroom);
			break;
		case RESTAURANT:
			Restaurant restaurant = new Restaurant();

			restaurant.setId(pin.getId());
			restaurant.setNumber(detailContents.getOrDefault("number", null));
			restaurant.setSite(detailContents.getOrDefault("site", null));
			restaurant.setUseableTime(detailContents.getOrDefault("useableTime", null));

			restaurantDao.insert(restaurant);
			break;
		}
	}

	@Override
	@Transactional
	public void updatePin(final int id, final Pin pin, final MultipartFile[] images,
			final Map<String, String> detailContents) throws IOException {
		
		if(detailContents == null || detailContents.isEmpty())
			throw new BadRequestException(ErrorType.BAD_REQUEST, "핀 상세 정보가 없습니다.");

		pin.setId(id);
		pinDao.update(pin);

		if (images != null) {
			List<PinImage> pinImages = getPinImages(images, pin.getId(), pin.isState());
			pinImageDao.insert(pinImages);
		}

		// type 으로 분류
		switch (PinType.valueOf(pin.getType())) {
		case OBSTACLE:
		case CURB:
		case DIRTROAD:
		case NARROWROAD:
			break;
		case RESTROOM:
			Restroom restroom = restroomDao.get(id);

			restroom.setUseableTime(detailContents.getOrDefault("useableTime", restroom.getUseableTime()));
			restroom.setDepartmentNumber(
					detailContents.getOrDefault("departmentNumber", restroom.getDepartmentNumber()));

			restroomDao.update(restroom);
			break;
		case RESTAURANT:
			Restaurant restaurant = restaurantDao.get(id);

			restaurant.setNumber(detailContents.getOrDefault("number", restaurant.getNumber()));
			restaurant.setSite(detailContents.getOrDefault("site", restaurant.getSite()));
			restaurant.setUseableTime(detailContents.getOrDefault("useableTime", restaurant.getUseableTime()));
			
			restaurantDao.update(restaurant);
			break;
		}

	}

	@Override
	@Transactional
	public void deletePin(final String token, final int id) {

		// 요청자에게 삭제 권한이 존재하는지 확인
		int userId = userService.findIdByToken(token);
		if (userId != id)
			throw new ForbiddenException(ErrorType.FORBIDDEN, "pin 삭제 권한이 없습니다.");

		Pin pin = pinDao.get(id);

		// 세부 사항 삭제
		switch (PinType.valueOf(pin.getType())) {
		case OBSTACLE:
		case CURB:
		case DIRTROAD:
		case NARROWROAD:
			roadDao.delete(id);
			break;
		case RESTROOM:
			restroomDao.delete(id);
			break;
		case RESTAURANT:
			restaurantDao.delete(id);
			break;
		}

		// 공통 사항 삭제
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

}
