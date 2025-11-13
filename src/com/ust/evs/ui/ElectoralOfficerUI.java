package com.ust.evs.ui;

import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;

import com.ust.evs.dao.ElectoralOfficedao;

public class ElectoralOfficerUI {
	public static void showElectoralMenu() {
		while(true) {
	    	try {
	    	    String menu = "EVS EO Panel\n\n"
	    	                + "1. Generate VoterID"
	    	                + "2. View All Voter Application"
	    	                + "3. LogOut";

	    	    String input = JOptionPane.showInputDialog(null, menu);
	    	    
	    	    if (input == null) return; 
	    	    
	    	    int choice = Integer.parseInt(input.trim());

	            ElectoralOfficedao dao = new ElectoralOfficedao();
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	            switch (choice) {
	                case 1:
	            }
	    	}
	    	 catch (Exception e) {
	             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
	         }
	}
}
}