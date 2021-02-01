package com.example.quizme_layout;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Infotext extends AppCompatActivity {

    //Hintergundmusik
    private Musik musik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infotext);

        //Hintergrundmusik (wird gestartet)
        musik = new Musik(this);

        //Hintergrundanimation
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(Infotext.this, 4000);
    }

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