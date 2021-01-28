package com.example.quizme_layout;
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
     * @param _swipeButton1
     * @param _swipeButton2     die benötigten SwipeButtons werden zugewiesen
     * @param _swipeButton3
     */
    Minigame1(SwipeButton _swipeButton1, SwipeButton _swipeButton2, SwipeButton _swipeButton3 ){

        //zuweisen der Buttons
        swipeButton1 = _swipeButton1;
        swipeButton2 = _swipeButton2;
        swipeButton3 = _swipeButton3;

        //das sichtbar machen der Buttons
        swipeButton1.setVisibility(View.VISIBLE);
        swipeButton2.setVisibility(View.VISIBLE);
        swipeButton3.setVisibility(View.VISIBLE);
        //reset();
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
            swipeButton1.setVisibility(View.GONE);
            swipeButton2.setVisibility(View.GONE);
            swipeButton3.setVisibility(View.GONE);
        }
    }

    public void reset(){
        //swipeButton1 = null;
        //swipeButton2 = null;
        //swipeButton3 = null;
        swipeButton1.toggleState();
        swipeButton2.toggleState();
        swipeButton3.toggleState();

        //swipeButton2.setActivated(false);
        //swipeButton3.setActivated(false);

    }

}
