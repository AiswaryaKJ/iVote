package com.ust.evs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ust.evs.bean.CandidateBean;
import com.ust.evs.bean.ElectionBean;
import com.ust.evs.bean.ResultBean;
import com.ust.evs.service.Voter;
import com.ust.evs.util.DBUtil;

public class Voterdao implements Voter {

	@Override
	public String CastVote(String voterId, String electionId, String candidateId) {
	    Connection con = DBUtil.con;

	    if (voterId == null || voterId.trim().isEmpty()
	        || electionId == null || electionId.trim().isEmpty()
	        || candidateId == null || candidateId.trim().isEmpty()) {
	        return "INVALID INPUT";
	    }

	    try {
	        con.setAutoCommit(false);

	        // 1) Insert into voter details (Serialno auto_increment)
	        String sqlInsertVD =
	            "INSERT INTO evs_tbl_voter_details (Candidateid, Electionid, Voterid) VALUES (?, ?, ?)";
	        try (PreparedStatement ps = con.prepareStatement(sqlInsertVD)) {
	            ps.setString(1, candidateId);
	            ps.setString(2, electionId);
	            ps.setString(3, voterId);
	            if (ps.executeUpdate() != 1) {
	                con.rollback();
	                return "FAIL";
	            }
	        }

	        // 2) Update result table (increment vote count)
	        String sqlUpdateRes =
	            "UPDATE evs_tbl_result SET Votecount = Votecount + 1 WHERE Electionid = ? AND Candidateid = ?";
	        try (PreparedStatement ps = con.prepareStatement(sqlUpdateRes)) {
	            ps.setString(1, electionId);
	            ps.setString(2, candidateId);
	            int updated = ps.executeUpdate();

	            // If no row updated, insert new result row (Serialno auto_increment)
	            if (updated == 0) {
	                String sqlInsertRes =
	                    "INSERT INTO evs_tbl_result (Electionid, Candidateid, Votecount) VALUES (?, ?, 1)";
	                try (PreparedStatement ps2 = con.prepareStatement(sqlInsertRes)) {
	                    ps2.setString(1, electionId);
	                    ps2.setString(2, candidateId);
	                    if (ps2.executeUpdate() != 1) {
	                        con.rollback();
	                        return "FAIL";
	                    }
	                }
	            }
	        }

	        con.commit();
	        return "SUCCESS";

	    } catch (SQLException e) {
	        try { con.rollback(); } catch (SQLException ex) { /* ignore */ }
	        e.printStackTrace();
	        return "FAIL";
	    } finally {
	        try { con.setAutoCommit(true); } catch (SQLException e) { /* ignore */ }
	    }
	}

	@Override
	public String ViewGeneratedVoterid(String userId, String constituency) {
	    try {
	        PreparedStatement ps = DBUtil.con.prepareStatement(
	            "SELECT VoterId FROM evs_tbl_application " +
	            "WHERE UserId = ? AND Constituency = ? AND ApprovedStatus = 1"
	        );
	        ps.setString(1, userId);
	        ps.setString(2, constituency);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            String voterId = rs.getString("VoterId");
	            if (voterId != null && !voterId.trim().isEmpty()) {
	                return voterId; // Voter ID has been generated
	            }
	        }
	        return "NOT GENERATED"; // No voter ID yet
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        return "FAIL"; // Error occurred
	    }
	}

	@Override
	public String RequestVoterid(String userId, String constituency) {
	    try {
	        // 🔹 Step 1: Fetch constituency from user profile
	        PreparedStatement psProfile = DBUtil.con.prepareStatement(
	            "SELECT Constituency FROM evs_tbl_user_profile WHERE UserId = ?"
	        );
	        psProfile.setString(1, userId);
	        ResultSet rs = psProfile.executeQuery();

	        if (rs.next()) {
	            String profileConstituency = rs.getString("Constituency");

	            // 🔹 Step 2: Compare with requested constituency
	            if (!profileConstituency.equalsIgnoreCase(constituency)) {
	                return "Constituency mismatch! Application rejected.";
	            }

	            // 🔹 Step 3: Insert into application table if matched
	            PreparedStatement ps = DBUtil.con.prepareStatement(
	                "INSERT INTO evs_tbl_application (Userid, Constituency, Passedstatus) VALUES (?, ?, 1)"
	            );
	            ps.setString(1, userId);
	            ps.setString(2, constituency);

	            int i = ps.executeUpdate();
	            return i > 0 ? "Application Submitted" : "FAIL";
	        } else {
	            return "User not found in profile table.";
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return "FAIL";
	    }
	}


	@Override
	public ArrayList<CandidateBean> viewCandidatesByElectionName(String electionName, String constituency) {
	    ArrayList<CandidateBean> candidates = new ArrayList<>();

	    try {
	        PreparedStatement ps = DBUtil.con.prepareStatement(
	            "SELECT c.* FROM evs_tbl_candidate c " +
	            "JOIN evs_tbl_election e ON c.ElectionID = e.ElectionID " +
	            "WHERE e.Name = ? AND c.Constituency = ?"
	        );

	        ps.setString(1, electionName);
	        ps.setString(2, constituency);

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
	public ArrayList<ResultBean> viewListOfElectionsResults() {
	    Connection con = DBUtil.con;
	    ArrayList<ResultBean> results = new ArrayList<>();

	    try {
	        String sql = "SELECT Electionid, Candidateid, Votecount FROM evs_tbl_result ORDER BY Electionid";
	        try (PreparedStatement ps = con.prepareStatement(sql);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                ResultBean bean = new ResultBean();
	                bean.setElectionID(rs.getString("Electionid"));
	                bean.setCandidateID(rs.getString("Candidateid"));
	                bean.setVoteCount(rs.getInt("Votecount"));
	                results.add(bean);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return results;
	}


	@Override
	public ArrayList<ElectionBean> viewListOfElections() {
	    ArrayList<ElectionBean> elections = new ArrayList<>();

	    try {
	        PreparedStatement ps = DBUtil.con.prepareStatement("SELECT * FROM evs_tbl_election");
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
}
