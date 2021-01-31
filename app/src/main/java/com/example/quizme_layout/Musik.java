package com.example.quizme_layout;

import android.app.Application;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

public class Musik extends Application {

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
//    MediaPlayer musik;
//    Musik(MediaPlayer _sound){
//        this.musik = _sound;
//        this.musik.setLooping(true);
//        this.musik.start();
//    }
//    public void endMusik(){
//        this.musik.release();
//    }

    MediaPlayer musik;


//    public Boolean getMusik_() {
//        return musik_;
//    }
//
//    public void setMusik_(Boolean musik_) {
//        this.musik_ = musik_;
//    }
//
//    Boolean musik_;




    public void onCreate() {
        super.onCreate();

        musik = new MediaPlayer();
//        setMusik_(false);

    }

    protected void musikPlay(){

        musik.reset();
        musik.setLooping(true);

        try {
            musik.setDataSource(getApplicationContext(), Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.musik));
            musik.prepare();
            this.musik.start();
        }catch(IOException e){

            e.printStackTrace();
        }

    }

    protected void musikStop(){

        this.musik.stop();

    }


}
