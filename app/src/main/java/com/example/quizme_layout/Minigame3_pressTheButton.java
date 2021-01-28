package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Minigame3_pressTheButton {

    // Variablen festlegen
    private final Button klickMich_b;
    private final TextView klickZaehler_tv;
    private final TextView klickMax_tv;

    // Klickanzahl fängt bei 0 an
    int klickAnzahl = 0;

    public Minigame3_pressTheButton(Button button, TextView view, TextView v){
        klickMich_b = button;
        klickZaehler_tv = view;
        klickMax_tv = v;

        klickMich_b.setVisibility(View.VISIBLE);
        klickZaehler_tv.setVisibility(View.VISIBLE);
        klickMax_tv.setVisibility(View.VISIBLE);
    }

    public void clickMe(){

        /*
         * onClick für die Klicks
         */
        klickMich_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                klickAnzahl++;

                // maximale Anzahl der Klicks festlegen -> max. 15
                if (klickAnzahl == 15) {
                    klickMich_b.setEnabled(false);

                    klickMich_b.setVisibility(View.INVISIBLE);
                    klickZaehler_tv.setVisibility(View.INVISIBLE);
                    klickMax_tv.setVisibility(View.INVISIBLE);

                    checkAll();

                }

                klickZaehler_tv.setText("Klicks: " + klickAnzahl);
            }
        });

    }

    /*
     * hier wird überprüft, ob alle Buttons sich im richtigen Zustand befinden
     * Wenn ja, werden die Buttons unsichtbar, damit der Hinweis gelesen werden kann
     */
    private void checkAll(){
        if(klickMich_b.isEnabled()==false && klickZaehler_tv.isEnabled()==false && klickMax_tv.isEnabled()==false) {
            klickMich_b.setVisibility(View.GONE);
            klickZaehler_tv.setVisibility(View.GONE);
            klickMax_tv.setVisibility(View.GONE);
        }
    }
}