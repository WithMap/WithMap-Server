package com.seoulapp.withmap.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Component;

import com.seoulapp.withmap.dao.LikeLogDao;
import com.seoulapp.withmap.model.log.LikeLog;

@Component
public class LikeLogDaoMybatis extends CommonDaoSupport implements LikeLogDao{

	@Override
	public void insert(LikeLog likeLog) {
		getSqlSession().insert("com.seoulapp.withmap.like_log.insert", likeLog);
	}

	@Override
	public List<LikeLog> getList(int userId) {
		return getSqlSession().selectList("com.seoulapp.withmap.like_log.selectList", userId);
	}

}
