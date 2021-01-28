package com.example.quizme_layout;
import android.graphics.drawable.AnimationDrawable;

/***
 * in dieser Klasse wird die Hintergrundanimtaion erstellt und gestartet
 */
public class HintergrundAnimation {

    //dauer
    int duration;
    AnimationDrawable animation;

    HintergrundAnimation(AnimationDrawable _animation, int _duration){
        //setzen der Instanzvariablen
        this.animation = _animation;
        this.duration = _duration;

        //Die Dauer wird geändert, wenn das neue Drawable eintrifft
        animation.setEnterFadeDuration(duration);

        //Die Dauer wird geändert, wenn das Drawable verschwindet
        animation.setExitFadeDuration(duration);

        //das Starten der Hintergrundanimation
        animation.start();
    }
}
