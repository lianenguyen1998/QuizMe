package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
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
    Button startAnim, highscorelisteAnim, ueberQuizmeAnim;
    ImageButton einstellungAnim;

    // ---- QuizButton
    private Button start;

    // ---- HighscorelisteButton
    private Button highscoreliste;

    // ---- InfotextButton
    private Button ueberQuizme;

    // ---- EinstellungsButton
    private ImageButton einstellung;

    private Musik musik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startseite);

        //HintergrundAnimation
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(Startseite.this,3000);

        //Hintergrundmusik (wird gesartet)
        musik = new Musik(this);


        //Sound für den Buttonklick
        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.button_klick1);

        // ---- Splash Screen Animation
        obenAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        untenAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        willkommenAnim = findViewById(R.id.willkommen);
        startAnim = findViewById(R.id.start);
        highscorelisteAnim = findViewById(R.id.highscoreliste);
        ueberQuizmeAnim = findViewById(R.id.infotext);
        logoAnim = findViewById(R.id.logo4);
        einstellungAnim = findViewById(R.id.einstellung);

        // Übergabe der Animationen
        willkommenAnim.setAnimation(obenAnim);
        startAnim.setAnimation(untenAnim);
        highscorelisteAnim.setAnimation(untenAnim);
        ueberQuizmeAnim.setAnimation(untenAnim);
        logoAnim.setAnimation(untenAnim);
        einstellungAnim.setAnimation(obenAnim);

        start = (Button) findViewById(R.id.start);
        highscoreliste = (Button) findViewById(R.id.highscoreliste);
        ueberQuizme = (Button) findViewById(R.id.infotext);
        einstellung = (ImageButton) findViewById(R.id.einstellung);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                openQuiz();
                musik.endMusik();
            }
        });

        highscoreliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                openHighscoreliste();
                musik.endMusik();
            }
        });

        ueberQuizme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mediaPlayer.start();
                openInfotext();
                musik.endMusik();
                    //openBackgroundTest();
            }
        });

        einstellung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                openEinstellung();
                musik.endMusik();
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

    // ---- bringt uns zu der Einstellung
    public void openEinstellung() {
        Intent intent = new Intent(this, Einstellung.class);
        startActivity(intent);
    }

    // ---------------------------------------------------------------------------------------- //

    /**
     * Methode um den zurück-Button zu steuern
     *      -die Musik wird gestoppt
     *      -zurück zur Startseite
     */
    @Override
    public void onBackPressed() {
        //Musik stoppen
        musik.endMusik();

        //zur Startseite
        Intent intent = new Intent(this, Startseite.class);
        startActivity(intent);
        super.onBackPressed();
    }
}