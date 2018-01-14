package com.szhis.frsoft.common.utils.xml;


import java.math.BigDecimal;
import java.util.Date;

import org.dom4j.Element;

import com.szhis.frsoft.common.utils.DateUtils;

public class Dom4jUtils {

	public final static String getChildText(Element element, String childName) {
		Element child = element.element(childName);
		if(child != null) {
			return child.getText();
		}
		return null;
	}
	
	public final static Integer getChildTextAsInt(Element element, String childName) {
		String text = Dom4jUtils.getChildText(element, childName);
		if(text == null || text.length() == 0) { 
			return null;
		}
		return Integer.valueOf(text);
	}
	
	public final static BigDecimal getChildTextAsBigDecimal(Element element, String childName) {
		String text = Dom4jUtils.getChildText(element, childName);
		if(text == null || text.length() == 0) {
			return null;
		}
		return new BigDecimal(text);
	}	
	
	public static Date getChildTextAsDate(Element element, String childName, String... dateFmt) {
		String text = Dom4jUtils.getChildText(element, childName);
		if(text == null || text.length() == 0) {
			return null;
		}
		String sdateFmt;
    	if(dateFmt.length == 0) {
        	if(text.length() == 10) {
        		sdateFmt = DateUtils.DATE_FORMAT_SHORT_DEFAULT;
        	} else if(text.contains("T")) {
        		sdateFmt = "yyyy-MM-dd'T'HH:mm:ss";
        	} else {
        		sdateFmt = DateUtils.DATE_FORMAT_LONG_DEFAULT;
        	}
    	} else {
    		sdateFmt = dateFmt[0];
    	}
		return DateUtils.parse(text, sdateFmt);
	}	
}
