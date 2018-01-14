package com.szhis.frsoft.common.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 当浏览器检测到跨域访问，会向服务端发送OPTIONS请求，这里通过过滤器拦截OPTIONS方法，通过响应头告诉
 * 浏览器服务端支持跨域访问。浏览器接收到服务端支持跨域访问，再向服务端发送真实请求，当真实请求发送
 * 到服务端servlet,我们同样需要在响应头中包含Access-Control-Allow-Origin
 * @author Administrator
 *
 */
public class CorsFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//response.addHeader("Access-Control-Allow-Origin", "*");
		if (request.getHeader("Access-Control-Request-Method") != null
				&& "OPTIONS".equals(request.getMethod())) {
			// CORS "pre-flight" request
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			//response.addHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,Content-Type"); 
			response.addHeader("Access-Control-Max-Age", "1800");// 30 min
		}
		//校验提交数据合法性,可以防止中途被修改数据、方便客户端通过机构访问集团
//		String md5 = request.getHeader("frCheckKey");
//		if (md5!=null && md5.length()>0){
//			
//		}
//		//判断是否代理访问
//		if("true".compareTo(request.getHeader("frProxy"))==0){
//			//判断来源是否合法
//		}
	
		filterChain.doFilter(request, response);
	}
	
	 /*private Properties prop = new Properties();
	 
	 @Override
	 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		 InputStream in = getClass().getResourceAsStream("/META-INF/app.properties");
		 prop.load(in);
		 in.close();
		 
		 Set<String> allowedOrigins = new HashSet<String>(Arrays.asList (prop.getProperty("allowed.origins").split(",")));
		 if(request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
			 String originHeader = request.getHeader("Origin");
			 if(allowedOrigins.contains(request.getHeader("Origin")))
				 response.addHeader("Access-Control-Allow-Origin", originHeader);
			 response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			 response.addHeader("Access-Control-Allow-Headers", "Content-Type");
			 response.addHeader("Access-Control-Max-Age", "1800");
		 }
		 filterChain.doFilter(request, response);
	 } */	
	
}

/*以下配置和代码等价
	<filter>
		<filter-name>corsFilter</filter-name>
    	<filter-class>com.szhis.frsoft.common.web.CorsFilter</filter-class>
    </filter>
    <filter-mapping>
    	<filter-name>corsFilter</filter-name>
    	<url-pattern>/*</url-pattern>
    </filter-mapping>
    
 public class AppInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(javax.servlet.ServletContext servletContext) throws ServletException {、
		 Other code omitted for brevity 
		FilterRegistration.Dynamic corsFilter = servletContext.addFilter("corsFilter", CORSFilter.class);
		corsFilter.addMappingForUrlPatterns(null, false, "/*");
	}
} */
