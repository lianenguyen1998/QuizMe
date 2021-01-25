package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class pressTheButton extends AppCompatActivity {

    // Variablen festlegen
    private TextView klickZaehler_tv;
    private Button klickMich_b;

    // Klickanzahl fängt bei 0 an
    int klickAnzahl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press_the_button);


        /**
         Die oben stehende Objekte werden initialisiert
         */
        klickZaehler_tv = findViewById(R.id.klickZaehler);

        klickMich_b =  findViewById(R.id.klickMich);


        /**
         * onClick für die Klicks
         */
        klickMich_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                klickAnzahl++;

                // maximale Anzahl der Klicks festlegen -> max. 15
                if (klickAnzahl == 15) {
                    klickMich_b.setEnabled(false);
                }

                klickZaehler_tv.setText("Klicks: " + klickAnzahl);

            }


        });

    }
}