package com.seoulapp.withmap.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.seoulapp.withmap.dao.RestroomDao;
import com.seoulapp.withmap.model.Restroom;

@Repository
public class RestroomDaoMybatis extends CommonDaoSupport implements RestroomDao {

	@Override
	public void insert(Restroom restroom) {
		getSqlSession().insert("com.seoulapp.withmap.restroom.insert", restroom);
	}

	@Override
	public void update(Restroom restroom) {
		getSqlSession().update("com.seoulapp.withmap.restroom.update", restroom);
	}

	@Override
	public void delete(int id) {
		getSqlSession().delete("com.seoulapp.withmap.restroom.delete", id);
	}

	@Override
	public Restroom getRestroom(int id) {
		return getSqlSession().selectOne("com.seoulapp.withmap.restroom.select", id);
	}

}
