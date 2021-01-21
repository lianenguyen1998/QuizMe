package com.example.quizme_layout;

import android.media.MediaPlayer;

public class Sound {

    /*
    Bei release idt der Mediaplayer im End State, sollte aufgerufen werden, wenn der Musikplayer nicht mehr benötogt wird
    bei start() wird gestartet isPlaying() = true
    bei pause() wird gestoppt und die Position wird sich gemerkt isPlaying()=false, bei einem start() wird jetzt alles auf Anfang zurückgesetzt
    getCurrentPosition(),
    seekTo(long, int)
    After reset(), the object is like being just created.

    setOnErrorListener
any
{}
This method can be called in any state and calling it does not change the object state.

    If the looping mode was being set to true with setLooping(boolean), the MediaPlayer object shall remain in the Started state.
     */
    MediaPlayer sound;
    Sound(MediaPlayer _sound){
        this.sound = _sound;
        this.sound.setLooping(true);
        this.sound.start();
    }
    public void endSound(){
        this.sound.release();
    }

}
