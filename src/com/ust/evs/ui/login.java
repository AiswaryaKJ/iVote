package com.ust.evs.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import com.ust.evs.bean.ElectionBean;
import com.ust.evs.dao.Admdao;
import com.ust.evs.dao.ElectoralOfficedao;
import com.ust.evs.dao.Voterdao;
import com.ust.evs.service.Administrator;
import com.ust.evs.service.ElectoralOfficer;
import com.ust.evs.service.Voter;

public class login {

    public static void main(String[] args) {

        String userId = JOptionPane.showInputDialog("Enter User ID:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        if (userId.equals("ADMIN") && password.equals("ADMIN@123")) {

            Administrator admin = new Admdao();
            adminMenu(admin);   

        }
        else if (userId.equals("EO") && password.equals("EO@123")) {

            ElectoralOfficer eo = new ElectoralOfficedao();
            eoMenu(eo);   

        }
        else if (userId.equals("ADMIN") && password.equals("ADMIN@123")) {

            Voter citizen = new Voterdao();
            VoterMenu(citizen);   

        }
        else {
            JOptionPane.showMessageDialog(null, "Invalid Login!");
        }
    }

    public static void adminMenu(Administrator admin) {

        while (true) {
            String menu = """
                    Select an option:
                    
                    1. Add Election
                    2. View All Elections
                    3. View Upcoming Elections
                    4. Add Party
                    5. View All Parties
                    6. Add Candidate
                    7. ViewCandidateDetailsByElectionName
                    8. ViewCandidatesDetailsByPartyName
                    9. ViewAllPendingApplication
                    10.ForwardVoterIDRequest
                    11. Logout
                    """;

            String choice = JOptionPane.showInputDialog(menu);

            if (choice == null) break; 

            switch (choice) {

                case "1":
                	

                    String elecID = JOptionPane.showInputDialog("Enter Election ID:");
                    String name = JOptionPane.showInputDialog("Enter Election Name:");

                   
                    String electionDateStr = JOptionPane.showInputDialog("Enter Election Date (YYYY-MM-DD):");
                    String countingDateStr = JOptionPane.showInputDialog("Enter Counting Date (YYYY-MM-DD):");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date electionDate = null;
                    Date countingDate = null;
				try {
					electionDate = sdf.parse(electionDateStr);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Invalid ElectionDate!");
					break;
				}
				try {
					countingDate = sdf.parse(countingDateStr);
				} catch (ParseException ex) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Invalid CountingDate!");
					break;
				}
				String district = JOptionPane.showInputDialog("Enter District:");
	            String cons= JOptionPane.showInputDialog("Enter Constituency:");
				ElectionBean eb = new ElectionBean(elecID,name,electionDate,district, cons, countingDate);
			
                    admin.addElection(eb);
                    
                    break;

                case "2":
                    JOptionPane.showMessageDialog(null, admin.viewElections());
                    break;

                case "3":
                    admin.viewAllUpcomingElections();
                    break;

                case "4":
                    admin.addParty(null);
                    break;

                case "5":
                    JOptionPane.showMessageDialog(null, admin.viewAllParty());
                    break;
                case "6":
                	admin.addCandidate(null);
                	break;
                	
                case "7":
                    String electionName = JOptionPane.showInputDialog("Enter Election Name:");
                    admin.viewCandidateDetailsByElectionName(electionName);
                    break;
                
                case "8":
                	String PartyID = JOptionPane.showInputDialog("Enter Party ID:");
                    admin.viewCandidateDetailsByElectionName(PartyID);
                    break;

                case "11":
                    JOptionPane.showMessageDialog(null, "Logged Out!");
                    return;
                    
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice!");
            }
        }
    }
    public static void eoMenu(ElectoralOfficer eo) {
}
    public static void VoterMenu(Voter citizen) {
}
}