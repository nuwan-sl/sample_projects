package uk.co.codegen.suppbill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import uk.co.codegen.suppbill.services.ClaimSearchServiceHandler;
import uk.co.codegen.suppbill.services.IClaimSearchService;

@Configuration
public class CoreDomainConfig {

	@Autowired
	Environment env;

	@Bean
	public IClaimSearchService configPackageSearchService()
	{
		return new ClaimSearchServiceHandler( );
	}
}
