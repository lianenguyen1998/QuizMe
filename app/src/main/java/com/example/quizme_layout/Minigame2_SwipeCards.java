package com.example.quizme_layout;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import java.util.ArrayList;

/**
 * Swipe Cards Minigame
 */
public class Minigame2_SwipeCards {

    Activity activity;
    ArrayList<String> cards = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    SwipeFlingAdapterView swipeAdapter;
    Boolean nextQuestion;

    public Minigame2_SwipeCards(Activity _activity, Boolean nextquestion)
    {
        //activity = _activity;
        arrayAdapter = new ArrayAdapter<>(_activity, R.layout.karten, R.id.textview_kartenzahl, cards);
        swipeAdapter  = (SwipeFlingAdapterView) _activity.findViewById(R.id.cards);
        nextQuestion = nextquestion;
        //reset();
        //createCards();
    }

    //Karten zur Liste hinzufügen
    public void add(){

        cards.add("SWIPE");
        cards.add("NOCH NEUN");
        cards.add("NOCH ACHT");
        cards.add("NOCH SIEBEN");
        cards.add("NOCH SECHS");
        cards.add("NOCH FÜNF");
        cards.add("NOCH VIER");
        cards.add("NOCH DREI");
        cards.add("NOCH ZWEI");
        cards.add("NOCH EINS");
    }

    /**
     * Zurücksetzen des Spiels
     * @throws NullPointerException
     */
    public void reset() throws NullPointerException{
        try {
                swipeAdapter.removeAllViewsInLayout();
                //createCards();

        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * Spiel ausführen
     */
    public void createCards(){
        //Karten hinzufügen
        add();

        swipeAdapter.setAdapter(arrayAdapter);

        swipeAdapter.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {

            @Override
            public void removeFirstObjectInAdapter() {

                //Karten werden vom Stapel geswiped
                cards.remove(0);
                //Adapter bescheid geben
                arrayAdapter.notifyDataSetChanged();
                swipeAdapter.setVisibility(View.VISIBLE);

            }

            @Override
            public void onLeftCardExit(Object o) {


            }

            @Override
            public void onRightCardExit(Object o) {

            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                if(cards.isEmpty()){
                    reset();
                }
            }

            @Override
            public void onScroll(float v) {

            }
        });
    }
}
