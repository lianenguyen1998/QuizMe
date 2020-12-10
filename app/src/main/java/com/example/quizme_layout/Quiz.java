package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Datenbank.DatabaseHelper;
import com.example.Datenbank.QuizMeModel;

import java.util.Collections;
import java.util.List;

public class Quiz extends AppCompatActivity {

    TextView level;
    TextView leben;
    TextView zeit;
    TextView logo;
    private TextView frage;
    private Button hinweis;
    //Button[] btn = new Button[4];
    //int[] btn_id = { R.id.choice1, R.id.choice2, R.id.choice3, R.id.choice4 };
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private Button currentButton;
    private Button quit;

    private ColorStateList textColorDefault;

    private List<QuizMeModel> fragenliste;
    private int questionCounter;
    private int questionCountTotal;
    private boolean answered;
    private QuizMeModel currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        frage = findViewById(R.id.question);
        hinweis = findViewById(R.id.hinweis);

        /*for (int i = 0; i < btn.length; i++) {
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setOnClickListener(new View.OnClickListener() {
               @Override
                public void onClick(View v) {
                    checkAnswer();
                }
            });
        }*/

        option1 = findViewById(R.id.choice1);
        option2 = findViewById(R.id.choice2);
        option3 = findViewById(R.id.choice3);
        option4 = findViewById(R.id.choice4);
        textColorDefault = option1.getTextColors();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        fragenliste = dbHelper.getAllQuestions();
        questionCountTotal = fragenliste.size();
        Collections.shuffle(fragenliste);

        showNextQuestion();

        option1.setOnClickListener(answer);
        option2.setOnClickListener(answer);
        option3.setOnClickListener(answer);
        option4.setOnClickListener(answer);

        hinweis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop up hinweis
                //erstmal ein Toast?
                Toast.makeText(getApplicationContext(), currentQuestion.getHinweis() ,Toast.LENGTH_SHORT).show();
            }
        });






       /* quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abbrechen();
            }
        });*/

    }

    private  View.OnClickListener answer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkAnswer();
        }
    };

    private void showNextQuestion(){


        //option1.setTextColor(textColorDefault);
        //option2.setTextColor(textColorDefault);
        //option3.setTextColor(textColorDefault);
        //option4.setTextColor(textColorDefault);


        if(questionCounter < questionCountTotal){
            currentQuestion = fragenliste.get(questionCounter);
            frage.setText(currentQuestion.getFragen());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());
            option4.setText(currentQuestion.getOption4());

            questionCounter++;
            answered = false;

        } else {
            finishQuiz();
        }
    }

    private void finishQuiz(){
        finish();
    }

    private void checkAnswer(){
        //answered = true;
        int answerNr = 1;

            if(option1.isSelected() || option2.isSelected() || option3.isSelected() || option4.isSelected()) {

                //falsche antworten rot
                option1.setBackgroundColor(Color.parseColor("#FF6347"));
                option2.setBackgroundColor(Color.parseColor("#FF6347"));
                option3.setBackgroundColor(Color.parseColor("#FF6347"));
                option4.setBackgroundColor(Color.parseColor("#FF6347"));

                for(int i = 0; i < 5; i++) {

                    //  int answerNr = btn_id[i];

                    if (answerNr == currentQuestion.getAntwort_nr()) {
                        //wenn Antwort richtig
                        //Farbe des Buttons grün
                        switch (currentQuestion.getAntwort_nr()) {
                            case 1:
                                option1.setBackgroundColor(Color.parseColor("#98FB98"));
                                break;
                            case 2:
                                option2.setBackgroundColor(Color.parseColor("#98FB98"));
                                break;
                            case 3:
                                option3.setBackgroundColor(Color.parseColor("#98FB98"));
                                break;
                            case 4:
                                option4.setBackgroundColor(Color.parseColor("#98FB98"));
                                break;
                        }
                        // -> Gewonnen Pop Up
                    } else {
                        answerNr++;
                    }
                }

                //finishQuiz();
                        //leben weg
                        //keine leben mehr -> Antwort anzeigen -> Quiz beenden
                }
            }

   /* public void abbrechen(){
        Intent intent = new Intent(Quiz.this, Startseite.class);
        startActivity(intent);
    }*/
}