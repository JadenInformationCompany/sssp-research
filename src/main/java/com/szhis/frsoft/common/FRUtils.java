package com.szhis.frsoft.common;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Locale;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.NoSuchMessageException;

import com.szhis.frsoft.common.exception.Exceptions;
import com.szhis.frsoft.common.factory.config.PropertyPlaceholderConfigurer;
import com.szhis.frsoft.common.utils.chinesechar.IGenerateIMECodePY;
import com.szhis.frsoft.common.utils.chinesechar.IGenerateIMECodeWB;

public class FRUtils {	
	
	public static String getProperty(String key) {
		if (System.getProperties().containsKey(key)) {
			return System.getProperties().getProperty(key);
		}
		return PropertyPlaceholderConfigurer.getProperty(key);
	}
	
	public static String getProperty(String key, String defaultValue) {
		if (System.getProperties().containsKey(key)) {
			return System.getProperties().getProperty(key, defaultValue);
		}		
		return PropertyPlaceholderConfigurer.getProperty(key, defaultValue);
	}
	
	public static Boolean containsProperty(String key) {
		if (System.getProperties().containsKey(key)) {
			return true;
		}
		return PropertyPlaceholderConfigurer.getProperties().containsKey(key);
	}
	
	public static String getPym(final String text) {
		return IGenerateIMECodePY.getInstance().generateShortCode(text);
	}
	
	public static String getWbm(final String text) {
		return IGenerateIMECodeWB.getInstance().generateShortCode(text);
	}
	
	public static String parseTableNameFromColumn(String columnName) {
		if(columnName == null)
			return null; 
		
		int i = columnName.indexOf("_");
		
		if(i == -1) {
			return null;
		}
		int j = columnName.indexOf("_", i+1);
		return j == -1 ? columnName.substring(0, i) : columnName.substring(i+1, j);
	}
	
	/**
	 * 
	 * @param columnName
	 * @return 返回null解析失败，否则数组第0个元素保存表名，第一个原酸保存关联的字段名
	 */
	public static String[] parseRelationTableAndColumn(String columnName) {
		if(columnName == null)
			return null; 
		int i = columnName.indexOf("_");
		
		if(i == -1) {
			return null;
		}
		int j = columnName.indexOf("_", i+1);
		String[] result = new String[2];
		if(j == -1) {
			result[0] = columnName.substring(0, i);
			result[1] = columnName.substring(i+1);
		} else {
			result[0] = columnName.substring(i+1, j);
			result[1] = columnName.substring(j+1);
		}
		return result;
	}
	/**
	 * 
	 * @param type java.sql.Types
	 * @return
	 */
	public static Class<?> jdbcTypeToJavaType(int type) {
		switch (type) {
		case java.sql.Types.INTEGER:
			return Integer.class;			
		case java.sql.Types.CHAR:
		case java.sql.Types.VARCHAR:
		case java.sql.Types.LONGVARCHAR:
			return String.class;
		case java.sql.Types.NUMERIC:
		case java.sql.Types.DECIMAL:
			return BigDecimal.class;
		case java.sql.Types.BIT:
			return Boolean.class;		
		case java.sql.Types.TINYINT:
			return Byte.class;
		case java.sql.Types.SMALLINT:
			return Short.class;
		case java.sql.Types.BIGINT:
			return Long.class;
		case java.sql.Types.DATE:
		case java.sql.Types.TIMESTAMP:
			return java.util.Date.class;
		default:
			throw new RuntimeException("cant convert java.sql.Types");
		}
	}
	
	
	//MessageFormat格式 
	public static String getMessage(final String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return SpringApplicationContext.getApplicationContext().getMessage(code, args, locale);
	}
	
	public static String getMessage(final String code, Object... args) throws NoSuchMessageException {
		return SpringApplicationContext.getApplicationContext().getMessage(code, args, null);
	}
	
	/*public static String getMessage(final String code) throws NoSuchMessageException {
		return SpringApplicationContext.getApplicationContext().getMessage(code, null, null);
	}*/
	
	public static String getMessage(final String code, Object[] args, String defaultMessage, Locale locale) {
		return SpringApplicationContext.getApplicationContext().getMessage(code, args, defaultMessage, locale);
	}

	public static String getMessage(final String code, Object[] args, String defaultMessage) {
		return SpringApplicationContext.getApplicationContext().getMessage(code, args, defaultMessage, null);
	}
	
	public static String getMessage(final String code, String defaultMessage) {
		return SpringApplicationContext.getApplicationContext().getMessage(code, null, defaultMessage, null);
	}
	
	/**
	 * 解析存储过程返回的XML，同时检查是否执行成功
	 * @return 返回顶级节点
	 */
	public static Element checkStoredProcRetXML(String retXML) {
		SAXReader saxReader = new SAXReader();
		Document document;
		Element root;
		try {
			document = saxReader.read(new ByteArrayInputStream(retXML.getBytes("UTF-8")));
			root = document.getRootElement();
			Integer retCode = Integer.valueOf(root.element("retCode").getText());
			if(retCode.intValue() != 0) {
				String retMsg = root.element("retMsg").getText();				
				/*Pattern pattern = Pattern.compile("FR_MSG:\\(msgCode=(.+?)\\)");
				Matcher matcher = pattern.matcher(retMsg); // 获取消息代码
				if(matcher.find()) {
					String msgCode = matcher.group(1);
				}*/
				throw new RuntimeException(retMsg);
			}
		} catch (DocumentException e) {
			throw Exceptions.unchecked(e);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}	
		return root;
	}
}
