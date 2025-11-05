package com.ust.evs.bean;

import java.util.Date;

public class CandidateBean {
	private String candidateID ;  
     
	private String name; 
	     
	private String electionID;
	     
	private String partyID;  
	     
	private String district;  
	    
	private String constituency;  
	     
	private Date dateOfBirth  ;
	     
	private String mobileNo ;  
	     
	private String address ;  

	private String emailID ; 
	     
	public String getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getElectionID() {
		return electionID;
	}

	public void setElectionID(String electionID) {
		this.electionID = electionID;
	}

	public String getPartyID() {
		return partyID;
	}

	public void setPartyID(String partyID) {
		this.partyID = partyID;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getConstituency() {
		return constituency;
	}

	public void setConstituency(String constituency) {
		this.constituency = constituency;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

}
