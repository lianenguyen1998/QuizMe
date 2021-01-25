package com.example.quizme_layout;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

public class Minigame2_SwipeCards extends AppCompatActivity {

    Context context;
    ArrayList<String> cards;
    ArrayAdapter arrayAdapter;
    SwipeFlingAdapterView swipeAdapter;

    public Minigame2_SwipeCards(Context _context, SwipeFlingAdapterView adapter, ArrayList cards, ArrayAdapter arrayAdapter)
    { this.context = _context;
      this.swipeAdapter = adapter;
      this.cards = cards;
      this.arrayAdapter = arrayAdapter;
      //adapter.setVisibility(View.VISIBLE);
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
