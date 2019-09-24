package com.seoulapp.withmap.dao.mybatis;

import com.seoulapp.withmap.dao.RoadDao;
import com.seoulapp.withmap.model.Road;

public class RoadDaoMybatis extends CommonDaoSupport implements RoadDao{

	@Override
	public void insert(Road road) {
		getSqlSession().insert("com.seoulapp.withmap.road.insert", road);		
	}

	@Override
	public void update(Road road) {
		getSqlSession().update("com.seoulapp.withmap.road.update", road);		
	}

	@Override
	public void delete(int id) {
		getSqlSession().delete("com.seoulapp.withmap.road.delete", id);		
	}
	
	@Override
	public Road get(int id) {
		return getSqlSession().selectOne("com.seoulapp.withmap.road.select", id);
	}


}
