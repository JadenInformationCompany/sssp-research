package com.szhis.frsoft.common.mapper.jackson;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.szhis.frsoft.common.utils.DateUtils;

public class CustomTimeSerializer  extends JsonSerializer<Date> {
	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		String formattedDate = DateUtils.format(value, "HH:mm:ss");
		jgen.writeString(formattedDate);
	}
}
