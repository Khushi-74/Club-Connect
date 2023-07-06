package com.example.clubconnect;

public class user {
    String clubName;
    String leader;
    String description;
   public user(){

   }
    public user(String clubName, String leader, String description) {
        this.clubName = clubName;
        this.leader = leader;
        this.description = description;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
