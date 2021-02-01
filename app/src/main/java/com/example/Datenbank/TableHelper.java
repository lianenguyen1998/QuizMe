package com.example.Datenbank;

import android.content.Context;

import com.example.quizme_layout.Quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Erstellen der Highscoreliste
 */
public class TableHelper{

    Context c;

    //Tabellenkopf
    private String[] header = {"Rang", "Name", "Level", "Zeit"};
    private String [][] spieler;

    public TableHelper(Context c){
        this.c = c;
    }

    /**
     *
     * @return gibt Tabellenkopf aus
     */
    public String[] getHeader(){
        return header;
    }

    /**
     * Werte des Spielers werden zusammengebaut
     * @return Werte der Spieler
     */
    public String[][] getSpieler(){

        //Highscorewerte aus der Datenbank
        List<HighscoreModel> spielerListe = new DatabaseHighscorelist(c).getHighscorelist();
        //aktueller Spieler
        HighscoreModel currentmodel;

        int increment = 1;

        this.spieler = new String[spielerListe.size()][4];

        for(int i = 0; i < spielerListe.size(); i++){

            //Alle Spieler aus der Liste holen
            currentmodel = spielerListe.get(i);

            //rang
            spieler[i][0] = String.valueOf(increment);
            //Name des Spielers
            spieler[i][1] = currentmodel.getUsername();
            //Levelanzahl bei dem der Spieler aufgehört hat
            spieler[i][2] = String.valueOf(currentmodel.getLevelanzahl());
            //Zeit bei dem der Spieler aufgehört hat
            spieler[i][3] = String.valueOf(currentmodel.getUserzeit());

            //neuer Rang, wenn neuer Spieler eingefügt wird
            ++increment;
        }
    return spieler;
    }

}
