package com.example.quizme_layout;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class Minigame2_SwipeCards extends AppCompatActivity {

    Context context;
    ArrayList<String> cards = new ArrayList<>();
   ArrayAdapter arrayAdapter;
    SwipeFlingAdapterView swipeAdapter;

    public  Minigame2_SwipeCards(){
        //leerer Konstruktor
    }

    public Minigame2_SwipeCards(Context _context, SwipeFlingAdapterView adapter, ArrayList<String> cards, ArrayAdapter<String> arrayAdapter)
    { this.context = _context;
      this.swipeAdapter = adapter;
      this.cards = cards;
      this.arrayAdapter = arrayAdapter;
      //adapter.setVisibility(View.VISIBLE);
    }

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
            if (cards != null && arrayAdapter != null) {
                cards.clear();
                arrayAdapter.clear();
                swipeAdapter.setAdapter(null);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void createCards(){


        swipeAdapter.setAdapter(arrayAdapter);
        swipeAdapter.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                cards.remove(0);
                arrayAdapter.notifyDataSetChanged();
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
