package com.example.quizme_layout;

import android.app.Activity;
import android.view.View;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;

public class Minigame1_SwipeButtons {

    //Die zum spielen benötigten Swipebuttons
    SwipeButton swipeButton1;
    SwipeButton swipeButton2;
    SwipeButton swipeButton3;

    //gibt an, ob die Buttons geswiped worden sind
    boolean swiped1 = false;
    boolean swiped2 = false;
    boolean swiped3 = false;

    /***
     * Konstruktor, der die Buttons der Activity zuweist
     * @param _activity Activity (Quiz)
     */
    Minigame1_SwipeButtons(Activity _activity){

        swipeButton1 = (SwipeButton) _activity.findViewById(R.id.swipeButton1);
        swipeButton2 = (SwipeButton) _activity.findViewById(R.id.swipeButton2);
        swipeButton3 = (SwipeButton) _activity.findViewById(R.id.swipeButton3);

        //setzt alle Buttons in den Startzustand
        reset();
    }

    /***
     * Es wird überprüft, ob die Buttons genutzt worden sind, wenn ja werden die Buttons blockiert,
     * so dass man sie nur einmal swipen muss
     */
    public void spielen()
    {
        swipeButton1.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                //Button blockieren
                swipeButton1.setEnabled(false);

                //alle überprüfen
                checkAll();
                swiped1 = true;
            }
        });

        swipeButton2.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                //Button blockieren
                swipeButton2.setEnabled(false);

                //alle überprüfen
                checkAll();
                swiped2 = true;
            }
        });

        swipeButton3.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                //Button blockieren
                swipeButton3.setEnabled(false);

                //alle überprüfen
                checkAll();
                swiped3 = true;
            }
        });
    }

    /***
     * hier wird überprüft, ob alle Buttons sich im richtigen Zustand befinden
     * Wenn ja, werden die Buttons unsichtbar, damit der Hinweis gelesen werden kann
     */
    public void checkAll(){

        //wenn alle Buttons blockiert sind
        if(swipeButton1.isEnabled()==false && swipeButton2.isEnabled()==false  && swipeButton3.isEnabled()==false ) {

            //und geswiped sind
            if (swiped1 == true && swiped2 == true && swiped3==true){
                //unsichtbar machen
                invisible();
            }
            //zurücksetzen der Buttons
            reset();
        }
    }

    /***
     * zurücksetzen und entblockieren der Buttons
     */
    public void reset(){
            //zurücksetzen
            swipeButton1.toggleState();
            swipeButton2.toggleState();
            swipeButton3.toggleState();

            //entblockieren
            swipeButton1.setEnabled(true);
            swipeButton2.setEnabled(true);
            swipeButton3.setEnabled(true);
    }

    /***
     * die Buttons werden sichtbar
     */
    public void visible(){
        swipeButton1.setVisibility(View.VISIBLE);
        swipeButton2.setVisibility(View.VISIBLE);
        swipeButton3.setVisibility(View.VISIBLE);
    }

    /***
     * die Buttons werden unsichtbar
     */
    public void invisible(){
        swipeButton1.setVisibility(View.INVISIBLE);
        swipeButton2.setVisibility(View.INVISIBLE);
        swipeButton3.setVisibility(View.INVISIBLE);
    }
}
