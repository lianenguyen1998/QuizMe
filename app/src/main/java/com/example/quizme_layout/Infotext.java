package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class Infotext extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infotext);

        backgroundAnimation();

        ArrayList<String> card = new ArrayList<>();
        SwipeFlingAdapterView swipeAdapter = (SwipeFlingAdapterView) findViewById(R.id.cards_info);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(Infotext.this, R.layout.karten, R.id.textview_kartenzahl, card);
        SwipeCards cards = new SwipeCards(Infotext.this, swipeAdapter, card, arrayAdapter);
        //swipeAdapter.setVisibility(View.INVISIBLE);
        cards.createCards();
    }
    /***
     * Die Hintergrundanimation, welche aus aus einer Animationsliste besteht, soll hier in ihrer Dauer
     * angepasst und gestartet werden
     */
    private void backgroundAnimation(){
        //Von class android.graphics.drawable.DrawableContainer

        RelativeLayout constraintLayout = findViewById(R.id.hintergrundInfo_id);

        //der Hintergrund beinhaltet die Animationsliste (siehe quiz.xml)
        //diese muss als Variable definiert werden, um sie starten zu können
        AnimationDrawable animation= (AnimationDrawable) constraintLayout.getBackground();

        //duration ist die Dauer (in Millisekunden), um das Frame anzuzeigen
        //Die Duration wird geändert, wenn das neue Drawable eintrifft
        animation.setEnterFadeDuration(4000);

        //Die Duration wird geändert, wenn das Drawable verschwindet
        animation.setExitFadeDuration(4000);

        //das Starten der Hintergrundanimation
        animation.start();
    }
}