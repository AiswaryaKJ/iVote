package com.ust.evs.dao;

import com.ust.evs.dao.Admdao;
import com.ust.evs.util.DBUtil;

import java.sql.*;
import com.ust.evs.bean.CredentialBean;

public class LoginDao {
    private PreparedStatement ps;
    private ResultSet rs;
public CredentialBean authenticate(String userID, String password) {
    CredentialBean bean = null;
    try (Connection conn = DBUtil.getCon();
         PreparedStatement ps = conn.prepareStatement(
             "SELECT * FROM evs_tbl_user_credentials WHERE Userid = ? AND Password = ?")) {

        ps.setString(1, userID.trim());
        ps.setString(2, password.trim());

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                bean = new CredentialBean();
                bean.setUserID(rs.getString("Userid"));       // matches DB
                bean.setPassword(rs.getString("Password"));   // matches DB
                bean.setUserType(rs.getString("Usertype"));   // matches DB
                bean.setLoginStatus(rs.getInt("Loginstatus"));// matches DB
            }
        }
    } catch (SQLException e) {
        System.out.println("SQL Error: " + e.getMessage());
    }
    return bean;
}
}
