package com.example.quizme_layout;
import android.app.Activity;
import android.view.View;
import com.ebanx.swipebtn.OnStateChangeListener;
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

        swipeButton1.setVisibility(View.VISIBLE);
        swipeButton2.setVisibility(View.VISIBLE);
        swipeButton3.setVisibility(View.VISIBLE);
    }

    /***
     * Es wird überprüft, ob die Buttons genutzt worden sind, wenn ja werden die Buttons blockiert,
     * so dass man sie nur einmal swipen muss
     */
    public void spielen()
    {
        swipeButton1.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                swipeButton1.setEnabled(false);
                checkAll();
            }
        });

        swipeButton2.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                swipeButton2.setEnabled(false);
                checkAll();
            }
        });
        swipeButton3.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                swipeButton3.setEnabled(false);
                checkAll();
            }
        });
    }

    /***
     * hier wird überprüft, ob alle Buttons sich im richtigen Zustand befinden
     * Wenn ja, werden die Buttons unsichtbar, damit der Hinweis gelesen werden kann
     */
    private void checkAll(){
        if(swipeButton1.isEnabled()==false && swipeButton2.isEnabled()==false && swipeButton3.isEnabled()==false) {
            //swipeButton1.setVisibility(View.GONE);
            //swipeButton2.setVisibility(View.GONE);
            //swipeButton3.setVisibility(View.GONE);
        }
    }

    public void reset(){
        //swipeButton1 = null;
        //swipeButton2 = null;
        //swipeButton3 = null;
        swipeButton1.toggleState();
        swipeButton2.toggleState();
        swipeButton3.toggleState();

        swipeButton1.setEnabled(true);
        swipeButton2.setEnabled(true);
        swipeButton3.setEnabled(true);


        //swipeButton2.setActivated(false);
        //swipeButton3.setActivated(false);
        //Minigame1 minigame1 = new Minigame1(_swipeButton1, _swipeButton2, _swipeButton3 );
        //minigame1.spielen();
    }

}
