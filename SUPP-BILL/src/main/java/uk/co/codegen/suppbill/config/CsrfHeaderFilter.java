/**
 * Copyright (c) 2016. CodeGen Ltd. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Angular 2.0 CSRF support integration for Spring security
 * Created by lasitha on 8/15/2016.
 */
package uk.co.codegen.suppbill.config;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsrfHeaderFilter extends OncePerRequestFilter
{
	public static final String X_CSRF_TOKEN = "X-XSRF-TOKEN";

	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException
	{
		CsrfToken csrf = ( CsrfToken ) request.getAttribute( CsrfToken.class.getName() );
		if ( csrf != null )
		{
			Cookie cookie = WebUtils.getCookie( request, X_CSRF_TOKEN );
			String token = csrf.getToken();
			if ( cookie == null || token != null && !token.equals( cookie.getValue() ) )
			{
				cookie = new Cookie( X_CSRF_TOKEN, token );
				cookie.setPath( "/" );
				response.addCookie( cookie );
			}
		}
		filterChain.doFilter( request, response );
	}
}
