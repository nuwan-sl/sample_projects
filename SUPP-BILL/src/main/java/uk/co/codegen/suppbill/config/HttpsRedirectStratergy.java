/*
 * Copyright (c) 2014. CodeGen Ltd. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by nuwanw on Oct 31, 2016 @ 2:25:33 PM
 */

/**
 * 
 */
package uk.co.codegen.suppbill.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.web.DefaultRedirectStrategy;


/**
 * @author nuwanw
 */
public class HttpsRedirectStratergy extends DefaultRedirectStrategy
{

	private Logger logger = LoggerFactory.getLogger( this.getClass() );
	
	@Override
	public void sendRedirect( HttpServletRequest request, HttpServletResponse response, String configuredUrl ) throws IOException
	{
		String logoutUrl = null;
		String referrer = request.getHeader("referer");
		if(referrer!=null){
			logoutUrl = referrer + configuredUrl;
		}
        response.sendRedirect(logoutUrl );
		logger.info( "Redirecting to configure logout success url. :" + logoutUrl );
	}
}
