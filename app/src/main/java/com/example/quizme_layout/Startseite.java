package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Startseite extends AppCompatActivity {

    // ---- Splash Screen Animation - Variablen
    Animation obenAnim, untenAnim;
    TextView willkommenAnim, logoAnim;
    Button startAnim, highscorelisteAnim;
    ImageButton infoAnim;

    // ---- QuizButton
    private Button start;

    // ---- HighscorelisteButton
    private Button highscoreliste;

    // ---- InfotextButton
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startseite);

        // ---- Splash Screen Animation
        obenAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        untenAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        // Hooks
        willkommenAnim = findViewById(R.id.willkommen);
        startAnim = findViewById(R.id.start);
        highscorelisteAnim = findViewById(R.id.highscoreliste);
        logoAnim = findViewById(R.id.logo4);
        infoAnim = findViewById(R.id.info);

        willkommenAnim.setAnimation(obenAnim);
        startAnim.setAnimation(untenAnim);
        highscorelisteAnim.setAnimation(untenAnim);
        logoAnim.setAnimation(untenAnim);
        infoAnim.setAnimation(untenAnim);

        backgroundAnimation();

        start = (Button) findViewById(R.id.start);
        highscoreliste = (Button) findViewById(R.id.highscoreliste);
        imageButton = (ImageButton) findViewById(R.id.info);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openQuiz();
            }
        });

        highscoreliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openHighscoreliste();
            }
        });

         imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openInfotext();
                    //openBackgroundTest();
            }
        });
    }

    // ---- bringt uns zum Quiz
    public void openQuiz() {
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }

    // ---- bringt uns zur Highscoreliste
    public void openHighscoreliste() {
        Intent intent = new Intent(this, Highscoreliste.class);
        startActivity(intent);
    }

    // ---- bringt uns zum Infotext
     public void openInfotext() {
        Intent intent = new Intent(this, Infotext.class);
        startActivity(intent);
    }


    // ---------------------------------------------------------------------------------------- //

    public void openBackgroundTest()
    {
        Intent intent = new Intent(this, HintergrundTest.class);
        startActivity(intent);
    }

    /***
     * Die Hintergrundanimation, welche aus aus einer Animationsliste besteht, soll hier in ihrer Dauer
     * angepasst und gestartet werden
     */
    private void backgroundAnimation(){
        //Von class android.graphics.drawable.DrawableContainer

        RelativeLayout constraintLayout = findViewById(R.id.hintergrundStart_id);

        //der Hintergrund beinhaltet die Animationsliste (siehe quiz.xml)
        //diese muss als Variable definiert werden, um sie starten zu können
        AnimationDrawable  animation= (AnimationDrawable) constraintLayout.getBackground();

        //duration ist die Dauer (in Millisekunden), um das Frame anzuzeigen
        //Die Duration wird geändert, wenn das neue Drawable eintrifft
        animation.setEnterFadeDuration(2000);

        //Die Duration wird geändert, wenn das Drawable verschwindet
        animation.setExitFadeDuration(2000);

        //das Starten der Hintergrundanimation
        animation.start();
    }

}