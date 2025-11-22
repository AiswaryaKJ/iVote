package com.ust.evs.ui;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.ust.evs.bean.*;
import com.ust.evs.dao.Admdao;
import com.ust.evs.dao.Voterdao;

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
                String name = JOptionPane.showInputDialog("Enter Election Name:");
                String electionDateStr = JOptionPane.showInputDialog("Enter Election Date (yyyy-MM-dd):");
                String district = JOptionPane.showInputDialog("Enter District:");
                String constituency = JOptionPane.showInputDialog("Enter Constituency:");
                String countingDateStr = JOptionPane.showInputDialog("Enter Counting Date (yyyy-MM-dd):");

                Date electionDate = sdf.parse(electionDateStr);
                Date countingDate = sdf.parse(countingDateStr);

                // Pass null for ElectionId, DAO will generate it
                ElectionBean electionBean = new ElectionBean(null, name, electionDate, district, constituency, countingDate);

                String electionResult = dao.addElection(electionBean);
                JOptionPane.showMessageDialog(null, "Election Add Status: " + electionResult);
                break;


                case 2:
                    String partyName = JOptionPane.showInputDialog("Enter Party Name:");
                    String leader = JOptionPane.showInputDialog("Enter Party Leader:");
                    String symbol = JOptionPane.showInputDialog("Enter Party Symbol:");

                    // Build PartyBean without PartyId (DAO will generate it)
                    PartyBean partyBean = new PartyBean(null, partyName, leader, symbol);
                    partyBean.setName(partyName);
                    partyBean.setLeader(leader);
                    partyBean.setSymbol(symbol);

                    // Validation
                    if (partyName == null || partyName.trim().isEmpty() ||
                        leader == null || leader.trim().isEmpty() ||
                        symbol == null || symbol.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Name, Leader, and Symbol are required fields.");
                        return;
                    }

                    // DAO will generate PartyId and return it
                    String partyResult = dao.addParty(partyBean);
                    JOptionPane.showMessageDialog(null, "Party Add Status: " + partyResult);
                    break;


                case 3:
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

                    // Pass null for CandidateId, DAO will generate it
                    CandidateBean candidateBean = new CandidateBean(null, candidateName, candElectionId, candPartyId,
                                                                    candDistrict, candConstituency, dob, mobile, address, email);
                    candidateBean.setName(candidateName);
                    candidateBean.setElectionID(candElectionId);
                    candidateBean.setPartyID(candPartyId);
                    candidateBean.setDistrict(candDistrict);
                    candidateBean.setConstituency(candConstituency);
                    candidateBean.setDateOfBirth(dob);
                    candidateBean.setMobileNo(mobile);
                    candidateBean.setAddress(address);
                    candidateBean.setEmailID(email);

                    // DAO will generate CandidateId and return it
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
                case 9: // View Pending Applications
                    ArrayList<ApplicationBean> pendingApps = dao.viewAllAdminPendingApplications();

                    if (pendingApps.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No pending applications found.");
                    } else {
                        StringBuilder sb11 = new StringBuilder("Pending Applications:\n\n");
                        for (ApplicationBean app : pendingApps) {
                            sb11.append("UserID: ").append(app.getUserID())
                              .append(", Constituency: ").append(app.getConstituency())
                              .append(", PassedStatus: ").append(app.getPassedStatus())
                              .append(", ApprovedStatus: ").append(app.getApprovedStatus())
                              .append("\n");
                        }
                        JOptionPane.showMessageDialog(null, sb11.toString());
                    }
                    break;
                case 10: // Forward VoterID Request
                    String userIdToForward = JOptionPane.showInputDialog("Enter User ID to forward:");

                    if (userIdToForward == null || userIdToForward.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "User ID is required.");
                        break;
                    }

                    boolean forwardResult = dao.forwardVoterIDRequest(userIdToForward);

                    if (forwardResult) {
                        JOptionPane.showMessageDialog(null, "Request forwarded to EO successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to forward request. Please check User ID.");
                    }
                    break;
                case 11:
                    // Admin approves election results and views winner(s)
                    String electionIdAdmin = JOptionPane.showInputDialog(
                            null,
                            "Enter Election ID to approve results:",
                            "Approve Election Results",
                            JOptionPane.QUESTION_MESSAGE);

                    if (electionIdAdmin != null && !electionIdAdmin.trim().isEmpty()) {
                        Admdao service = new Admdao();
                        Map<String, Object> resultMap = service.approveElectionResults(electionIdAdmin);

                        StringBuilder sb11 = new StringBuilder();
                        sb11.append("Election ID: ").append(electionIdAdmin).append("\n");
                        sb11.append("Status: ").append(resultMap.get("status")).append("\n\n");

                        // Show each candidate’s votes
                        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
                            String key = entry.getKey();
                            if (!key.equals("status") && !key.equals("winner") && !key.equals("winnerVotes")) {
                                sb11.append("Candidate ").append(key)
                                  .append(" → Votes: ").append(entry.getValue()).append("\n");
                            }
                        }

                        // Show winner(s)
                        Object winners = resultMap.get("winner");
                        Object winnerVotes = resultMap.get("winnerVotes");
                        if (winners != null) {
                            sb11.append("\nWinner(s): ").append(winners)
                              .append(" with ").append(winnerVotes).append(" votes");
                        }

                        JOptionPane.showMessageDialog(
                                null,
                                sb11.toString(),
                                "Approved Election Results",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Election ID is required!",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
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