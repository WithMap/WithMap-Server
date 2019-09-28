package com.seoulapp.withmap.converter;

import java.io.IOException;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateTimeSerializer extends JsonSerializer<DateTime> {

	@Override
	public void serialize(DateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeNumber(value.toString("yyyy-MM-dd HH:mm:ss"));
	}

}