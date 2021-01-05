package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Highscoreliste extends AppCompatActivity {

    // ---- StartseiteButton
    private Button zurückzurStartseite;

    // ---- WeiterSpielenButton
    private Button weiterSpielen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscoreliste);

        backgroundAnimation();

        zurückzurStartseite = (Button) findViewById(R.id.zurückzurStartseite);
        weiterSpielen = (Button) findViewById(R.id.weiterSpielen);

        zurückzurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openzurückzurStartseite();
            }
        });

        weiterSpielen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openweiterSpielen();
            }
        });
    }

    // ---- bringt uns zurück Startseite
    public void openzurückzurStartseite() {
        Intent intent = new Intent(this, Startseite.class);
        startActivity(intent);
    }

    // ---- bringt uns zurück zum Quiz
    public void openweiterSpielen() {
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }

    /***
     * Die Hintergrundanimation, welche aus aus einer Animationsliste besteht, soll hier in ihrer Dauer
     * angepasst und gestartet werden
     */
    private void backgroundAnimation(){
        //Von class android.graphics.drawable.DrawableContainer

        RelativeLayout constraintLayout = findViewById(R.id.hintergrundListe_id);

        //der Hintergrund beinhaltet die Animationsliste (siehe quiz.xml)
        //diese muss als Variable definiert werden, um sie starten zu können
        AnimationDrawable animation= (AnimationDrawable) constraintLayout.getBackground();

        //duration ist die Dauer (in Millisekunden), um das Frame anzuzeigen
        //Die Duration wird geändert, wenn das neue Drawable eintrifft
        animation.setEnterFadeDuration(4000);

        //Die Duration wird geändert, wenn das Drawable verschwindet
        animation.setExitFadeDuration(4000);

        //das Starten der Hintergrundanimation
        animation.start();
    }

}