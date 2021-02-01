package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class Einstellung extends AppCompatActivity {

    //Hintergrund musik
     private Musik musik;

    private Switch musik_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.einstellung);

        //Hintergrundanimation
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(Einstellung.this,4000);

        //Hintergrundmusik (wird gestartet)
        musik = new Musik(this);

        musik_ = (Switch) findViewById(R.id.musik);

        /***
        mediaPlayer.pause();
        musik_.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            mediaPlayer.start();

                        } else {

                            mediaPlayer.pause();
                        }
                    }
                    }
        );
}   ***/
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