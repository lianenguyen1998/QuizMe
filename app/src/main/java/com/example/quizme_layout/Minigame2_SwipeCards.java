package com.example.quizme_layout;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class Minigame2_SwipeCards {

    Activity activity;
    ArrayList<String> cards = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    SwipeFlingAdapterView swipeAdapter;


    public Minigame2_SwipeCards(Activity _activity)
    {
        activity = _activity;
        reset();
        //add();
        createCards();
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

    public void reset() throws NullPointerException{
        try {
            if (swipeAdapter != null) {
                swipeAdapter.removeAllViewsInLayout();
                arrayAdapter.notifyDataSetChanged();
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void createCards(){
        add();
        arrayAdapter = new ArrayAdapter<>(activity, R.layout.karten, R.id.textview_kartenzahl, cards);
        swipeAdapter  = (SwipeFlingAdapterView) activity.findViewById(R.id.cards);
        swipeAdapter.setAdapter(arrayAdapter);

        swipeAdapter.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                cards.remove(0);
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


            }

            @Override
            public void onScroll(float v) {

            }
        });
    }
}
