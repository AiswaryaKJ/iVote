package com.ust.evs.bean;

public class ResultBean {
	
	private int serialNo;  
    
	private String electionID ; 
	       
	private String candidateID ;   
	         
	private int voteCount ;

	@Override
	public String toString() {
		return "ResultBean [serialNo=" + serialNo + ", electionID=" + electionID + ", candidateID=" + candidateID
				+ ", voteCount=" + voteCount + "]";
	}

	public ResultBean() {}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getElectionID() {
		return electionID;
	}

	public void setElectionID(String electionID) {
		this.electionID = electionID;
	}

	public String getCandidateID() {
		return candidateID;
	}

	public void setCandidateID(String candidateID) {
		this.candidateID = candidateID;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}
}
