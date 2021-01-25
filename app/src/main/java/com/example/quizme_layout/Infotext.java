package com.example.quizme_layout;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ebanx.swipebtn.SwipeButton;


public class Infotext extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infotext);

        ///////////////////////
        //Variablen für Minigame1
        SwipeButton swipeButton1;
        SwipeButton swipeButton2;
        SwipeButton swipeButton3;

        swipeButton1 = (SwipeButton) findViewById(R.id.swipeButton1);
        swipeButton2 = (SwipeButton) findViewById(R.id.swipeButton2);
        swipeButton3 = (SwipeButton) findViewById(R.id.swipeButton3);

        swipeButton1.setVisibility(View.INVISIBLE);
        swipeButton2.setVisibility(View.INVISIBLE);
        swipeButton3.setVisibility(View.INVISIBLE);
        //WENN DAS MINISPIEL KOMMEN SOLL

        Minigame1 minigame1 = new Minigame1(swipeButton1, swipeButton2, swipeButton3);
        minigame1.spielen();
        /////////////////////////////

        backgroundAnimation();

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.musik);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();



    }
    /***
     * Die Hintergrundanimation, welche aus aus einer Animationsliste besteht, soll hier in ihrer Dauer
     * angepasst und gestartet werden
     */
    private void backgroundAnimation(){
        RelativeLayout constraintLayout = findViewById(R.id.hintergrundInfo_id);

        //der Hintergrund beinhaltet die Animationsliste (siehe quiz.xml),diese muss als Variable definiert werden, um sie starten zu können
        AnimationDrawable animation= (AnimationDrawable) constraintLayout.getBackground();
        //Die Dauer wird geändert, wenn das neue Drawable eintrifft
        animation.setEnterFadeDuration(4000);

        //Die Dauer wird geändert, wenn das Drawable verschwindet
        animation.setExitFadeDuration(4000);

        //das Starten der Hintergrundanimation
        animation.start();
    }
}