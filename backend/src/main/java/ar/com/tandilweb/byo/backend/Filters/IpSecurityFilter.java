package ar.com.tandilweb.byo.backend.Filters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class IpSecurityFilter implements HandlerInterceptor {
	
	public static List<String> allowedIps = new ArrayList<String>();
	
	protected static Properties prop = new Properties();

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//IpSecurityFilter.getAllowedOfProperties(System.getenv("HOME")+"/ip-whitelist.properties");
		String ipCliente = this.getClientIP(request);
		this.logAccessSecurityFilter(ipCliente, "validating");
		boolean allowed = false;
		for(String ip : IpSecurityFilter.allowedIps)
			if(ipCliente.equals(ip)) {
				allowed = true;
				this.logAccessSecurityFilter(ipCliente, "ACCESS ALLOWED");
			}
		if(!allowed){
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
			response.addHeader("Location", "/home"); // redireccion
			this.logAccessSecurityFilter(ipCliente, "ACCESS NOT ALLOWED");
		}
		return allowed;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}
	
	private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}
	
	private void logAccessSecurityFilter(String ip, String action){
		//IpSecurityFilter.logger.info("IpSecurityFilter "+action+" "+ip);
	}
	
	public static void getAllowedOfProperties(String file){
		try {
			IpSecurityFilter.prop.load(new FileInputStream(file));
			String list = IpSecurityFilter.prop.getProperty("whiteList");
			//IpSecurityFilter.logger.debug("IpSecurityFilter ip WHITELIST load: "+list);
			IpSecurityFilter.allowedIps = Arrays.asList(list.split(","));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//IpSecurityFilter.logger.warn("IpSecurityFilter Cannot load Property File "+file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//IpSecurityFilter.logger.warn("IpSecurityFilter Cannot load Property File "+file);
		}
	}

}
