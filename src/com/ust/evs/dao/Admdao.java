package com.ust.evs.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.ust.evs.bean.ApplicationBean;
import com.ust.evs.bean.CandidateBean;
import com.ust.evs.bean.ElectionBean;
import com.ust.evs.bean.PartyBean;
import com.ust.evs.service.Administrator;

public class Admdao implements Administrator {

    ArrayList<ElectionBean> electionArray = new ArrayList<>();
    ArrayList<PartyBean> partyArray = new ArrayList<>();
    ArrayList<CandidateBean> candidateArray = new ArrayList<>();
    ArrayList<ApplicationBean> applicationArray = new ArrayList<>();

    
    @Override
    public ArrayList<ElectionBean> addElection(ElectionBean electionBean) {

        try {
            electionArray.add(electionBean);

            JOptionPane.showMessageDialog(null, "Election Added Successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed!!");
        }
        return null;
    }

   
    @Override
    public ArrayList<ElectionBean> viewAllUpcomingElections() {

        ArrayList<ElectionBean> upcoming = new ArrayList<>();
        Date today = new Date();

        for (ElectionBean e : electionArray) {
            if (e.getElectionDate() != null && e.getElectionDate().after(today)) {
                upcoming.add(e);
            }
        }

        if (upcoming.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Upcoming Elections.");
        } else {
            StringBuilder sb = new StringBuilder("Upcoming Elections:\n\n");
            for (ElectionBean e : upcoming) {
                sb.append("ID: ").append(e.getElectionID()).append("\n");
                sb.append("Name: ").append(e.getName()).append("\n");
                sb.append("Date: ").append(e.getElectionDate()).append("\n");
                sb.append("District: ").append(e.getDistrict()).append("\n");
                sb.append("Constituency: ").append(e.getConstituency()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
        return upcoming;
    }

    @Override
    public ArrayList<ElectionBean> viewElections() {
        return electionArray;
    }

   
    @Override
    public String addParty(PartyBean partyBean) {

        PartyBean pb = new PartyBean(null, null, null, null);
        pb.setPartyID(JOptionPane.showInputDialog("Enter Party ID:"));
        pb.setName(JOptionPane.showInputDialog("Enter Party Name:"));
        pb.setLeader(JOptionPane.showInputDialog("Enter Party Leader:"));

        partyArray.add(pb);

        JOptionPane.showMessageDialog(null, "Party Added Successfully!");
        return "SUCCESS";
    }

   
    @Override
    public ArrayList<PartyBean> viewAllParty() {

        if (partyArray.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Parties Available.");
        } else {
            StringBuilder sb = new StringBuilder("Party List:\n\n");
            for (PartyBean p : partyArray) {
                sb.append("ID: ").append(p.getPartyID()).append("\n");
                sb.append("Name: ").append(p.getName()).append("\n");
                sb.append("Leader: ").append(p.getLeader()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
        return partyArray;
    }

   
    @Override
    public String addCandidate(CandidateBean candidateBean) {

        CandidateBean cb = new CandidateBean(null, null, null, null, null, null, null, null, null, null);

        cb.setCandidateID(JOptionPane.showInputDialog("Enter Candidate ID:"));
        cb.setName(JOptionPane.showInputDialog("Enter Candidate Name:"));
        cb.setElectionID(JOptionPane.showInputDialog("Enter Election ID:"));
        cb.setPartyID(JOptionPane.showInputDialog("Enter Party ID:"));

        candidateArray.add(cb);

        JOptionPane.showMessageDialog(null, "Candidate Added Successfully!");
        return "SUCCESS";
    }

   
    @Override
    public ArrayList<CandidateBean> viewCandidateDetailsByElectionName(String electionName) {

        ArrayList<CandidateBean> list = new ArrayList<>();

        for (CandidateBean c : candidateArray) {
            ElectionBean e = getElectionById(c.getElectionID());
            if (e != null && e.getName().equalsIgnoreCase(electionName)) {
                list.add(c);
            }
        }

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Candidates Found for this Election.");
        } else {
            StringBuilder sb = new StringBuilder("Candidates:\n\n");
            for (CandidateBean c : list) {
                sb.append("ID: ").append(c.getCandidateID()).append("\n");
                sb.append("Name: ").append(c.getName()).append("\n");
                sb.append("Party: ").append(c.getPartyID()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }

        return list;
    }

    private ElectionBean getElectionById(String id) {
        for (ElectionBean e : electionArray)
            if (e.getElectionID().equals(id))
                return e;
        return null;
    }

   
    @Override
    public ArrayList<CandidateBean> viewCandidateDetailsByParty(String partyId) {

        ArrayList<CandidateBean> list = new ArrayList<>();

        for (CandidateBean c : candidateArray) {
            if (c.getPartyID().equalsIgnoreCase(partyId)) {
                list.add(c);
            }
        }

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No Candidates Found for Party ID: " + partyId);
        } else {
            StringBuilder sb = new StringBuilder("Candidates of Party " + partyId + ":\n\n");
            for (CandidateBean c : list) {
                sb.append("ID: ").append(c.getCandidateID()).append("\n");
                sb.append("Name: ").append(c.getName()).append("\n");
                sb.append("Election ID: ").append(c.getElectionID()).append("\n\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }

        return list;
    }

   
    @Override
    public ArrayList<ApplicationBean> viewAllAdminPendingApplications() {
        return applicationArray;
    }

    
    @Override
    public boolean forwardVoterIDRequest(String userId) {
        JOptionPane.showMessageDialog(null, "Voter ID request forwarded for: " + userId);
        return true;
    }

   
    @Override
    public Map<String, Integer> approveElectionResults(String electionId) {

        Map<String, Integer> results = new HashMap<>();

        for (CandidateBean c : candidateArray) {
            if (c.getElectionID().equals(electionId)) {
                int votes = (int)(Math.random() * 5000);  // dummy value
                results.put(c.getName(), votes);
            }
        }

        JOptionPane.showMessageDialog(null, "Election Results Approved for Election: " + electionId);

        return results;
    }
}