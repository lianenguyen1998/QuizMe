package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Minigame3_pressTheButton extends AppCompatActivity {

    // Variablen festlegen
    private final TextView klickZaehler_tv;
    private final Button klickMich_b;

    // Klickanzahl fängt bei 0 an
    int klickAnzahl = 0;

    public Minigame3_pressTheButton(Button button, TextView view){
        klickMich_b = button;
        klickZaehler_tv = view;
        klickMich_b.setVisibility(View.VISIBLE);
        klickZaehler_tv.setVisibility(View.VISIBLE);
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
                    klickMich_b.setVisibility(View.GONE);
                    klickZaehler_tv.setVisibility(View.GONE);
                }

                klickZaehler_tv.setText("Klicks: " + klickAnzahl);
            }
        });

    }
}