package com.seoulapp.withmap.converter;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class DateTimeModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public DateTimeModule() {
		super();
		// 모듈에 Serializer를 등록해 준다.
		addSerializer(DateTime.class, new DateTimeSerializer());
	}
}