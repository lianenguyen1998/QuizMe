package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Highscoreliste extends AppCompatActivity {

    // ---- StartseiteButton
    private Button zurückzurStartseite;

    // ---- WeiterSpielenButton
    private Button weiterSpielen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscoreliste);

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