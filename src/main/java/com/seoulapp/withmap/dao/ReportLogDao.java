package com.seoulapp.withmap.dao;

import java.util.List;

import com.seoulapp.withmap.model.log.ReportLog;

public interface ReportLogDao {

	void insert(final ReportLog reportLog);

	List<ReportLog> getList(final int userId);

}
