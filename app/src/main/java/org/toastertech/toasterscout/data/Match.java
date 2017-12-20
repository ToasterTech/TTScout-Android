package org.toastertech.toasterscout.data;

public class Match {

    // Permanent
    private int matchNum;
    private int teamNum;
    private String scoutName;
    private String competitionName;

    public Match(){ // SuperCSV Writer
        matchNum = 0;
        teamNum = 0;
        scoutName = "";
        competitionName = "";
    }

    public Match(int matchNum, int teamNum, String scoutName, String competitionName) { // Human-Used
        this.matchNum = matchNum;
        this.teamNum = teamNum;
        this.scoutName = scoutName;
        this.competitionName = competitionName;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public String getScoutName() {
        return scoutName;
    }

    public void setScoutName(String scoutName) {
        this.scoutName = scoutName;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    @Override
    public String toString() {
        return "Match Number: " + matchNum + "\nTeam Number: " + teamNum + "\nScout Name: " + scoutName;
    }
}
