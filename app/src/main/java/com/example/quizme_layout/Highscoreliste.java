package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import de.codecrafters.tableview.*;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import com.example.Datenbank.HighscoreTableHelper;

public class Highscoreliste extends AppCompatActivity {

    // ---- StartseiteButton
    private Button zurückzurStartseite;

    // ---- neuesSpielButton
    private Button neuesSpiel;

    //Tableview -- um Highscoreliste auszugeben
    TableView<String[]> table;
    HighscoreTableHelper highscoreTableHelper;

    //Hintergrundmusik
    private Musik musik;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscoreliste);

        //Hintergrundanimation
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(Highscoreliste.this,4000);

        //Hintergrundmusik (wird gestartet)
        musik = new Musik(this);

        zurückzurStartseite = (Button) findViewById(R.id.zurückzurStartseite);
        neuesSpiel = (Button) findViewById(R.id.neuesSpiel);


        zurückzurStartseite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openzurückzurStartseite();
                musik.endMusik();
            }
        });

        neuesSpiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openweiterSpielen();
                musik.endMusik();
            }
        });


        //Tableview um Highscorliste auszugeben

        highscoreTableHelper = new HighscoreTableHelper(this);
        table = (TableView<String[]>) findViewById(R.id.highscoreview);
        // Vier Werte für den Header
        table.setColumnCount(4);
        //Farbe des Headers
        table.setHeaderBackgroundColor(Color.parseColor("#D290FFE8"));
        //Header ausgeben
        table.setHeaderAdapter(new SimpleTableHeaderAdapter(this, highscoreTableHelper.getHeader()));
        //Tabelle mit Spielern ausgeben
        table.setDataAdapter(new SimpleTableDataAdapter(this, highscoreTableHelper.getSpieler()));



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