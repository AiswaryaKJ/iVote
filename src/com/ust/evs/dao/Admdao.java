package com.ust.evs.dao;

import java.sql.*;
import java.util.*;
import com.ust.evs.bean.*;
import com.ust.evs.service.Administrator;
import com.ust.evs.util.DBUtil;
public class Admdao implements Administrator {
	PreparedStatement ps;
    ResultSet rs;
    @Override
    public String addElection(ElectionBean electionBean) {
        int i = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = DBUtil.getCon();

            // 🔹 Step 1: Generate ElectionId from sequence table
            int nextSeq = 0;
            try (PreparedStatement psSeq = conn.prepareStatement("INSERT INTO evs_seq_electionId VALUES (NULL)")) {
                psSeq.executeUpdate();
            }
            try (PreparedStatement psSeqVal = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                 ResultSet rsSeq = psSeqVal.executeQuery()) {
                if (rsSeq.next()) {
                    nextSeq = rsSeq.getInt(1);
                }
            }

            // 🔹 Step 2: Build ElectionId with prefix + sequence
            String electionId = "EL" + nextSeq;   // Example: EL1001, EL1002...

            // 🔹 Step 3: Insert into evs_tbl_election
            ps = conn.prepareStatement(
                "INSERT INTO evs_tbl_election (ElectionId, Name, ElectionDate, District, Constituency, CountingDate) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, electionId);
            ps.setString(2, electionBean.getName());
            ps.setDate(3, new java.sql.Date(electionBean.getElectionDate().getTime()));
            ps.setString(4, electionBean.getDistrict());
            ps.setString(5, electionBean.getConstituency());
            ps.setDate(6, new java.sql.Date(electionBean.getCountingDate().getTime()));

            i = ps.executeUpdate();

            return i > 0 ? electionId : "FAIL";  // return generated ElectionId if success

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return "FAIL";
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
        }
    }

