package com.ust.evs.dao;
import java.util.ArrayList;
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

	
public static void main(String[] args) {
	//Admdao ad=new Admdao();
	//ElectionBean electionObj  = new ElectionBean("ElectionID", "JP","12-10-2023","Tvm","Tvm cons","13-12-2025");
	//ad.addElection(electionObj);
	//ad.viewAllUpcomingElections();
	//ad.viewElections();
	//PartyBean partyobj=new PartyBean ("123","BJP","modi","lotus");
	//ad.addParty(partyobj);

}


@Override
public String addElection(ElectionBean electionBean) {
	// TODO Auto-generated method stub
	return null;
}


@Override
public ArrayList<ElectionBean> viewAllUpcomingElections() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public ArrayList<ElectionBean> viewElections() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public String addParty(PartyBean partyBean) {
	// TODO Auto-generated method stub
	return null;
}


@Override
public <PartyBean> void viewAllParty() {
	// TODO Auto-generated method stub
	
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
