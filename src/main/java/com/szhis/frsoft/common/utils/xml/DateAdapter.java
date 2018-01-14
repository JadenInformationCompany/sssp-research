package com.szhis.frsoft.common.utils.xml;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.szhis.frsoft.common.utils.DateUtils;

public class DateAdapter extends XmlAdapter<String, Date> {

    @Override
    public String marshal(Date date) throws Exception {
    	return DateUtils.format(date); 
    }

    @Override
    public Date unmarshal(String dateStr) throws Exception {
        return DateUtils.parse(dateStr);
    }
}
