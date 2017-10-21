package uk.co.codegen.suppbill.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import uk.co.codegen.suppbill.constant.WebConstants;

public class DataInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	Environment env;
	
	
	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) throws Exception
	{
		String webBase = env.getProperty( WebConstants.WEB_BASE_URL );
		if(modelAndView!=null){
			
			modelAndView.getModel().put("webbase", webBase);
			

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth!=null && auth.getName()!=null){
				String name = auth.getName();
				modelAndView.getModel().put("loginuser", name);

			}
		}
		
		
	}
}
