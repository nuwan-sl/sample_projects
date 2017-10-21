package uk.co.codegen.suppbill.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uk.co.codegen.suppbill.criteria.ClaimSearchCriteria;
import uk.co.codegen.suppbill.domain.Claim;
import uk.co.codegen.suppbill.domain.User;
import uk.co.codegen.suppbill.services.IClaimSearchService;

@Controller
public class ClaimController extends BaseController {
	
	@Autowired
	private IClaimSearchService claimSearchService;
	
	@RequestMapping(value = { "/getSearchClaim"}, method = RequestMethod.GET)
	public String getSearchClaim(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return "search-claim";
		
	}

	@RequestMapping(value = { "/searchClaim"}, method = RequestMethod.GET)
	public String searchClaim( @Valid @ModelAttribute(value = "claimSC") ClaimSearchCriteria claimSearchCriteria, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		List<User> userResults = new ArrayList<User>();
		userResults = this.claimSearchService.searchClaim(claimSearchCriteria);
		
		model.addAttribute( "userResults", userResults );
		return "search-claim";
		
	}
	

	@RequestMapping(value = "/openClaim", method = { RequestMethod.GET })
	public String applyPromotionCode( @Valid @ModelAttribute("user") String user, Model model, Locale locale, HttpServletResponse response, HttpServletRequest request )
	{
		
		ClaimSearchCriteria claimSearchCriteria = new ClaimSearchCriteria();
		claimSearchCriteria.setUser(user);
		
		List<User> userResults = this.claimSearchService.searchClaim(claimSearchCriteria);
		if(!StringUtils.isEmpty(userResults)){
			
			List<Claim> claimList = userResults.get(0).getClaimList();
			model.addAttribute( "claimList", claimList );
		}
		
		return "claim-popup";
		
	}
	
}
