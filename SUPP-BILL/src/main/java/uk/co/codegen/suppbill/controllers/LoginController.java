package uk.co.codegen.suppbill.controllers;

import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.codegen.suppbill.constant.WebConstants;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@Autowired
	Environment env;
	
	
    @RequestMapping("/login")
    public String login(@CookieValue("X-XSRF-TOKEN") String token, @RequestParam(value="error", required=false)String error , Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("_csrf", token);  
        model.addAttribute("error", request.getParameter("error")); 
        return "login";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
    	
    	String webBase = env.getProperty( WebConstants.WEB_BASE_URL );
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        
        return "redirect:"+webBase+"login?logout=true";
    }
    
	@RequestMapping("/userlogout")
	public String userlogout( HttpServletRequest request, HttpServletResponse response, Model model )
	{
		String webBaseLoginUrl = env.getProperty( WebConstants.WEB_BASE_LOGIN_URL );
		return "redirect:" + webBaseLoginUrl;
	}
}
