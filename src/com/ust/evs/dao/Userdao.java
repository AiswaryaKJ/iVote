package com.ust.evs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.ust.evs.bean.ProfileBean;
import com.ust.evs.util.DBUtil;

public class Userdao {

    public String register(ProfileBean profile) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getCon();

            // 🔹 Step 1: Get next sequence value from evs_seq_userid
            int nextSeq = 0;
            try (PreparedStatement psSeq = conn.prepareStatement("INSERT INTO evs_seq_userid VALUES (NULL)")) {
                psSeq.executeUpdate();
            }
            try (PreparedStatement psSeqVal = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                 ResultSet rsSeq = psSeqVal.executeQuery()) {
                if (rsSeq.next()) {
                    nextSeq = rsSeq.getInt(1);
                }
            }

            // 🔹 Step 2: Build UserId with prefix + sequence
            String prefix = profile.getFirstName().substring(0, 2).toUpperCase();
            String userId = prefix + nextSeq;

            // 🔹 Step 3: Insert into User_Profile
            ps = conn.prepareStatement(
                "INSERT INTO evs_tbl_user_profile (Userid, Firstname, Lastname, Dateofbirth, Gender, Street, Location, City, State, Pincode, MobileNo, EmailId, Constituency) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, userId);
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setDate(4, profile.getDateOfBirth() != null ?
                new java.sql.Date(profile.getDateOfBirth().getTime()) : null);
            ps.setString(5, profile.getGender());
            ps.setString(6, profile.getStreet());
            ps.setString(7, profile.getLocation());
            ps.setString(8, profile.getCity());
            ps.setString(9, profile.getState());
            ps.setString(10, profile.getPincode());
            ps.setString(11, profile.getMobileNo());
            ps.setString(12, profile.getEmailID());
            ps.setString(13, profile.getConstituency());
            ps.executeUpdate();
            ps.close();

            // 🔹 Step 4: Insert into User_Credentials
            ps = conn.prepareStatement(
                "INSERT INTO evs_tbl_user_credentials (Userid, Password, Usertype, Loginstatus) VALUES (?, ?, ?, ?)");
            ps.setString(1, userId);
            ps.setString(2, profile.getPassword());
            ps.setString(3, "V");   // default user type = Voter
            ps.setInt(4, 0);        // login status = 0 (inactive)
            ps.executeUpdate();

            return userId; // return the generated sequential ID

        } catch (SQLException e) {
            e.printStackTrace();
            return "FAIL";
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (ps != null) ps.close(); } catch (Exception ignored) {}
            try { if (conn != null) conn.close(); } catch (Exception ignored) {}
        }
    }
}