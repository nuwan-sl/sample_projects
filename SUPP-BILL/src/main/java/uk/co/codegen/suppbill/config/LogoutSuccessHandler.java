/*
 * Copyright (c) 2014. CodeGen Ltd. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by nuwanw on Oct 31, 2016 @ 2:24:09 PM
 */

/**
 * 
 */
package uk.co.codegen.suppbill.config;

/**
 * @author nuwanw
 *
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

/**
 * @author nuwanw
 */
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
{
	private Logger logger = LoggerFactory.getLogger( this.getClass() );

	@Override
	public void onLogoutSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException
	{
		
		logger.info( "Inside  LogoutSuccessHandler");
		
		setRedirectStrategy( new HttpsRedirectStratergy() );

		// do whatever you want
		super.onLogoutSuccess( request, response, authentication );
	}

}
