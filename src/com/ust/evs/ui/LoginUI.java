package com.ust.evs.ui;

import javax.swing.*;
import com.ust.evs.bean.CredentialBean;
import com.ust.evs.dao.LoginDao;

public class LoginUI {
    public static void main(String[] args) {
        String userID = JOptionPane.showInputDialog("Enter User ID:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        LoginDao dao = new LoginDao();
        CredentialBean user = dao.authenticate(userID, password);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Invalid credentials.");
        } else {
            JOptionPane.showMessageDialog(null, "Welcome " + user.getUserID());
            switch (user.getUserType()) {
                case "A":
                    AdminUI.showAdminMenu();
                    break;
                case "E":
                    ElectoralOfficerUI.showElectoralMenu();
                    break;
                case "U":
                    UserUI.showUserMenu();
                    break;
            }
        }

            
    }
}

