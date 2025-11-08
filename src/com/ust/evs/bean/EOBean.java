package com.ust.evs.bean;

public class EOBean {
	private String electoralOfficerID ;   
    
	private String constituency;

	public EOBean(String electoralOfficerID, String constituency) {
		super();
		this.electoralOfficerID = electoralOfficerID;
		this.constituency = constituency;
	}

	public String getElectoralOfficerID() {
		return electoralOfficerID;
	}

	public void setElectoralOfficerID(String electoralOfficerID) {
		this.electoralOfficerID = electoralOfficerID;
	}

	public String getConstituency() {
		return constituency;
	}

	public void setConstituency(String constituency) {
		this.constituency = constituency;
	}
}
