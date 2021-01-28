package com.example.quizme_layout;

import android.graphics.drawable.AnimationDrawable;

public class HintergrundAnimation {

    //dauer
    int duration;
    AnimationDrawable animation;

    HintergrundAnimation(AnimationDrawable _animation, int _duration){
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
