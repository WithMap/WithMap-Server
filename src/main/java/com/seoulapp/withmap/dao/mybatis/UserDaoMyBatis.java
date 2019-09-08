package com.seoulapp.withmap.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.seoulapp.withmap.dao.UserDao;
import com.seoulapp.withmap.model.User;

@Repository
public class UserDaoMyBatis extends CommonDaoSupport implements UserDao {

	@Override
	public void insert(User user) {
		getSqlSession().insert("com.seoulapp.withmap.user.insert", user);
	}

	@Override
	public void update(User user) {
		getSqlSession().update("com.seoulapp.withmap.user.update", user);
	}

	@Override
	public User get(int id) {
		return getSqlSession().selectOne("com.seoulapp.withmap.user.select", id);
	}

}
