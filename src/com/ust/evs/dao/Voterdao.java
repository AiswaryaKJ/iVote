package com.ust.evs.dao;

import java.util.ArrayList;

import com.ust.evs.bean.CandidateBean;
import com.ust.evs.bean.ElectionBean;
import com.ust.evs.bean.ResultBean;
import com.ust.evs.service.Voter;

public class Voterdao implements Voter {

	@Override
	public String CastVote(String userId, String electionId, String candiadteId) {
		return null;
	}

	@Override
	public String ViewGeneratedVoterid(String userId, String constituency) {

		return null;
	}

	@Override
	public String RequestVoterid(String userId) {

		return null;
	}

	@Override
	public ArrayList<CandidateBean> viewCandidatesByElectionName(String electionName, String constituency) {

		return null;
	}

	@Override
	public ArrayList<ResultBean> viewListOfElectionsResults() {
		return null;
	}

	@Override
	public ArrayList<ElectionBean> viewListOfElections() {
		return null;
	}

}
