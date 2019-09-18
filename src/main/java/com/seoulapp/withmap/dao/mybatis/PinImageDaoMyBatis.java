package com.seoulapp.withmap.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.seoulapp.withmap.dao.PinImageDao;
import com.seoulapp.withmap.model.PinImage;

@Repository
public class PinImageDaoMyBatis extends CommonDaoSupport implements PinImageDao {

	@Override
	public List<PinImage> getAll(int pinId) {
		return getSqlSession().selectList("com.seoulapp.withmap.pinImage.selectList", pinId);
	}

	@Override
	public void insert(List<PinImage> pinImage) {
		getSqlSession().insert("com.seoulapp.withmap.pinImage.insert", pinImage);
	}

}
