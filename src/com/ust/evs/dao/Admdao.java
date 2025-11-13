package com.ust.evs.dao;

import java.sql.*;
import java.util.*;
import com.ust.evs.bean.*;
import com.ust.evs.service.Administrator;

public class Admdao implements Administrator {
    public static Connection con = getCon();
    public static PreparedStatement ps;
    public static ResultSet rs;

    public static Connection getCon() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/evs", "root", "pass@word1");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return null;
    }
    @Override
    public String addElection(ElectionBean electionBean) {
        int i = 0;
        try {
            ps = con.prepareStatement("INSERT INTO evs_tbl_election (ElectionId, Name, ElectionDate, District, Constituency, CountingDate) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, electionBean.getElectionID());
            ps.setString(2, electionBean.getName());
            ps.setDate(3, new java.sql.Date(electionBean.getElectionDate().getTime()));
            ps.setString(4, electionBean.getDistrict());
            ps.setString(5, electionBean.getConstituency());
            ps.setDate(6, new java.sql.Date(electionBean.getCountingDate().getTime()));
            i = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return i > 0 ? "SUCCESS" : "FAIL";
    }
    @Override
    public ArrayList<ElectionBean> viewElections(){
        ArrayList<ElectionBean> elections = new ArrayList<>();

        try {
             PreparedStatement ps = con.prepareStatement("SELECT * FROM evs_tbl_election");
             ResultSet rs = ps.executeQuery(); 

            while (rs.next()) {
                ElectionBean e = new ElectionBean();
                e.setElectionID(rs.getString("ElectionId"));
                e.setName(rs.getString("Name"));
                e.setElectionDate(rs.getDate("ElectionDate"));
                e.setDistrict(rs.getString("District"));
                e.setConstituency(rs.getString("Constituency"));
                e.setCountingDate(rs.getDate("CountingDate"));
                elections.add(e);
            }
        }
         catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        return elections;
    }


	@Override
	public ArrayList<ElectionBean> viewAllUpcomingElections() {
		ArrayList<ElectionBean> elections = new ArrayList<>();

        try {
             PreparedStatement ps = con.prepareStatement("SELECT * FROM evs_tbl_election WHERE ElectionDate >= CURRENT_DATE");
             ResultSet rs = ps.executeQuery(); 

            while (rs.next()) {
                ElectionBean e = new ElectionBean();
                e.setElectionID(rs.getString("ElectionId"));
                e.setName(rs.getString("Name"));
                e.setElectionDate(rs.getDate("ElectionDate"));
                e.setDistrict(rs.getString("District"));
                e.setConstituency(rs.getString("Constituency"));
                e.setCountingDate(rs.getDate("CountingDate"));
                elections.add(e);
            }
        }
         catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        return elections;
    }
		
	@Override
	public String addParty(PartyBean partyBean) {
	    int i = 0;
	    try {
	        ps = con.prepareStatement("INSERT INTO evs_tbl_party (PartyId, Name, Leader, Symbol) VALUES (?, ?, ?, ?)");
	        ps.setString(1, partyBean.getPartyID());
	        ps.setString(2, partyBean.getName());
	        ps.setString(3, partyBean.getLeader());
	        ps.setString(4, partyBean.getSymbol());
	        i = ps.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	    }
	    return i > 0 ? "SUCCESS" : "FAIL";
	}

	@Override
	public ArrayList<PartyBean> viewAllParty() {
		    ArrayList<PartyBean> parties = new ArrayList<>();

		    try {
		         PreparedStatement ps = con.prepareStatement("SELECT * FROM evs_tbl_party");
		         ResultSet rs = ps.executeQuery();

		        while (rs.next()) {
		            PartyBean party = new PartyBean();
		            party.setPartyID(rs.getString("PartyID"));
		            party.setName(rs.getString("Name"));
		            party.setLeader(rs.getString("Leader"));
		            party.setSymbol(rs.getString("Symbol"));
		            parties.add(party);
		        }

		    } catch (SQLException e) {
		        System.out.println("SQL Error: " + e.getMessage());
		    }

		    return parties;
		}
	@Override
	public String addCandidate(CandidateBean candidateBean) {
	    int i = 0;

	    try {
	       
	        PreparedStatement ps1 = con.prepareStatement("SELECT COUNT(*) FROM evs_tbl_election WHERE ElectionId = ?");
	        ps1.setString(1, candidateBean.getElectionID());
	        ResultSet rs1 = ps1.executeQuery();
	        rs1.next();
	        if (rs1.getInt(1) == 0) {
	            return "FAIL: Election ID not found";
	        }

	        PreparedStatement ps2 = con.prepareStatement("SELECT COUNT(*) FROM evs_tbl_party WHERE PartyId = ?");
	        ps2.setString(1, candidateBean.getPartyID());
	        ResultSet rs2 = ps2.executeQuery();
	        rs2.next();
	        if (rs2.getInt(1) == 0) {
	            return "FAIL: Party ID not found";
	        }

	        PreparedStatement ps = con.prepareStatement(
	            "INSERT INTO evs_tbl_candidate (CandidateId, Name, ElectionId, PartyId, District, Constituency, DateOfBirth, MobileNo, Address, EmailId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	        );
	        ps.setString(1, candidateBean.getCandidateID());
	        ps.setString(2, candidateBean.getName());
	        ps.setString(3, candidateBean.getElectionID());
	        ps.setString(4, candidateBean.getPartyID());
	        ps.setString(5, candidateBean.getDistrict());
	        ps.setString(6, candidateBean.getConstituency());
	        ps.setDate(7, new java.sql.Date(candidateBean.getDateOfBirth().getTime()));
	        ps.setString(8, candidateBean.getMobileNo());
	        ps.setString(9, candidateBean.getAddress());
	        ps.setString(10, candidateBean.getEmailID());

	        i = ps.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        return "FAIL: SQL Error";
	    }

	    return i > 0 ? "SUCCESS" : "FAIL";
	}

	@Override
	public ArrayList<CandidateBean> viewCandidateDetailsByElectionName(String electionName) {
	    ArrayList<CandidateBean> candidates = new ArrayList<>();

	    try {
	         PreparedStatement ps = con.prepareStatement(
	             "SELECT c.* FROM evs_tbl_candidate c " +
	             "JOIN evs_tbl_election e ON c.ElectionID = e.ElectionID " +
	             "WHERE e.Name = ?");

	        ps.setString(1, electionName);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            CandidateBean c = new CandidateBean();
	            c.setCandidateID(rs.getString("CandidateID"));
	            c.setName(rs.getString("Name"));
	            c.setElectionID(rs.getString("ElectionID"));
	            c.setPartyID(rs.getString("PartyID"));
	            c.setDistrict(rs.getString("District"));
	            c.setConstituency(rs.getString("Constituency"));
	            c.setDateOfBirth(rs.getDate("DateOfBirth"));
	            c.setMobileNo(rs.getString("MobileNo"));
	            c.setAddress(rs.getString("Address"));
	            c.setEmailID(rs.getString("EmailID"));
	            candidates.add(c);
	        }

	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	    }

	    return candidates;
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