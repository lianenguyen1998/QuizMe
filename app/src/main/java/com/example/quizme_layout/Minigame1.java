package com.example.quizme_layout;

import android.app.Activity;
import android.view.View;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;

public class Minigame1 {

    //Die zum spielen benötigten Swipebuttons
    SwipeButton swipeButton1;
    SwipeButton swipeButton2;
    SwipeButton swipeButton3;

    /***
     * Konstruktor
     */
    Minigame1(Activity _activity){

        swipeButton1 = (SwipeButton) _activity.findViewById(R.id.swipeButton1);
        swipeButton2 = (SwipeButton) _activity.findViewById(R.id.swipeButton2);
        swipeButton3 = (SwipeButton) _activity.findViewById(R.id.swipeButton3);

        reset();

        //swipeButton1.setVisibility(View.VISIBLE);
        //swipeButton2.setVisibility(View.VISIBLE);
        //swipeButton3.setVisibility(View.VISIBLE);

        spielen();
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
                swipeButton1.setEnabled(false);
                checkAll();
            }
        });

        swipeButton2.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                swipeButton2.setEnabled(false);
                checkAll();
            }
        });

        swipeButton3.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                swipeButton3.setEnabled(false);
                checkAll();
            }
        });
    }

    /***
     * hier wird überprüft, ob alle Buttons sich im richtigen Zustand befinden
     * Wenn ja, werden die Buttons unsichtbar, damit der Hinweis gelesen werden kann
     */
    public void checkAll(){

       // if(swipeButton1.isActivated()==true && swipeButton2.isActivated()==true && swipeButton3.isActivated()==true) {
        if(swipeButton1.isEnabled()==false && swipeButton2.isEnabled()==false  && swipeButton3.isEnabled()==false ) {

            swipeButton1.setVisibility(View.INVISIBLE);
            swipeButton2.setVisibility(View.INVISIBLE);
            swipeButton3.setVisibility(View.INVISIBLE);
            //reset();
        }
    }

    public void reset(){

            swipeButton1.toggleState();
            swipeButton2.toggleState();
            swipeButton3.toggleState();

            swipeButton1.setEnabled(true);
            swipeButton2.setEnabled(true);
            swipeButton3.setEnabled(true);


        //swipeButton2.setActivated(false);
        //swipeButton3.setActivated(false);
    }

}
