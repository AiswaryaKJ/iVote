package com.ust.evs.service;

import java.util.ArrayList;
import java.util.Map;

import com.ust.evs.bean.ApplicationBean;
import com.ust.evs.bean.CandidateBean;
import com.ust.evs.bean.ElectionBean;
import com.ust.evs.bean.PartyBean;

public interface Administrator {
	public String addElection(ElectionBean electionBean) ;
	public ArrayList<ElectionBean> viewAllUpcomingElections() ; 
	public ArrayList<ElectionBean> viewElections();
	public String addParty(PartyBean partyBean); 
	public <PartyBean> void viewAllParty() ;
	public String addCandidate(CandidateBean candidateBean) ;
	public ArrayList<CandidateBean> viewCandidateDetailsByElectionName(String electionName) ;
	public ArrayList<ApplicationBean> viewAllAdminPendingApplications() ;
	public boolean forwardVoterIDRequest(String userId);
	public ArrayList<CandidateBean> viewCandidateDetailsByParty(String partyId);  
	public Map<?, ?> approveElectionResults(String electionId); 
	
}
