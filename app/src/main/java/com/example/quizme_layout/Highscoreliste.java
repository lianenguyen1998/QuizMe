package com.example.quizme_layout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.Datenbank.DatabaseHighscorelist;
import com.example.Datenbank.HighscoreModel;

import java.util.ArrayList;
import java.util.List;

public class Highscoreliste extends AppCompatActivity {

    // ---- StartseiteButton
    private Button zurückzurStartseite;

    // ---- WeiterSpielenButton
    private Button weiterSpielen;

    // Datenbank Variablen
    private List<HighscoreModel> highscoreliste;
    private HighscoreModel currentHighscore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscoreliste);

        //Datenbank initialisieren
        DatabaseHighscorelist dbHelper = new DatabaseHighscorelist(Highscoreliste.this);


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

}