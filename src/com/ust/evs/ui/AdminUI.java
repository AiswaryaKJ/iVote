package com.ust.evs.ui;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.ust.evs.bean.*;
import com.ust.evs.dao.Admdao;

public class AdminUI {
    	public static void showAdminMenu() {
    	while(true) {
    	try {
    	    String menu = "EVS Admin Panel\n\n"
    	                + "1. Add Election\n"
    	                + "2. Add Party\n"
    	                + "3. Add Candidate\n"
    	                + "4. View Election\n"
    	                + "5. View Upcoming Election\n"
    	                + "6. View All Party\n"
    	                + "7. View Candidate by ElectionName\n"
    	                + "8. View Candidate by PartID\n"
    	                + "9. View Pending Application\n"
    	                + "10.Forward VoterID Request\n"
    	                + "11.Approve Election Result\n"
    	                + "12.LogOut";

    	    String input = JOptionPane.showInputDialog(null, menu);
    	    
    	    if (input == null) return; 
    	    
    	    int choice = Integer.parseInt(input.trim());

            Admdao dao = new Admdao();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            switch (choice) {
            
                case 1:
                    String electionId = JOptionPane.showInputDialog("Enter Election ID:");
                    String name = JOptionPane.showInputDialog("Enter Election Name:");
                    String electionDateStr = JOptionPane.showInputDialog("Enter Election Date (yyyy-MM-dd):");
                    String district = JOptionPane.showInputDialog("Enter District:");
                    String constituency = JOptionPane.showInputDialog("Enter Constituency:");
                    String countingDateStr = JOptionPane.showInputDialog("Enter Counting Date (yyyy-MM-dd):");

                    Date electionDate = sdf.parse(electionDateStr);
                    Date countingDate = sdf.parse(countingDateStr);

                    ElectionBean electionBean = new ElectionBean(electionId, name, electionDate, district, constituency, countingDate);
                    String electionResult = dao.addElection(electionBean);
                    JOptionPane.showMessageDialog(null, "Election Add Status: " + electionResult);
                    break;

                case 2:
                    String partyId = JOptionPane.showInputDialog("Enter Party ID:");
                    String partyName = JOptionPane.showInputDialog("Enter Party Name:");
                    String leader = JOptionPane.showInputDialog("Enter Party Leader:");
                    String symbol = JOptionPane.showInputDialog("Enter Party Symbol:");

                    PartyBean partyBean = new PartyBean(partyId,partyName,leader,symbol);
                    partyBean.setPartyID(partyId);
                    partyBean.setName(partyName);
                    partyBean.setLeader(leader);
                    partyBean.setSymbol(symbol);

if (partyId == null || partyId.trim().isEmpty() ||
    partyName == null || partyName.trim().isEmpty() ||
    leader == null || leader.trim().isEmpty() || symbol==null || symbol.trim().isEmpty()){
    JOptionPane.showMessageDialog(null, "Party ID, Name, Leader, symbol are required fields.");
    return;
}

                    String partyResult = dao.addParty(partyBean);
                    JOptionPane.showMessageDialog(null, "Party Add Status: " + partyResult);
                    break;

                case 3:
                    String candidateId = JOptionPane.showInputDialog("Enter Candidate ID:");
                    String candidateName = JOptionPane.showInputDialog("Enter Candidate Name:");
                    String candElectionId = JOptionPane.showInputDialog("Enter Election ID:");
                    String candPartyId = JOptionPane.showInputDialog("Enter Party ID:");
                    String candDistrict = JOptionPane.showInputDialog("Enter District:");
                    String candConstituency = JOptionPane.showInputDialog("Enter Constituency:");
                    String dobStr = JOptionPane.showInputDialog("Enter Date of Birth (yyyy-MM-dd):");
                    String mobile = JOptionPane.showInputDialog("Enter Mobile Number:");
                    String address = JOptionPane.showInputDialog("Enter Address:");
                    String email = JOptionPane.showInputDialog("Enter Email ID:");

                    Date dob = sdf.parse(dobStr);

                    CandidateBean candidateBean = new CandidateBean(candidateId,candidateName,candElectionId,candPartyId,candDistrict,candConstituency,dob,mobile,address,email);
                    candidateBean.setCandidateID(candidateId);
                    candidateBean.setName(candidateName);
                    candidateBean.setElectionID(candElectionId);
                    candidateBean.setPartyID(candPartyId);
                    candidateBean.setDistrict(candDistrict);
                    candidateBean.setConstituency(candConstituency);
                    candidateBean.setDateOfBirth(dob);
                    candidateBean.setMobileNo(mobile);
                    candidateBean.setAddress(address);
                    candidateBean.setEmailID(email);

                    String candidateResult = dao.addCandidate(candidateBean);
                    JOptionPane.showMessageDialog(null, "Candidate Add Status: " + candidateResult);
                    break;
                case 4:
                    ArrayList<ElectionBean> allElections = dao.viewElections();
                    StringBuilder sb = new StringBuilder("All Elections:\n");

                    for (ElectionBean e : allElections) {
                        sb.append("ID: ").append(e.getElectionID())
                          .append(", Name: ").append(e.getName())
                          .append(", Date: ").append(e.getElectionDate())
                          .append(", District: ").append(e.getDistrict())
                          .append(", Constituency: ").append(e.getConstituency())
                          .append(", Counting Date: ").append(e.getCountingDate())
                          .append("\n");
                    }

                    JOptionPane.showMessageDialog(null, sb.toString());
                    break;
                case 5:
                	 ArrayList<ElectionBean> allupcomingElections = dao.viewElections();
                     StringBuilder sb1 = new StringBuilder("All Elections:\n");

                     for (ElectionBean e : allupcomingElections) {
                         sb1.append("ID: ").append(e.getElectionID())
                           .append(", Name: ").append(e.getName())
                           .append(", Date: ").append(e.getElectionDate())
                           .append(", District: ").append(e.getDistrict())
                           .append(", Constituency: ").append(e.getConstituency())
                           .append(", Counting Date: ").append(e.getCountingDate())
                           .append("\n");
                     }

                     JOptionPane.showMessageDialog(null, sb1.toString());
                     break;
                case 6:
                	ArrayList<PartyBean> allParties = dao.viewAllParty();
                    StringBuilder sb2 = new StringBuilder("All Parties:\n");

                    for (PartyBean p : allParties) {
                        sb2.append("ID: ").append(p.getPartyID())
                          .append(", Name: ").append(p.getName())
                          .append(", Leader: ").append(p.getLeader())
                          .append(", Symbol: ").append(p.getSymbol())
                          .append("\n");
                    }

                    JOptionPane.showMessageDialog(null, sb2.toString());
                    break;
                case 7:
                    String electionName = JOptionPane.showInputDialog("Enter Election Name:");
                    if (electionName == null || electionName.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Election name is required.");
                        break;
                    }

                    ArrayList<CandidateBean> candidates = dao.viewCandidateDetailsByElectionName(electionName);
                    if (candidates.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No candidates found for election: " + electionName);
                    } else {
                        StringBuilder sb3 = new StringBuilder("Candidates for Election: " + electionName + "\n\n");
                        for (CandidateBean c : candidates) {
                            sb3.append("ID: ").append(c.getCandidateID())
                              .append(", Name: ").append(c.getName())
                              .append(", Party ID: ").append(c.getPartyID())
                              .append(", District: ").append(c.getDistrict())
                              .append(", Constituency: ").append(c.getConstituency())
                              .append(", DOB: ").append(c.getDateOfBirth())
                              .append(", Mobile: ").append(c.getMobileNo())
                              .append(", Email: ").append(c.getEmailID())
                              .append("\n");
                        }
                        JOptionPane.showMessageDialog(null, sb3.toString());
                    }
                    break;
                case 8:
                	String partyId1 = JOptionPane.showInputDialog("Enter Party Id:");
                    if (partyId1 == null || partyId1.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Party Id is required.");
                        break;
                    }

                    ArrayList<CandidateBean> candidates1 = dao.viewCandidateDetailsByElectionName(partyId1);
                    if (candidates1.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No candidates found for Party: " + partyId1);
                    } else {
                        StringBuilder sb3 = new StringBuilder("Candidates for Party: " + partyId1 + "\n\n");
                        for (CandidateBean c : candidates1) {
                            sb3.append("ID: ").append(c.getCandidateID())
                              .append(", Name: ").append(c.getName())
                              .append(", Party ID: ").append(c.getPartyID())
                              .append(", District: ").append(c.getDistrict())
                              .append(", Constituency: ").append(c.getConstituency())
                              .append(", DOB: ").append(c.getDateOfBirth())
                              .append(", Mobile: ").append(c.getMobileNo())
                              .append(", Email: ").append(c.getEmailID())
                              .append("\n");
                        }
                        JOptionPane.showMessageDialog(null, sb3.toString());
                    }
                	break;
                case 12:
                	JOptionPane.showMessageDialog(null, "You have been logged out.");
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice or cancelled.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    
}
}}