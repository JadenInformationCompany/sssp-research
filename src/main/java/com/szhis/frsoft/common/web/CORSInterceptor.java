package com.szhis.frsoft.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CORSInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		return true;
	}	
	 
	/*@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		InputStream in = getClass().getResourceAsStream("/META-INF/app.properties");
		Properties prop = new Properties();
		prop.load(in);
		in.close();
	 
		Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(prop.getProperty("allowed.origins").split(",")));
	 
		String origin = request.getHeader("Origin");
		if(allowedOrigins.contains(origin)) {
			response.addHeader("Access-Control-Allow-Origin", origin);
			return true;
		} else {
			return false;
		}
	}*/
} 

/*通过写代码配置启用拦截器
@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CORSInterceptor());
	}
}*/ 
