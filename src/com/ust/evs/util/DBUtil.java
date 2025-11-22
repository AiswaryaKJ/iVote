package com.ust.evs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
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
}
