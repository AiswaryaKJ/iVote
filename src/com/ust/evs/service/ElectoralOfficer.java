package com.ust.evs.service;

import java.util.ArrayList;

import com.ust.evs.bean.ApplicationBean;

public interface ElectoralOfficer {
	public String generateVoterId(String userId, String status); 
	public ArrayList<ApplicationBean> viewAllVoterIdApplications() ;
}
