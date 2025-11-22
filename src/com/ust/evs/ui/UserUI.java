package com.ust.evs.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.ust.evs.bean.CandidateBean;
import com.ust.evs.bean.ElectionBean;
import com.ust.evs.bean.ResultBean;
import com.ust.evs.dao.Admdao;
import com.ust.evs.dao.Voterdao;

public class UserUI {
    public static void showUserMenu(String currentUserId) { // pass logged-in userId
        while (true) {
            try {
                Voterdao dao = new Voterdao();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                // Prompt once for constituency to check voter ID
                String constituencyForCheck = JOptionPane.showInputDialog("Enter your Constituency:");
                String voterIdCheck = dao.ViewGeneratedVoterid(currentUserId, constituencyForCheck);

                boolean hasVoterId = !(voterIdCheck == null 
                                       || voterIdCheck.equals("FAIL") 
                                       || voterIdCheck.equals("NOT GENERATED"));

                // Build menu dynamically
                StringBuilder menu = new StringBuilder("EVS User Panel\n\n");
                int optionNumber = 1;

                if (hasVoterId) {
                    menu.append(optionNumber++).append(". Cast Vote\n");
                }
                menu.append(optionNumber++).append(". View CandidatebyElectionName\n")
                    .append(optionNumber++).append(". View List of Election Result\n")
                    .append(optionNumber++).append(". Request VoterID\n")
                    .append(optionNumber++).append(". View Generated VoterID\n")
                    .append(optionNumber++).append(". View List of Elections\n")
                    .append(optionNumber++).append(". LogOut\n");

                String input = JOptionPane.showInputDialog(null, menu.toString());
                if (input == null) return;

                int choice = Integer.parseInt(input.trim());

                // If Cast Vote is hidden, shift choices down by 1
                int adjustedChoice = hasVoterId ? choice : choice + 1;

                switch (adjustedChoice) {
                case 1: // Cast Vote
                	String voterId = JOptionPane.showInputDialog(null, "Enter Voter ID:", "Voting System", JOptionPane.QUESTION_MESSAGE);

                    // If user cancels or leaves empty
                    if (voterId == null || voterId.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Voter ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Ask for Election ID
                    String electionId = JOptionPane.showInputDialog(null, "Enter Election ID:", "Voting System", JOptionPane.QUESTION_MESSAGE);
                    if (electionId == null || electionId.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Election ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Ask for Candidate ID (could be dropdown, but here simple input)
                    String candidateId = JOptionPane.showInputDialog(null, "Enter Candidate ID:", "Voting System", JOptionPane.QUESTION_MESSAGE);
                    if (candidateId == null || candidateId.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Candidate ID is required!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Call your backend method
                    Voterdao service = new Voterdao(); // assume this has CastVote method
                    String result = service.CastVote(voterId, electionId, candidateId);

                    // Show result
                    JOptionPane.showMessageDialog(null, "Result: " + result, "Voting System", JOptionPane.INFORMATION_MESSAGE);
                
            
            break;
            
                    case 2: // View CandidatebyElectionName
                        String electionName1 = JOptionPane.showInputDialog("Enter Election Name:");
                        String constituency = JOptionPane.showInputDialog("Enter Constituency:");
                        ArrayList<CandidateBean> candidates = dao.viewCandidatesByElectionName(electionName1, constituency);

                        if (candidates.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                "No candidates found for Election: " + electionName1 + " in Constituency: " + constituency);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append("Candidates for Election: ").append(electionName1)
                              .append(" in Constituency: ").append(constituency).append("\n\n");

                            for (CandidateBean c : candidates) {
                                sb.append("Candidate ID: ").append(c.getCandidateID()).append("\n")
                                  .append("Name: ").append(c.getName()).append("\n")
                                  .append("Party ID: ").append(c.getPartyID()).append("\n")
                                  .append("District: ").append(c.getDistrict()).append("\n")
                                  .append("Constituency: ").append(c.getConstituency()).append("\n")
                                  .append("DOB: ").append(c.getDateOfBirth()).append("\n")
                                  .append("Mobile: ").append(c.getMobileNo()).append("\n")
                                  .append("Email: ").append(c.getEmailID()).append("\n")
                                  .append("Address: ").append(c.getAddress()).append("\n")
                                  .append("-----------------------------\n");
                            }
                            JOptionPane.showMessageDialog(null, sb.toString());
                        }
                        break;

                    case 3:
                    
                        // Voter views list of all election results
                        Voterdao service1 = new Voterdao();
                        ArrayList<ResultBean> results = service1.viewListOfElectionsResults();

                        if (results.isEmpty()) {
                            JOptionPane.showMessageDialog(null,
                                    "No election results available.",
                                    "Election Results",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            StringBuilder sb = new StringBuilder("Election Results:\n\n");
                            for (ResultBean bean : results) {
                                sb.append(bean.toString()).append("\n");
                            }

                            JOptionPane.showMessageDialog(null,
                                    sb.toString(),
                                    "Election Results",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;



                    case 4: // Request VoterID
                        String constituency1 = JOptionPane.showInputDialog("Enter your Constituency:");
                        String result1 = dao.RequestVoterid(currentUserId, constituency1);
                        if ("FAIL".equals(result1)) {
                            JOptionPane.showMessageDialog(null, "Unable to submit application. Please try again.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Request Status: " + result1);
                        }
                        break;

                    case 5: // View Generated VoterID
                        String constituency2 = JOptionPane.showInputDialog("Enter your User constituency:");
                        String voterId1 = dao.ViewGeneratedVoterid(currentUserId, constituency2);
                        if ("FAIL".equals(voterId1)) {
                            JOptionPane.showMessageDialog(null, "Error occurred while fetching Voter ID.");
                        } else if ("NOT GENERATED".equals(voterId1)) {
                            JOptionPane.showMessageDialog(null, "Your Voter ID has not been generated yet.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Your Voter ID is: " + voterId1);
                        }
                        break;

                    case 6: // View List of Elections
                        ArrayList<ElectionBean> elections = dao.viewListOfElections();
                        if (elections.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No elections found.");
                        } else {
                            String[] columns = {"Election ID", "Name", "Election Date", "District", "Constituency", "Counting Date"};
                            Object[][] data = new Object[elections.size()][6];
                            for (int i = 0; i < elections.size(); i++) {
                                ElectionBean e = elections.get(i);
                                data[i][0] = e.getElectionID();
                                data[i][1] = e.getName();
                                data[i][2] = e.getElectionDate();
                                data[i][3] = e.getDistrict();
                                data[i][4] = e.getConstituency();
                                data[i][5] = e.getCountingDate();
                            }
                            JTable table = new JTable(data, columns);
                            JScrollPane scrollPane = new JScrollPane(table);
                            table.setFillsViewportHeight(true);
                            JOptionPane.showMessageDialog(null, scrollPane, "List of Elections", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;

                    case 7: // LogOut
                        JOptionPane.showMessageDialog(null, "You have been logged out.");
                        return;

                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice or cancelled.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }
}
