package com.ust.evs.service;

import java.util.ArrayList;

import com.ust.evs.bean.CandidateBean;
import com.ust.evs.bean.ElectionBean;
import com.ust.evs.bean.ResultBean;

public interface Voter {
	public String CastVote(String userId, String electionId, String candiadteId);
	public String ViewGeneratedVoterid(String userId, String constituency);
	public String RequestVoterid(String userId);
	public ArrayList<CandidateBean> viewCandidatesByElectionName(String electionName, String constituency);
	public ArrayList<ResultBean> viewListOfElectionsResults();
	public ArrayList<ElectionBean> viewListOfElections(); 
	
}
