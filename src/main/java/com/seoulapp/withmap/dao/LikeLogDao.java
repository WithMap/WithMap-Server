package com.seoulapp.withmap.dao;

import java.util.List;

import com.seoulapp.withmap.model.log.LikeLog;

public interface LikeLogDao {
	
	void insert(final LikeLog likeLog);
	
	List<LikeLog> getList(final int userId);

}
