package com.ust.evs.dao;

import com.ust.evs.dao.Admdao;
import java.sql.*;
import com.ust.evs.bean.CredentialBean;

public class LoginDao {
    private Connection con = Admdao.getCon();
    private PreparedStatement ps;
    private ResultSet rs;

    public CredentialBean authenticate(String userID, String password) {
        CredentialBean bean = null;
        try {
            ps = con.prepareStatement("SELECT * FROM evs_tbl_user_credentials WHERE UserID = ? AND Password = ?");
            ps.setString(1, userID);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                bean = new CredentialBean();
                bean.setUserID(rs.getString("UserID"));
                bean.setPassword(rs.getString("Password"));
                bean.setUserType(rs.getString("UserType"));
                bean.setLoginStatus(rs.getInt("LoginStatus"));
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return bean;
    }
}
