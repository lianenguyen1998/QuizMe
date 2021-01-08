package com.example.Datenbank;

public class HighscoreModel {


    public String username;
    public long userzeit;
    public int levelanzahl;

    public HighscoreModel(){}

    public HighscoreModel(String _username, long _userzeit, int _levelanzahl){
        this.username = _username;
        this.userzeit = _userzeit;
        this.levelanzahl = _levelanzahl;
    }

    public String toString(){
        return "Dein Name ist " + username + ". Deine Zeit ist " + userzeit + ". Du bist zu Level " + levelanzahl + " gekommen.";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserzeit() {
        return userzeit;
    }

    public void setUserzeit(long userzeit) {
        this.userzeit = userzeit;
    }

    public int getLevelanzahl() {
        return levelanzahl;
    }

    public void setLevelanzahl(int levelanzahl) {
        this.levelanzahl = levelanzahl;
    }

}
