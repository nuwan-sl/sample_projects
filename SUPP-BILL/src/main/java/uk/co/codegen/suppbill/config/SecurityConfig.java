package uk.co.codegen.suppbill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import uk.co.codegen.suppbill.constant.WebConstants;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	Environment env;
	
	@Autowired
	LogoutSuccessHandler logoutSuccessHandler;
	
	private static final String X_CSRF_TOKEN = "X-XSRF-TOKEN";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		String webBase = env.getProperty( WebConstants.WEB_BASE_URL );
		
		http
				.authorizeRequests()
				.antMatchers( "/**/*.js", "/**/*.css","/**/*.png","/**/*.jpg","/**/*.jpeg","/**/*.gif","/**l/**","/**/login/**","/**/userlogout").permitAll()
				.and()
				.formLogin()
				.loginProcessingUrl( "/process" ).loginPage("/login")
				.usernameParameter( "username" )
				.passwordParameter( "password" )
				/*.defaultSuccessUrl( "/", true ).failureUrl("/login?error=true")*/
				.defaultSuccessUrl( webBase, true ).failureUrl("/login?error=true")
				.permitAll()
				.and()
				.logout().logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ))
				/*.logoutSuccessUrl( webBase )*/
				.logoutSuccessHandler( logoutSuccessHandler )
				.and()
				.csrf()
				.csrfTokenRepository( csrfTokenRepository() )
				.and()
				.addFilterAfter( new CsrfHeaderFilter(), CsrfFilter.class);
		http.authorizeRequests().anyRequest().authenticated();
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		  auth.inMemoryAuthentication().withUser("nuwan").password("123").roles("ADMIN");
		  auth.inMemoryAuthentication().withUser("sampth").password("123").roles("USER");
	}
	

	/**
	 * manging CSRF cookie for Angular JS 2.0
	 *
	 * @return
	 */
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName(X_CSRF_TOKEN);
		return repository;
	}
	
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler()
	{
		LogoutSuccessHandler logoutSuccessHandler = new LogoutSuccessHandler();
		logoutSuccessHandler.setDefaultTargetUrl( "/userlogout" );
		logoutSuccessHandler.setRedirectStrategy( new HttpsRedirectStratergy() );
		return logoutSuccessHandler;

	}

}   