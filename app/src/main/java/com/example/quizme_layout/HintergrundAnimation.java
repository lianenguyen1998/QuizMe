package com.example.quizme_layout;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.widget.RelativeLayout;

/***
 * in dieser Klasse wird die Hintergrundanimtaion erstellt und gestartet
 */
public class HintergrundAnimation {

    //dauer
    int duration;
    AnimationDrawable animation;

    /***
     * Konstruktor, startet auch direkt die Animation
     *
     * @param _activity die Activity in der die Hintergrundanimation laufen soll
     * @param _duration die Dauer zwischen den einzelnen items der Animationsliste
     */
    HintergrundAnimation(Activity _activity, int _duration){

        //Zuweisen des Hintergrunds in der jeweiligen Activity
        //die id muss in jeder Activity gleich sein!
        RelativeLayout constraintLayout = _activity.findViewById(R.id.hintergrund_Animation);
        animation= (AnimationDrawable) constraintLayout.getBackground();

        this.duration = _duration;

        //Die Dauer wird geändert, wenn das neue Drawable eintrifft
        animation.setEnterFadeDuration(duration);

        //Die Dauer wird geändert, wenn das Drawable verschwindet
        animation.setExitFadeDuration(duration);

        //das Starten der Hintergrundanimation
        animation.start();
    }
}