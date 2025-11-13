package com.ust.evs.ui;

import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import com.ust.evs.dao.Voterdao;

public class UserUI {
public static void showUserMenu() {
	while(true) {
    	try {
    	    String menu = "EVS EO Panel\n\n"
    	                + "1. Cast Vote"
    	                + "2. View CandidatebyElectionName"
    	                + "3. View List of Election Result"
    	                + "4. Request VoterID"
    	                + "5. View Generated VoterID"
    	                + "6. View List of Elections"
    	                + "7. LogOut";

    	    String input = JOptionPane.showInputDialog(null, menu);
    	    
    	    if (input == null) return; 
    	    
    	    int choice = Integer.parseInt(input.trim());

            Voterdao dao = new Voterdao();
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