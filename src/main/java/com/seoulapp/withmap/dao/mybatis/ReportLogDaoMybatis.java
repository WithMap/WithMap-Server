package com.seoulapp.withmap.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Component;

import com.seoulapp.withmap.dao.ReportLogDao;
import com.seoulapp.withmap.model.log.ReportLog;

@Component
public class ReportLogDaoMybatis extends CommonDaoSupport implements ReportLogDao {

	@Override
	public void insert(ReportLog reportLog) {
		getSqlSession().insert("com.seoulapp.withmap.report_log.insert", reportLog);

	}

	@Override
	public List<ReportLog> getList(int userId) {
		return getSqlSession().selectList("com.seoulapp.withmap.report_log.selectList", userId);
	}

}
