package com.example.Datenbank;

import android.content.Context;

import com.example.quizme_layout.Quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TableHelper{

    Context c;

    private String[] header = {"Rang", "Name", "Level", "Zeit"};
    private String [][] spieler;

    public TableHelper(Context c){
        this.c = c;
    }

    public String[] getHeader(){
        return header;
    }

    public String[][] getSpieler(){
        List<HighscoreModel> spielerListe = new DatabaseHighscorelist(c).getHighscorelist();
        HighscoreModel currentmodel;
        int increment = 1;

        this.spieler = new String[spielerListe.size()][4];

        for(int i = 0; i < spielerListe.size(); i++){
            currentmodel = spielerListe.get(i);
            // nur 10 Plätze
            //rang
            spieler[i][0] = String.valueOf(increment);
            spieler[i][1] = currentmodel.getUsername();
            spieler[i][2] = String.valueOf(currentmodel.getLevelanzahl());
            spieler[i][3] = String.valueOf(currentmodel.getUserzeit());
            ++increment;
        }
    return spieler;
    }

}
