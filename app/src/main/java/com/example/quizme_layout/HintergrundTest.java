package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

public class HintergrundTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hintergrund_test);
        //From class android.graphics.drawable.DrawableContainer
        //duration  = Amount of time (in milliseconds) to display this frame.
        ConstraintLayout constraintLayout = findViewById(R.id.hintergrund_id);
        AnimationDrawable  animationDrawable= (AnimationDrawable) constraintLayout.getBackground();

        //Change the global fade duration when a new drawable is entering the scene.
        animationDrawable.setEnterFadeDuration(4000);

        //Change the global fade duration when a new drawable is leaving the scene.
        animationDrawable.setExitFadeDuration(4000);

        animationDrawable.start();
    }
}