    @Override
    public ArrayList<ElectionBean> viewElections(){
        ArrayList<ElectionBean> elections = new ArrayList<>();

        try {
             PreparedStatement ps = DBUtil.con.prepareStatement("SELECT * FROM evs_tbl_election");
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
        	PreparedStatement ps = DBUtil.con.prepareStatement(
        		    "SELECT * FROM evs_tbl_election WHERE Electiondate > CURDATE()"
        		);
        		ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                ElectionBean e = new ElectionBean();
                e.setElectionID(rs.getString("ElectionId"));
                e.setName(rs.getString("Name"));
                e.setElectionDate(rs.getDate("Electiondate"));
                e.setDistrict(rs.getString("District"));
                e.setConstituency(rs.getString("Constituency"));
                e.setCountingDate(rs.getDate("Countingdate"));
                elections.add(e);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }

        return elections;
    }

		
	@Override
	public String addParty(PartyBean partyBean) {
	    int i = 0;
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        Connection conn = DBUtil.getCon();

	        // 🔹 Step 1: Generate PartyId from sequence table
	        int nextSeq = 0;
	        try (PreparedStatement psSeq = conn.prepareStatement("INSERT INTO evs_seq_partyId VALUES (NULL)")) {
	            psSeq.executeUpdate();
	        }
	        try (PreparedStatement psSeqVal = conn.prepareStatement("SELECT LAST_INSERT_ID()");
	             ResultSet rsSeq = psSeqVal.executeQuery()) {
	            if (rsSeq.next()) {
	                nextSeq = rsSeq.getInt(1);
	            }
	        }

	        // 🔹 Step 2: Build PartyId with prefix + sequence
	        String partyId = "PA" + nextSeq;   // Example: PA1001, PA1002...

	        // 🔹 Step 3: Insert into evs_tbl_party
	        ps = conn.prepareStatement(
	            "INSERT INTO evs_tbl_party (PartyId, Name, Leader, Symbol) VALUES (?, ?, ?, ?)");
	        ps.setString(1, partyId);
	        ps.setString(2, partyBean.getName());
	        ps.setString(3, partyBean.getLeader());
	        ps.setString(4, partyBean.getSymbol());

	        i = ps.executeUpdate();

	        return i > 0 ? partyId : "FAIL";  // return generated PartyId if success

	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        return "FAIL";
	    } finally {
	        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
	        try { if (ps != null) ps.close(); } catch (Exception ignored) {}
	    }
	}


	@Override
	public ArrayList<PartyBean> viewAllParty() {
		    ArrayList<PartyBean> parties = new ArrayList<>();

		    try {
		         PreparedStatement ps = DBUtil.con.prepareStatement("SELECT * FROM evs_tbl_party");
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
	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    try {
	        Connection conn = DBUtil.getCon();

	        // 🔹 Step 1: Validate ElectionId
	        PreparedStatement ps1 = conn.prepareStatement("SELECT COUNT(*) FROM evs_tbl_election WHERE ElectionId = ?");
	        ps1.setString(1, candidateBean.getElectionID());
	        ResultSet rs1 = ps1.executeQuery();
	        rs1.next();
	        if (rs1.getInt(1) == 0) {
	            return "FAIL: Election ID not found";
	        }

	        // 🔹 Step 2: Validate PartyId
	        PreparedStatement ps2 = conn.prepareStatement("SELECT COUNT(*) FROM evs_tbl_party WHERE PartyId = ?");
	        ps2.setString(1, candidateBean.getPartyID());
	        ResultSet rs2 = ps2.executeQuery();
	        rs2.next();
	        if (rs2.getInt(1) == 0) {
	            return "FAIL: Party ID not found";
	        }

	        // 🔹 Step 3: Generate CandidateId from sequence table
	        int nextSeq = 0;
	        try (PreparedStatement psSeq = conn.prepareStatement("INSERT INTO evs_seq_candidateId VALUES (NULL)")) {
	            psSeq.executeUpdate();
	        }
	        try (PreparedStatement psSeqVal = conn.prepareStatement("SELECT LAST_INSERT_ID()");
	             ResultSet rsSeq = psSeqVal.executeQuery()) {
	            if (rsSeq.next()) {
	                nextSeq = rsSeq.getInt(1);
	            }
	        }
	        String candidateId = "CA" + nextSeq;   // Example: CA1001, CA1002...

	        // 🔹 Step 4: Insert into evs_tbl_candidate
	        ps = conn.prepareStatement(
	            "INSERT INTO evs_tbl_candidate (CandidateId, Name, ElectionId, PartyId, District, Constituency, DateOfBirth, MobileNo, Address, EmailId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
	        );
	        ps.setString(1, candidateId);
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

	        return i > 0 ? candidateId : "FAIL";

	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        return "FAIL: SQL Error";
	    } finally {
	        try { if (rs != null) rs.close(); } catch (Exception ignored) {}
	        try { if (ps != null) ps.close(); } catch (Exception ignored) {}
	    }
	}

	@Override
	public ArrayList<CandidateBean> viewCandidateDetailsByElectionName(String electionName) {
	    ArrayList<CandidateBean> candidates = new ArrayList<>();

	    try {
	         PreparedStatement ps = DBUtil.con.prepareStatement(
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
	    ArrayList<ApplicationBean> apps = new ArrayList<>();
	    try {
	        PreparedStatement ps = DBUtil.con.prepareStatement(
	            "SELECT * FROM evs_tbl_application WHERE Passedstatus = 1 AND Approvedstatus IS NULL"
	        );
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            ApplicationBean app = new ApplicationBean();
	            app.setUserID(rs.getString("Userid")); // match exact column name
	            app.setConstituency(rs.getString("Constituency"));
	            app.setPassedStatus(rs.getInt("Passedstatus"));
	            app.setApprovedStatus(rs.getInt("Approvedstatus"));
	            //app.setVoterID(rs.getString("VoterId"));
	            apps.add(app);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return apps;
	}


	@Override
	public boolean forwardVoterIDRequest(String userId) {
	    try {
	        PreparedStatement ps = DBUtil.con.prepareStatement(
	            "UPDATE evs_tbl_application SET PassedStatus = 2 WHERE UserId = ?"
	        );
	        ps.setString(1, userId);
	        int i = ps.executeUpdate();

	        // If at least one row was updated, return true
	        return i > 0;
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        return false; // return false on error
	    }
	}

	@Override
	public ArrayList<CandidateBean> viewCandidateDetailsByParty(String partyId) {
		 ArrayList<CandidateBean> candidates = new ArrayList<>();

		    try {
		         PreparedStatement ps = DBUtil.con.prepareStatement(
		             "SELECT c.* FROM evs_tbl_candidate c " +
		             "JOIN evs_tbl_election e ON c.ElectionID = e.ElectionID " +
		             "WHERE e.Name = ?");

		        ps.setString(1, partyId);
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
	public Map<String, Object> approveElectionResults(String electionId) {
	    Connection con = DBUtil.con;
	    Map<String, Object> resultMap = new HashMap<>();

	    if (electionId == null || electionId.trim().isEmpty()) {
	        resultMap.put("status", "INVALID INPUT");
	        return resultMap;
	    }

	    try {
	        String sql = "SELECT Candidateid, Votecount FROM evs_tbl_result WHERE Electionid = ?";
	        List<String> winners = new ArrayList<>();
	        int maxVotes = -1;

	        try (PreparedStatement ps = con.prepareStatement(sql)) {
	            ps.setString(1, electionId);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    String candidateId = rs.getString("Candidateid");
	                    int voteCount = rs.getInt("Votecount");

	                    // Store each candidate's votes
	                    resultMap.put(candidateId, voteCount);

	                    // Track max votes
	                    if (voteCount > maxVotes) {
	                        maxVotes = voteCount;
	                        winners.clear();
	                        winners.add(candidateId);
	                    } else if (voteCount == maxVotes) {
	                        // Handle tie
	                        winners.add(candidateId);
	                    }
	                }
	            }
	        }

	        if (maxVotes == -1) {
	            resultMap.put("status", "NO RESULTS FOUND");
	        } else {
	            resultMap.put("status", "APPROVED");
	            resultMap.put("winnerVotes", maxVotes);
	            resultMap.put("winner", winners);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        resultMap.put("status", "FAIL");
	    }

	    return resultMap;
	}

}