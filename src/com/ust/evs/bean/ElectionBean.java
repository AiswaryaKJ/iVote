package com.ust.evs.bean;

import java.util.Date;

public class ElectionBean {
	 private String electionID;
     
	 private String name;   
	     
	 private Date electionDate;   
	    
	 private String district;  
	   
	     
	 private String constituency;   
	     
	 private Date countingDate;

	 public String getElectionID() {
		 return electionID;
	 }

	 public void setElectionID(String electionID) {
		 this.electionID = electionID;
	 }

	 public String getName() {
		 return name;
	 }

	 public void setName(String name) {
		 this.name = name;
	 }

	 public Date getElectionDate() {
		 return electionDate;
	 }

	 public void setElectionDate(Date electionDate) {
		 this.electionDate = electionDate;
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

	 public Date getCountingDate() {
		 return countingDate;
	 }

	 public void setCountingDate(Date countingDate) {
		 this.countingDate = countingDate;
	 }

	 @Override
	 public String toString() {
		return "ElectionBean [electionID=" + electionID + ", name=" + name + ", electionDate=" + electionDate
				+ ", district=" + district + ", constituency=" + constituency + ", countingDate=" + countingDate + "]";
	 } 
	 
}
