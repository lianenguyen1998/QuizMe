package com.example.quizme_layout;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


public class Infotext extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infotext);


//        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.musik);
//        Musik musik = new Musik(mediaPlayer);
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