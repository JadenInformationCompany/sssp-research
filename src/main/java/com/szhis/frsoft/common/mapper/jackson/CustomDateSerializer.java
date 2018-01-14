package com.szhis.frsoft.common.mapper.jackson;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.szhis.frsoft.common.utils.DateUtils;

public class CustomDateSerializer extends JsonSerializer<Date> {
	
	private String dateformat;
	
	public CustomDateSerializer(String dateformat) {
		this.dateformat = dateformat;
	}
	
	public CustomDateSerializer() {
		this.dateformat = DateUtils.DATE_FORMAT_SHORT_DEFAULT; 
	}
	
	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		String formattedDate = DateUtils.format(value, this.dateformat);
		jgen.writeString(formattedDate);
	}
}
