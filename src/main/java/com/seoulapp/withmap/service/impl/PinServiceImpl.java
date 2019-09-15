package com.seoulapp.withmap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seoulapp.withmap.dao.PinDao;
import com.seoulapp.withmap.model.Pin;
import com.seoulapp.withmap.service.PinService;

@Service
public class PinServiceImpl implements PinService {
	
	@Autowired
	private PinDao pinDao;

	@Override
	public List<Pin> getPins(final double latitude, final double longitude, final int radius) {
		List<Pin> pins = pinDao.getAll();
		
		// TODO : 반지름을 통해 현재 위치에 있는 모든 핀 반환
		return pins;
	}

	@Override
	public Pin getPinById(final int id) {
		return pinDao.get(id);
	}

	@Override
	public void savePin(final Pin pin) {
		pinDao.insert(pin);
	}

	@Override
	public void updatePin(final Pin pin) {
		pinDao.update(pin);
	}

	@Override
	public void deletePin(final int id) {
		pinDao.delete(id);
	}

}
