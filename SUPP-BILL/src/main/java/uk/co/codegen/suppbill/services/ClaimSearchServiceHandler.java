package uk.co.codegen.suppbill.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import uk.co.codegen.suppbill.criteria.ClaimSearchCriteria;
import uk.co.codegen.suppbill.domain.Claim;
import uk.co.codegen.suppbill.domain.User;

public class ClaimSearchServiceHandler implements IClaimSearchService {

	@Override
	public List<User> searchClaim(ClaimSearchCriteria claimSearchCriteria) {
		
		List<User> userResults = new ArrayList<User>();
		User  user = new User();
		user.setUserId(1);
		user.setFirstName("sampath");
		user.setLastName("test");
		
		Claim claim = new  Claim();
		claim.setClaimAmount(45000d);
		
		Claim claim3 = new  Claim();
		claim3.setClaimAmount(5000d);
		
		List<Claim> claimList = new ArrayList<>();
		claimList.add(claim);
		claimList.add(claim3);
		
		user.setClaimList(claimList);
		
		
		User  user2 = new User();
		user2.setUserId(1);
		user2.setFirstName("nuwan");
		user2.setLastName("test");
		
		Claim claim2 = new  Claim();
		claim2.setClaimAmount(6000d);
		
		List<Claim> claimList2 = new ArrayList<>();
		claimList2.add(claim2);
		
		user2.setClaimList(claimList2);
		
		userResults.add(user);
		userResults.add(user2);
		
		
		userResults = userResults.stream().filter(p -> p.getFirstName().startsWith(claimSearchCriteria.getUser())).collect(Collectors.toList());
		return userResults;
		
	}

}
