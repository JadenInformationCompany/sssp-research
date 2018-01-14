package com.szhis.frsoft.common.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtils {

	public static Map<String, String> getMapFromRequest(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		String key;
		@SuppressWarnings("unchecked")
		Enumeration<String> keys = request.getParameterNames();
		while(keys.hasMoreElements()) {
		    key = keys.nextElement();
		    map.put(key, request.getParameter(key));
		}
		return map;
	}
	
	public static Boolean isUseJson(ServletRequest request) {
		String contentType = request.getContentType();
		if (contentType != null)
			return contentType.equalsIgnoreCase(MediaTypes.JSON_UTF_8) || contentType.equalsIgnoreCase(MediaTypes.JSON);
		else
			return false;
	}	
	
	public static void responseOutWithJson(HttpServletResponse response, String json) {
		((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaTypes.JSON_UTF_8);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(json);			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}		
	}
	
	public static String getRemoteIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取app磁盘绝对目录
	 * @param request
	 * @return
	 */
	public static String getWebAppPath(ServletRequest request) {
		String result = ((HttpServletRequest)request).getSession().getServletContext().getRealPath("/").toString();
		if (result != null) {
			return result;
		}
		try {
			/**
			 * getRealPath(“/”)方法，在.war包发布时，就会失效。会返回null。因此，我们应该避免使用getRealPath(“/”)
			 * 这样的方法来获取应用程序的绝对路径。getResource tomcat中会返回jndi路径。
			 */			
			result = ((HttpServletRequest)request).getSession().getServletContext().getResource("/").toString();

			return result.substring("file:/".length());
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
