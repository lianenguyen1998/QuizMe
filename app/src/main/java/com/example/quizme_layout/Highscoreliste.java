package com.example.quizme_layout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.Toast;

import de.codecrafters.tableview.*;
import de.codecrafters.tableview.listeners.OnScrollListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

import com.example.Datenbank.DatabaseHighscorelist;
import com.example.Datenbank.HighscoreModel;
import com.example.Datenbank.TableHelper;

import java.util.ArrayList;
import java.util.List;
import android.widget.RelativeLayout;

public class Highscoreliste extends AppCompatActivity {

    // ---- StartseiteButton
    private Button zurückzurStartseite;

    // ---- neuesSpielButton
    private Button neuesSpiel;

    //Tableview
    TableView<String[]> table;
    TableHelper tableHelper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscoreliste);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.musik);
        Musik musik = new Musik(mediaPlayer);

        backgroundAnimation();

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


        tableHelper = new TableHelper(this);
        table = (TableView<String[]>) findViewById(R.id.highscoreview);
        table.setColumnCount(4);
        table.setHeaderBackgroundColor(Color.parseColor("#D27FFFE5"));
        table.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tableHelper.getHeader()));
        table.setDataAdapter(new SimpleTableDataAdapter(this, tableHelper.getSpieler()));



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
        RelativeLayout constraintLayout = findViewById(R.id.hintergrundListe_id);

        //der Hintergrund beinhaltet die Animationsliste (siehe quiz.xml),diese muss als Variable definiert werden, um sie starten zu können
        AnimationDrawable animation= (AnimationDrawable) constraintLayout.getBackground();
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(animation, 4000);
    }
}