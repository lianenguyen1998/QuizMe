package com.example.Datenbank;

/**
 * Modelklasse für Highscorelistenwerte
 */
public class HighscoreModel {

    public String username;
    public String userzeit;
    public int levelanzahl;

    public HighscoreModel(){}

    public HighscoreModel(String _username, String _userzeit, int _levelanzahl){
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

    public String getUserzeit() {
        return userzeit;
    }

    public void setUserzeit(String userzeit) {
        this.userzeit = userzeit;
    }

    public int getLevelanzahl() {
        return levelanzahl;
    }

    public void setLevelanzahl(int levelanzahl) {
        this.levelanzahl = levelanzahl;
    }

}
