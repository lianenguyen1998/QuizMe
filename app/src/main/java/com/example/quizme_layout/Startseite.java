package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Startseite extends AppCompatActivity {

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





}