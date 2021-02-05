package com.example.quizme_layout;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Minigame3_PressTheButton {

    // Variablen festlegen
   private Button klickMich_b;
   private TextView klickZaehler_tv;
   private TextView klickMax_tv;

    // Klickanzahl wird auf 0 gesetzt -> Anfangsstatus
    int klickAnzahl = 0;


    /**
     * Konstruktor, der die Views der Activity zuweist
     * @param activity Activity -> Quiz
     */
    public Minigame3_PressTheButton(Activity activity){
        klickMich_b = activity.findViewById(R.id.klickMich);
        klickZaehler_tv = activity.findViewById(R.id.klickZaehler);
        klickMax_tv = activity.findViewById(R.id.klickMax);

//        klickMich_b.setVisibility(View.VISIBLE);
//        klickZaehler_tv.setVisibility(View.VISIBLE);
//        klickMax_tv.setVisibility(View.VISIBLE);

        reset();

    }

    public void clickMe() {

        /*
         * onClickListener für die Klicks
         */
        klickMich_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                klickAnzahl++;

                /*
                 * maximale Anzahl der Klicks festlegen -> max. 15
                 * Spieler kann 15x auf den Button klicken
                 */

                if (klickAnzahl == 15) {

                    // Button blockieren
                    klickMich_b.setEnabled(false);

//                    klickMich_b.setVisibility(View.INVISIBLE);
//                    klickZaehler_tv.setVisibility(View.INVISIBLE);
//                    klickMax_tv.setVisibility(View.INVISIBLE);

                    // Views überprüfen
                    checkAll();

                }

                // klickAnzahl wird dem Textview zugewiesen
                klickZaehler_tv.setText("Klicks: " + klickAnzahl);
            }
        });

    }

    /*
     * hier wird überprüft, ob alle Buttons sich im richtigen Zustand befinden
     * Wenn ja, werden die Buttons unsichtbar, damit der Hinweis gelesen werden kann
     */
    public void checkAll() {

        // wenn alle Views blockiert sind
        if(klickMich_b.isEnabled()==false && klickZaehler_tv.isEnabled()==false  && klickMax_tv.isEnabled()==false ) {

                // unsichtbar machen
                invisible();
            }
            // zurücksetzen des Buttons
            reset();
        }


    // zurücksetzen und entblockieren der Views
    public void reset(){

        klickMich_b=null;
        klickMax_tv=null;
        klickMax_tv=null;

        // entblockieren
        klickMich_b.setEnabled(true);
        klickZaehler_tv.setEnabled(true);
        klickMax_tv.setEnabled(true);

    }

    // Views werden sichtbar
    public void visible(){
        klickMich_b.setVisibility(View.VISIBLE);
        klickZaehler_tv.setVisibility(View.VISIBLE);
        klickMax_tv.setVisibility(View.VISIBLE);
    }

    // Views werden unsichtbar
    public void invisible(){
        klickMich_b.setVisibility(View.INVISIBLE);
        klickZaehler_tv.setVisibility(View.INVISIBLE);
        klickMax_tv.setVisibility(View.INVISIBLE);
    }

} // end class Minigame3_PressTheButton










