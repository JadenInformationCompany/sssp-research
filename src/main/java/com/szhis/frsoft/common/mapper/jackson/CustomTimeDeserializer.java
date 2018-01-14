package com.szhis.frsoft.common.mapper.jackson;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.szhis.frsoft.common.utils.DateUtils;

public class CustomTimeDeserializer extends JsonDeserializer<Date> {
	
	@Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String datestr = jp.getText();
        if(datestr.equals(""))
        	return null;	
        return DateUtils.parse(datestr, "H:m:s");        
    }
}
