package com.seoulapp.withmap.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.seoulapp.withmap.dao.PinDao;
import com.seoulapp.withmap.model.Pin;

@Repository
public class PinDaoMyBatis extends CommonDaoSupport implements PinDao {

	@Override
	public List<Pin> getAll() {
		return getSqlSession().selectList("com.seoulapp.withmap.pin.selectList");
	}

	@Override
	public Pin get(final int id) {
		return getSqlSession().selectOne("com.seoulapp.withmap.pin.select", id);
	}

	@Override
	public void insert(final Pin pin) {
		getSqlSession().insert("com.seoulapp.withmap.pin.insert", pin);		
	}

	@Override
	public void update(final Pin pin) {
		getSqlSession().update("com.seoulapp.withmap.pin.update", pin);		
	}

	@Override
	public void delete(final int id) {
		getSqlSession().delete("com.seoulapp.withmap.pin.delete", id);		
	}

}
