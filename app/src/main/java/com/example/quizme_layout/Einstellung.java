package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

public class Einstellung extends AppCompatActivity {


    protected Musik app;
    private Switch musikSwitch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.einstellung);

//        app=(Musik)getApplicationContext();


//        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.musik);
//        Musik musik = new Musik(mediaPlayer);


        musikSwitch = (Switch) findViewById(R.id.musik);


//        if(app.getMusik_()){
//
//            musikSwitch.setChecked(true);
//        } else {
//
//            musikSwitch.setChecked(false);
//        }

        musikSwitch.setOnCheckedChangeListener(

                ((buttonView, isChecked) -> {

                if (isChecked) {
//                app.setMusik_(true);
                app.musikPlay();

                } else {
//                app.setMusik_(false);
                app.musikStop();
                }
                }


                )

        );


}



    /***
     * Die Hintergrundanimation, welche aus aus einer Animationsliste besteht, soll hier in ihrer Dauer
     * angepasst und gestartet werden
     */
    private void backgroundAnimation(){

        RelativeLayout constraintLayout = findViewById(R.id.hintergrundInfo_id);

        //der Hintergrund beinhaltet die Animationsliste (siehe quiz.xml),diese muss als Variable definiert werden, um sie starten zu können
        AnimationDrawable animation= (AnimationDrawable) constraintLayout.getBackground();
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(animation, 4000);
    }
}