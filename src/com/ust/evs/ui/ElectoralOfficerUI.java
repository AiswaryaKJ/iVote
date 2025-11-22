package com.ust.evs.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.ust.evs.bean.ApplicationBean;
import com.ust.evs.dao.ElectoralOfficedao;

public class ElectoralOfficerUI {
	public static void showElectoralMenu() {
		while(true) {
	    	try {
	    	    String menu = "EVS EO Panel\n\n"
	    	                + "1. Generate VoterID\n"
	    	                + "2. View All Voter Application\n"
	    	                + "3. LogOut\n";

	    	    String input = JOptionPane.showInputDialog(null, menu);
	    	    
	    	    if (input == null) return; 
	    	    
	    	    int choice = Integer.parseInt(input.trim());

	            ElectoralOfficedao dao = new ElectoralOfficedao();
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	            switch (choice) {
	            case 1:
	            
	                String userId = JOptionPane.showInputDialog("Enter User ID to generate Voter ID:");
	                String status = JOptionPane.showInputDialog("Enter status (APPROVED/REJECTED):");
	                String voterId = dao.generateVoterId(userId, status);
	                JOptionPane.showMessageDialog(null, "Generated Voter ID: " + voterId);
	                break;

	            case 2: // View All Voter Applications
	                ArrayList<ApplicationBean> apps = dao.viewAllVoterIdApplications();
	                if (apps.isEmpty()) {
	                    JOptionPane.showMessageDialog(null, "No applications found.");
	                } else {
	                    StringBuilder sb = new StringBuilder("Voter Applications:\n\n");
	                    for (ApplicationBean app : apps) {
	                        sb.append("UserID: ").append(app.getUserID())
	                          .append(", Constituency: ").append(app.getConstituency())
	                          .append(", PassedStatus: ").append(app.getPassedStatus())
	                          .append(", ApprovedStatus: ").append(app.getApprovedStatus())
	                          .append(", VoterID: ").append(app.getVoterID())
	                          .append("\n");
	                    }
	                    JOptionPane.showMessageDialog(null, sb.toString());
	                }
	                break;

	            	
	            case 3:
                	JOptionPane.showMessageDialog(null, "You have been logged out.");
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice or cancelled.");

	                	
	            }
	    	}
	    	 catch (Exception e) {
	             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
	         }
	}
}
}