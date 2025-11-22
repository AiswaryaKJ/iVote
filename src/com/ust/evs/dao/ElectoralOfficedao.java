package com.ust.evs.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ust.evs.bean.ApplicationBean;
import com.ust.evs.service.ElectoralOfficer;
import com.ust.evs.util.DBUtil;

public class ElectoralOfficedao implements ElectoralOfficer {
	 public static String loggedInEOId;
	    public static String loggedInEOConstituency;
	@Override
    public String generateVoterId(String userId, String status) {
        String voterId = null;
        try {
            if (status.equalsIgnoreCase("REJECTED")) {
                // EO rejects → PassedStatus=3, ApprovedStatus=0, VoterId=NULL
                PreparedStatement psReject = DBUtil.con.prepareStatement(
                    "UPDATE evs_tbl_application SET PassedStatus = 3, ApprovedStatus = 0, VoterId = NULL WHERE UserId = ?"
                );
                psReject.setString(1, userId);
                int updated = psReject.executeUpdate();
                return updated > 0 ? "Request Rejected by EO" : "FAIL";
            } else if (status.equalsIgnoreCase("APPROVED")) {
                // 🔹 Step 1: Fetch user first name & constituency
                PreparedStatement psFetch = DBUtil.con.prepareStatement(
                    "SELECT u.FirstName, a.Constituency FROM evs_tbl_user_profile u "
                  + "JOIN evs_tbl_application a ON u.UserId = a.UserId WHERE u.UserId = ?"
                );
                psFetch.setString(1, userId);
                ResultSet rsFetch = psFetch.executeQuery();

                if (rsFetch.next()) {
                    String firstName = rsFetch.getString("FirstName");
                    String constituency = rsFetch.getString("Constituency");

                    // 🔹 Step 2: Generate sequence number from evs_seq_voterid
                    int nextSeq = 0;
                    try (PreparedStatement psSeq = DBUtil.con.prepareStatement(
                            "INSERT INTO evs_seq_voterid VALUES (NULL)", Statement.RETURN_GENERATED_KEYS)) {
                        psSeq.executeUpdate();
                        try (ResultSet rsSeq = psSeq.getGeneratedKeys()) {
                            if (rsSeq.next()) {
                                nextSeq = rsSeq.getInt(1);
                            }
                        }
                    }

                    // 🔹 Step 3: Build voterId safely
                    String voterIdPrefix = (firstName.length() >= 2 ? firstName.substring(0, 2) : firstName).toUpperCase()
                                         + (constituency.length() >= 2 ? constituency.substring(0, 2) : constituency).toUpperCase();
                    voterId = voterIdPrefix + String.format("%04d", nextSeq);

                    // 🔹 Step 4: Update application table
                    PreparedStatement psUpdate = DBUtil.con.prepareStatement(
                        "UPDATE evs_tbl_application SET VoterId = ?, ApprovedStatus = 1, PassedStatus = 3 WHERE UserId = ?"
                    );
                    psUpdate.setString(1, voterId);
                    psUpdate.setString(2, userId);

                    int updated = psUpdate.executeUpdate();
                    return updated > 0 ? voterId : "FAIL";
                } else {
                    return "User not found";
                }
            }
            return "Invalid Status";
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            return "FAIL";
        }
        }
	 @Override
	    public ArrayList<ApplicationBean> viewAllVoterIdApplications() {
	        ArrayList<ApplicationBean> apps = new ArrayList<>();
	        try {
	            System.out.println("DEBUG: loggedInEOConstituency = " + loggedInEOConstituency);

	            PreparedStatement ps = DBUtil.con.prepareStatement(
	                "SELECT * FROM evs_tbl_application WHERE Constituency = ?"
	            );
	            ps.setString(1, loggedInEOConstituency);

	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                ApplicationBean app = new ApplicationBean();
	                app.setUserID(rs.getString("Userid"));
	                app.setConstituency(rs.getString("Constituency"));
	                app.setPassedStatus(rs.getInt("Passedstatus"));
	                app.setApprovedStatus(rs.getInt("Approvedstatus"));
	                app.setVoterID(rs.getString("VoterId"));
	                apps.add(app);
	            }
	        } catch (SQLException e) {
	            System.out.println("SQL Error: " + e.getMessage());
	        }
	        return apps;
	    }
	}

//	@Override
//	public ArrayList<ApplicationBean> viewAllVoterIdApplications() {
//	    ArrayList<ApplicationBean> apps = new ArrayList<>();
//	    try {
//	        PreparedStatement ps = DBUtil.con.prepareStatement("SELECT * FROM evs_tbl_application");
//	        ResultSet rs = ps.executeQuery();
//
//	        while (rs.next()) {
//	            ApplicationBean app = new ApplicationBean();
//	            app.setUserID(rs.getString("UserId"));
//	            app.setConstituency(rs.getString("Constituency"));
//	            app.setPassedStatus(rs.getInt("PassedStatus"));
//	            app.setApprovedStatus(rs.getInt("ApprovedStatus"));
//	            app.setVoterID(rs.getString("VoterId"));
//	            apps.add(app);
//	        }
//	    } catch (SQLException e) {
//	        System.out.println("SQL Error: " + e.getMessage());
//	    }
//	    return apps;
//	}


