package com.ust.evs.dao;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.ust.evs.bean.ApplicationBean;
import com.ust.evs.bean.CandidateBean;
import com.ust.evs.bean.ElectionBean;
import com.ust.evs.bean.PartyBean;
import com.ust.evs.service.*;
public class Admdao implements Administrator{
	ArrayList <ElectionBean> electionArray = new ArrayList<ElectionBean>();
	ArrayList<PartyBean> partyArray=new ArrayList<PartyBean>();
	ArrayList <CandidateBean> candidateArray = new ArrayList<CandidateBean>();
		@Override
		public ArrayList<ElectionBean> addElection(ElectionBean electionBean) {
			if (electionBean == null) {
		        return null;
		    }
		    electionArray.add(electionBean);
		   // ArrayList<ElectionBean> allElections = ((Admdao) admin).getAllElections()
		    return electionArray;
		}

		@Override
		public ArrayList<ElectionBean> viewAllUpcomingElections() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public ArrayList<ElectionBean> viewElections() {
			// TODO Auto-generated method stub
				return electionArray;

		}
		@Override
		public String addParty(PartyBean partyBean) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public ArrayList<PartyBean> viewAllParty() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String addCandidate(CandidateBean candidateBean) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public ArrayList<CandidateBean> viewCandidateDetailsByElectionName(String electionName) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public ArrayList<ApplicationBean> viewAllAdminPendingApplications() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean forwardVoterIDRequest(String userId) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public ArrayList<CandidateBean> viewCandidateDetailsByParty(String partyId) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Map<?, ?> approveElectionResults(String electionId) {
			// TODO Auto-generated method stub
			return null;
		}
		

		
	}
	
