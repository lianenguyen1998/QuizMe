package com.example.Datenbank;

import android.content.Context;
import java.util.List;

/**
 * Erstellen der Highscoreliste
 */
public class HighscoreTableHelper {

    Context c;

    //Tabellenkopf
    private final String[] header = {"Rang", "Name", "Level", "Zeit"};

    public HighscoreTableHelper(Context c){
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

        int rang = 1;

        String[][] spieler = new String[spielerListe.size()][4];

        for(int i = 0; i < spielerListe.size(); i++){

            //Alle Spieler aus der Liste holen
            currentmodel = spielerListe.get(i);

            //rang
            spieler[i][0] = String.valueOf(rang);
            //Name des Spielers
            spieler[i][1] = currentmodel.getUsername();
            //Levelanzahl bei dem der Spieler aufgehört hat
            spieler[i][2] = String.valueOf(currentmodel.getLevelanzahl());
            //Zeit bei dem der Spieler aufgehört hat
            spieler[i][3] = String.valueOf(currentmodel.getUserzeit());

            //neuer Rang, wenn neuer Spieler eingefügt wird
            ++rang;
        }
    return spieler;
    }

}
