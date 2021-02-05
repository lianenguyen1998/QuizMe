package com.example.quizme_layout;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;

/***
 * In dieser Klasse wird die Hintergrundmusik  und der Buttonsound geregelt
 */
public class Musik {

    private MediaPlayer musik;

    /***
     * Konstruktor für die Hintergrundmusik
     * @param _context für die Activity in welcher die Musik laufen soll
     */
    Musik(Context _context) {
        musik = MediaPlayer.create(_context, R.raw.musik);
        startMusik();
    }

    /***
     * Konstruktor für den Buttonsound
     * @param _context für die Activity in welcher die Musik laufen soll
     */
    Musik(Context _context, boolean _buttonSound){
        if(_buttonSound){
            musik = MediaPlayer.create(_context, R.raw.button_klick1);
        }
    }

    /***
     * zum starten des Buttonsound
     */
    public void starteButtonsound(){
        try {
            this.musik.start();
        }catch (Resources.NotFoundException e){
            e.printStackTrace();
        }
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
     * zum Beenden der Hintergrundmusik
     */
    public void endMusik(){
        try {
            this.musik.release();
        } catch(IllegalStateException e){
            e.printStackTrace();
        }
    }

    /***
     * zum Pausieren der Musik
     */
    private void pauseMusik(){
        try {
            this.musik.pause();
        } catch(IllegalStateException e){
            e.printStackTrace();
        }
    }

} //end class


