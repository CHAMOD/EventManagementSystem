package com.event.model;


public class Team {

    private int idTeam;
    private String teamName;
    private int teamLead;
    private String teamLeadName;

    public Team() {

    }

    public Team(int idTeam, String teamName, int teamLead, String teamLeadName) {

        this.idTeam = idTeam;
        this.teamName = teamName;
        this.teamLead = teamLead;
        this.teamLeadName = teamLeadName;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(int teamLead) {
        this.teamLead = teamLead;
    }

    public String getTeamLeadName() {
        return teamLeadName;
    }

    public void setTeamLeadName(String teamLeadName) {
        this.teamLeadName = teamLeadName;
    }
}


