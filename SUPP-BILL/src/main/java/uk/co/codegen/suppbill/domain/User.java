package uk.co.codegen.suppbill.domain;

import java.util.List;

public class User {
	
	private Integer userId;
	private String firstName;
	private String lastName;
	private List claimList;
	
	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the claimList
	 */
	public List getClaimList() {
		return claimList;
	}
	/**
	 * @param claimList the claimList to set
	 */
	public void setClaimList(List claimList) {
		this.claimList = claimList;
	}	
	
	

}
