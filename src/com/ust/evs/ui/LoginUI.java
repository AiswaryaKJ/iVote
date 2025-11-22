package com.ust.evs.ui;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import java.util.ArrayList;



import javax.swing.JLabel;

import javax.swing.JOptionPane;

import javax.swing.SwingConstants;

import com.ust.evs.bean.CredentialBean;

import com.ust.evs.bean.ElectionBean;

import com.ust.evs.bean.PartyBean;

import com.ust.evs.bean.ProfileBean;

import com.ust.evs.dao.Admdao;
import com.ust.evs.dao.LoginDao;
import com.ust.evs.dao.Userdao;

public class LoginUI {

  private static String currentUserId = null;

  public static void main(String[] args) {
    while (true) {

      // Show a simple menu with buttons

      String[] options = {"Login", "Register", "Exit"};

      int choice = JOptionPane.showOptionDialog(

          null,

          "   Welcome to iVote     ",

          "Election Voting System",

          JOptionPane.DEFAULT_OPTION,

          JOptionPane.INFORMATION_MESSAGE,

          null,

          options,

          options[0]);



      if (choice == 0) {

        onLogin();

      } else if (choice == 1) {

        onRegister();

      } else {

        break; // Exit

      }

    }

  }


  private static void onRegister() {

    // Collect registration details from user

    String firstName = JOptionPane.showInputDialog("Enter First Name:");

    String lastName = JOptionPane.showInputDialog("Enter Last Name:");

    String dob = JOptionPane.showInputDialog("Enter Date of Birth (yyyy-mm-dd):");

    String gender = JOptionPane.showInputDialog("Enter Gender (M/F):");

    String street = JOptionPane.showInputDialog("Enter Street:");

    String location = JOptionPane.showInputDialog("Enter Location:");

    String city = JOptionPane.showInputDialog("Enter City:");

    String state = JOptionPane.showInputDialog("Enter State:");

    String pincode = JOptionPane.showInputDialog("Enter Pincode:");

    String mobileNo = JOptionPane.showInputDialog("Enter Mobile No:");

    String emailID = JOptionPane.showInputDialog("Enter Email ID:");
    
    String constituency = JOptionPane.showInputDialog("Enter constituency:");

    String password = JOptionPane.showInputDialog("Enter Password:");

    try {

      // Fill ProfileBean

      ProfileBean profile = new ProfileBean();

      profile.setFirstName(firstName);

      profile.setLastName(lastName);

      profile.setDateOfBirth(java.sql.Date.valueOf(dob)); // convert String to java.sql.Date

      profile.setGender(gender);

      profile.setStreet(street);

      profile.setLocation(location);

      profile.setCity(city);

      profile.setState(state);

      profile.setPincode(pincode);

      profile.setMobileNo(mobileNo);

      profile.setEmailID(emailID);
      
      profile.setConstituency(constituency);

      profile.setPassword(password);

      // Call DAO register

      Userdao dao = new Userdao();
      

      String result = dao.register(profile);

      if (!"FAIL".equals(result)) {

        JOptionPane.showMessageDialog(null, "Registration successful! Your User ID is: " + result);

      } else {

        JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");

      }

    } catch (Exception e) {

      JOptionPane.showMessageDialog(null, "Error during registration: " + e.getMessage());

      e.printStackTrace();
    }
  }
   private static void onLogin() {
       String userID = JOptionPane.showInputDialog("Enter User ID:");
       String password = JOptionPane.showInputDialog("Enter Password:");

       LoginDao dao = new LoginDao();
       CredentialBean user = dao.authenticate(userID, password);

       if (user == null) {
           JOptionPane.showMessageDialog(null, "Invalid credentials.");
       } else {
           currentUserId = user.getUserID();
           JOptionPane.showMessageDialog(null, "Welcome " + currentUserId);

           switch (user.getUserType()) {
               case "A":
                   AdminUI.showAdminMenu();
                   break;
               case "E":
                   try {
                       // Save EO id
                       com.ust.evs.dao.ElectoralOfficedao.loggedInEOId = currentUserId;

                       // Fetch constituency from EO table
                       PreparedStatement psCons = com.ust.evs.util.DBUtil.con.prepareStatement(
                           "SELECT Constituency FROM evs_tbl_eo WHERE Electoralofficerid = ?"
                       );
                       psCons.setString(1, currentUserId);
                       ResultSet rsCons = psCons.executeQuery();
                       if (rsCons.next()) {
                           com.ust.evs.dao.ElectoralOfficedao.loggedInEOConstituency = rsCons.getString("Constituency");
                           System.out.println("DEBUG: EO " + currentUserId +
                                              " constituency = " + com.ust.evs.dao.ElectoralOfficedao.loggedInEOConstituency);
                       } else {
                           System.out.println("No constituency found for EO " + currentUserId);
                       }
                   } catch (Exception e) {
                       JOptionPane.showMessageDialog(null, "Error fetching EO constituency: " + e.getMessage());
                   }

                   ElectoralOfficerUI.showElectoralMenu();
                   break;
               case "V":
            	    UserUI.showUserMenu(userID);
            	    break;

               default:
                   JOptionPane.showMessageDialog(null, "Unknown user type.");
           }
       }
   }}
    	