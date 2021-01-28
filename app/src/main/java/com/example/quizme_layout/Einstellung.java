package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Einstellung extends AppCompatActivity {

    // Variablen festlegen
    private TextView klickZaehler_tv;
    private Button klickMich_b;

    // Klickanzahl fängt bei 0 an
    int klickAnzahl = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_press_the_button);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.musik);
         Musik musik = new Musik(mediaPlayer);


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

    /***
     * Die Hintergrundanimation, welche aus aus einer Animationsliste besteht, soll hier in ihrer Dauer
     * angepasst und gestartet werden
     */
    private void backgroundAnimation(){

        RelativeLayout constraintLayout = findViewById(R.id.hintergrundInfo_id);

        //der Hintergrund beinhaltet die Animationsliste (siehe quiz.xml),diese muss als Variable definiert werden, um sie starten zu können
        AnimationDrawable animation= (AnimationDrawable) constraintLayout.getBackground();
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(animation, 4000);
    }
}