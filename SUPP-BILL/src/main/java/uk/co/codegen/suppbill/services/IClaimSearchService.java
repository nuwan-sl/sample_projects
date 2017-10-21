package uk.co.codegen.suppbill.services;

import java.util.List;

import uk.co.codegen.suppbill.criteria.ClaimSearchCriteria;
import uk.co.codegen.suppbill.domain.User;

public interface IClaimSearchService {
	
	List<User> searchClaim( ClaimSearchCriteria claimSearchCriteria );
}
