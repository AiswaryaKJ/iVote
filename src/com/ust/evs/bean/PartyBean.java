package com.ust.evs.bean;

public class PartyBean {
	private String partyID ; 
    
	 private String name ;  
	    
	 private String leader ;  
	     
	 private String symbol;

	 public PartyBean(String partyID, String name, String leader, String symbol) {
		super();
		this.partyID = partyID;
		this.name = name;
		this.leader = leader;
		this.symbol = symbol;
	}
	 public PartyBean() {
		}

	 public String getPartyID() {
		 return partyID;
	 }

	 public void setPartyID(String partyID) {
		 this.partyID = partyID;
	 }

	 public String getName() {
		 return name;
	 }

	 public void setName(String name) {
		 this.name = name;
	 }

	 public String getLeader() {
		 return leader;
	 }

	 public void setLeader(String leader) {
		 this.leader = leader;
	 }

	 public String getSymbol() {
		 return symbol;
	 }

	 public void setSymbol(String symbol) {
		 this.symbol = symbol;
	 }
}
