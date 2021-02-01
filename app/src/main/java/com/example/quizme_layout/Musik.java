package com.example.quizme_layout;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;

/***
 * In dieser Klasse wird die Hintergrundmusik geregelt
 */
public class Musik {

    private MediaPlayer musik;

    Musik(Context _context) {
        musik = MediaPlayer.create(_context, R.raw.musik);
        startMusik();
    }

    /***
     * zum starten der Musik, wenn sie abläuft fängt sie automatisch von vorne an
     */
    public void startMusik(){
        try {
            this.musik.setLooping(true);
            this.musik.start();

        } catch(Resources.NotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * zum beenden der Hintergrundmusik
     */
    public void endMusik(){
        try {
            this.musik.release();
        } catch(IllegalStateException e){
            e.printStackTrace();
        }
    }

    /***
     * zum pausieren der Musik
     */
    public void pauseMusik(){
        try {
            this.musik.pause();
        } catch(IllegalStateException e){
            e.printStackTrace();
        }
    }
} //end class
// ------------------------------------------------------------


