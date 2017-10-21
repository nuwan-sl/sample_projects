package uk.co.codegen.suppbill.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.codegen.suppbill.constant.WebConstants;
import uk.co.codegen.suppbill.interceptor.DataInterceptor;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;
import org.apache.tomcat.util.buf.MessageBytes;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.JstlView;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;


/**
 * Copyright (c) 2016. CodeGen Ltd. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by nuwanw on 21/10/2017.
 */
@Configuration
@EnableWebMvc
@Import({ SecurityConfig.class })
@PropertySource({ "file:${web.server.config}/application.properties" })
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
	private static final Logger LOGGER = LoggerFactory.getLogger( WebMvcConfig.class );
	@Autowired
	Environment env;
	

	@Autowired
	@Qualifier("jstlViewResolver")
	private ViewResolver jstlViewResolver;

	@Override
	public void addResourceHandlers( ResourceHandlerRegistry registry )
	{

		String staticAngularAppPath = env.getProperty( WebConstants.STATIC_WEB_ANGULAR_APP_PATH );
		String staticConfigFilePath = env.getProperty( WebConstants.STATIC_WEB_ANGULAR_APP_CONFIG_PATH );

		String staticTemplateCssPath = env.getProperty( WebConstants.STATIC_WEB_TEMPLATE_CSS_PATH );
		String staticTemplateImagePath = env.getProperty( WebConstants.STATIC_WEB_TEMPLATE_IMAGE_PATH );
		String staticTemplateJsPath = env.getProperty( WebConstants.STATIC_WEB_TEMPLATE_JS_PATH );

		if ( staticAngularAppPath != null )
		{
			registry.addResourceHandler( "/**" )
					.addResourceLocations( WebConstants.FILE_PROTOCAL + staticAngularAppPath )
					.addResourceLocations( WebConstants.FILE_PROTOCAL + staticConfigFilePath )
					.addResourceLocations( WebConstants.FILE_PROTOCAL + staticTemplateCssPath )
					.addResourceLocations( WebConstants.FILE_PROTOCAL + staticTemplateImagePath)
					.addResourceLocations( WebConstants.FILE_PROTOCAL + staticTemplateJsPath);
		}

		LOGGER.info( "Resource locations are successfully configured." );
	}

	@Bean
	@DependsOn({ "jstlViewResolver" })
	public ViewResolver viewResolver()
	{
		return jstlViewResolver;
	}

	@Bean(name = "jstlViewResolver")
	public ViewResolver jstlViewResolver()
	{
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix( "" ); // NOTE: no preffix here
		resolver.setViewClass( JstlView.class );
		resolver.setSuffix( ".html" ); // NOTE: no suffix here
		return resolver;
	}
	
	@Bean
	public DataInterceptor dataInterceptor()
	{
		DataInterceptor di = new DataInterceptor();
		return di;
	}
	
	@Override
	public void addInterceptors( final InterceptorRegistry registry )
	{
		registry.addInterceptor( dataInterceptor() );
	}
	

	@Override
	public void addViewControllers( ViewControllerRegistry registry )
	{
		registry.addViewController( "/" ).setViewName( "forward:/index.html" );
	}

	/**
	 * Override default message to convert response to JSON data format.This will eliminate the usage of
	 * produce attribute in order to convert responsebody to JSON format explicitly
	 *
	 * @param converters
	 */
	@Override
	public void configureMessageConverters( List<HttpMessageConverter<?>> converters )
	{
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion( JsonInclude.Include.USE_DEFAULTS );
		converter.setObjectMapper( objectMapper );
		converters.add( converter );
		super.configureMessageConverters( converters );
	}
	
	
	@Bean
	public EmbeddedServletContainerCustomizer customizer()
	{
		return ( final ConfigurableEmbeddedServletContainer container ) -> {
			( ( TomcatEmbeddedServletContainerFactory ) container ).addContextValves( new ValveBase()
			{

				@Override
				public void invoke( final Request request, final Response response ) throws IOException, ServletException
				{

					final MessageBytes serverNameMB = request.getCoyoteRequest().serverName();
					String originalServerName = null;
					final String forwardedHost = request.getHeader( WebConstants.X_FORWARDED_HOST );
					if ( forwardedHost != null )
					{
						originalServerName = serverNameMB.getString();
						serverNameMB.setString( forwardedHost );
					}

					try
					{
						getNext().invoke( request, response );
					}
					finally
					{
						if ( forwardedHost != null )
						{
							serverNameMB.setString( originalServerName );
						}
					}
				}
			} );
		};
	}
}
