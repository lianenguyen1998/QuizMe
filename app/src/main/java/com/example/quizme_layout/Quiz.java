package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Datenbank.DatabaseHelper;
import com.example.Datenbank.QuizMeModel;
import com.example.quizmetime.Countdown;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Quiz extends AppCompatActivity implements Countdown {

    //TextView level;
    TextView leben;
    TextView zeit;
    TextView logo;
    private TextView frage;
    private Button hinweis;
    /*Button[] btn = new Button[4];
    int[] btn_id = { R.id.choice1, R.id.choice2, R.id.choice3, R.id.choice4 };*/
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private Button quit;

    private ColorStateList ColorDefault;

    private List<QuizMeModel> fragenliste;
    private int questionCounter;
    private int questionCountTotal;
    private boolean answered;
    private QuizMeModel currentQuestion;

    /////////////////////////////////////////
    private ColorStateList textColor_countdown;
    private CountDownTimer countdown;
    private long verbleibendeZeit;
    private TextView textview_timer;

    private int level;
    static final int MAXLEVEL = 50;
    ///////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        frage = findViewById(R.id.question);
        hinweis = findViewById(R.id.hinweis);

        /*
        for (int i = 0; i < btn.length; i++) {
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setOnClickListener(answer);
        }*/


        option1 = findViewById(R.id.choice1);
        option2 = findViewById(R.id.choice2);
        option3 = findViewById(R.id.choice3);
        option4 = findViewById(R.id.choice4);

        option1.setOnClickListener(answer);
        option2.setOnClickListener(answer);
        option3.setOnClickListener(answer);
        option4.setOnClickListener(answer);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        fragenliste = dbHelper.getAllQuestions();
        questionCountTotal = fragenliste.size();
        Collections.shuffle(fragenliste);

        showNextQuestion();


        hinweis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop up hinweis
                //erstmal ein Toast?
                Toast.makeText(getApplicationContext(), currentQuestion.getHinweis(), Toast.LENGTH_SHORT).show();
            }
        });


       /* quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abbrechen();
            }
        });*/

        ////////////////////////////////////////////////
        //Timer zuweisen
        textview_timer = findViewById(R.id.textview_timer);
        //Timer Dauer

        textColor_countdown = textview_timer.getTextColors();
        //////////////////////////////////////////////////////////

    }


    private View.OnClickListener answer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.choice1:
                    checkAnswer(1,option1);
                    break;
                case R.id.choice2:
                    checkAnswer(2,option2);
                    break;
                case R.id.choice3:
                    checkAnswer(3,option3);
                    break;
                case R.id.choice4:
                    checkAnswer(4,option4);
                    break;
            }
            /////////////////////////////////////////////////////////////
            startCountdown();
            //////////////////////////////////////////////////////
            countlevel();

        }
    };

    private void showNextQuestion() {

        if (questionCounter < questionCountTotal) {
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

    private void finishQuiz() {
        finish();
    }

    private boolean checkAnswer(int buttoncount, Button btnAnswer) {
        //////////////////
        boolean answered = false;
        ///////////////////////////

        if (btnAnswer.isPressed()) {

            //falsche antworten rot

                if (buttoncount == currentQuestion.getAntwort_nr()) {
                    //wenn Antwort richtig
                    //Farbe des Buttons grün
                    btnAnswer.setBackgroundColor(Color.parseColor("#98FB98"));
                    answered = true;
                    Toast.makeText(getApplicationContext(), "richtig", Toast.LENGTH_LONG).show();

                    // -> Gewonnen Pop Up -> next level

                } else {
                    btnAnswer.setBackgroundColor(Color.parseColor("#FF6347"));
                    //leben weg -> falsche antwort
                    //keine leben mehr -> Antwort anzeigen -> Quiz beenden
                }


            //nachdem man eine Antwort anklickt -> nicht mehr drücken
            //btn[0].setEnabled(false);
            //btn[1].setEnabled(false);
            //btn[2].setEnabled(false);
            //btn[3].setEnabled(false);

        }

        return answered;
    }

    /////////////////////////////////////////////////////////////////////////
    @Override
    public void startCountdown(){
        countdown = new CountDownTimer(COUNTDOWN_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                verbleibendeZeit = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                verbleibendeZeit = 0;
                updateCountdownText();
                // checkAnswer();
                //dort countdown.cancel();
            }
        }.start();
    }

    @Override
    public void updateCountdownText() {
        int minuten = (int) (verbleibendeZeit / 1000) / 60;
        int sekunden = (int) (verbleibendeZeit / 1000) % 60;

        String zeitformatiert = String.format(Locale.getDefault(), "\"%02d : %02d", minuten, sekunden);
        textview_timer.setText(zeitformatiert);

        if (verbleibendeZeit < 11000) {
            textview_timer.setTextColor(Color.RED);
        } else {
            textview_timer.setTextColor(textColor_countdown);
        }
    }


   /* public void abbrechen(){
        Intent intent = new Intent(Quiz.this, Startseite.class);
        startActivity(intent);
    }*/

    private void countlevel() {

        if(level>=1 && level<=MAXLEVEL ) {
            //wenn die Antwort richtig ist level hochzählen
            if (this.checkAnswer(1, option1) || this.checkAnswer(2,option2) || this.checkAnswer(3,option3) || this.checkAnswer(4,option4)) {
                this.level++;
                //Toast.makeText(getApplicationContext(), "richtig", Toast.LENGTH_LONG).show();
            }
            //wenn die Antwort falsch ist zur Highscoreliste
            else {
                //endeee
                //Toast.makeText(getApplicationContext(), "falsch", Toast.LENGTH_LONG).show();
            }
        }
    }
}