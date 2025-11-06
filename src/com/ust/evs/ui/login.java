package com.ust.evs.ui;

import javax.swing.JOptionPane;
import com.ust.evs.bean.ElectionBean;
import com.ust.evs.dao.Admdao;
import com.ust.evs.service.Administrator;
import java.text.SimpleDateFormat;
import java.util.Date;

public class login {
    public static void main(String[] args) {
        String userId = JOptionPane.showInputDialog("Enter User ID:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        if (userId.equals("ADMIN") && password.equals("ADMIN@123")) {
            Administrator admin = new Admdao();
            boolean running = true;

            while (running) {
                String choice = JOptionPane.showInputDialog(
                		"Choose an option:\n1. Add Election\n2.Remove Election\n3.View Election Details\n4.View All Upcoming Election\n5.Add Party\n6.View Party\n7.View All Pending Request Voterid8.Add Candidate\n9. Exit"
                );

                switch (choice) {
                    case "1":
                        try {
                            ElectionBean electionBean = new ElectionBean();
                            electionBean.setElectionID(JOptionPane.showInputDialog("Enter Election ID:"));
                            electionBean.setName(JOptionPane.showInputDialog("Enter Election Name:"));

                            String electionDateStr = JOptionPane.showInputDialog("Enter Election Date (YYYY-MM-DD):");
                            String countingDateStr = JOptionPane.showInputDialog("Enter Counting Date (YYYY-MM-DD):");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date electionDate = sdf.parse(electionDateStr);
                            Date countingDate = sdf.parse(countingDateStr);

                            electionBean.setElectionDate(electionDate);
                            electionBean.setCountingDate(countingDate);
                            electionBean.setDistrict(JOptionPane.showInputDialog("Enter District:"));
                            electionBean.setConstituency(JOptionPane.showInputDialog("Enter Constituency:"));
                            
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Invalid date format. Use YYYY-MM-DD.");
                        }
                        break;
                    case "3":
                    	admin.viewElections(); // 👈 This prints the election list
                        break;
                    case "9":
                        running = false;
                        JOptionPane.showMessageDialog(null, "Exiting...");
                        admin.viewElections(); // 👈 This prints the election list
                        break;


                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice. Try again.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid User ID and Password");
        }
        
    }
}
