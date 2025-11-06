package com.ust.evs.ui;

import javax.swing.JOptionPane;
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
                    6. Logout
                    """;

            String choice = JOptionPane.showInputDialog(menu);

            if (choice == null) break; 

            switch (choice) {

                case "1":
                    admin.addElection(null);
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
