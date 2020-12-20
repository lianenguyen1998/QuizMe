package com.example.quizme_layout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Datenbank.DatabaseHelper;
import com.example.Datenbank.QuizMeModel;
import com.example.quizmetime.Countdown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
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
    private QuizMeModel currentQuestion;

    /////////////////////////////////////////
    private ColorStateList textColor_countdown;
    private CountDownTimer countdown;
    private boolean timerRunning;
    private long verbleibendeZeit;
    private TextView textview_timer;

    private TextView textview_level;
    private int level_count = 1;
    static final int MAXLEVEL = 50;

    private TextView textview_leben3;
    private TextView textview_leben2;
    private TextView textview_leben1;
    private TextView textView_lebenAnzeige;
    private int leben_count = 3;
    final String prefNameFirstStart = "firstAppStart";
    ///////////////////////////////////////////

    //Pop-up Window Variables - Next
    private Dialog mydialog;
    private Button toNextLevel;

    //Pop-up Window Variables - Hinweis
    private Dialog dialogHinweis;
    private Button closesHinweis;
    private TextView hinweisText;

    //Pop-up Window Variables - Verloren
    private Dialog dialogLost;
    private Button closeLost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        frage = findViewById(R.id.question);
        hinweis = findViewById(R.id.hinweis);
        quit = findViewById(R.id.quit);

        //Antwortbuttons
        option1 = findViewById(R.id.choice1);
        option2 = findViewById(R.id.choice2);
        option3 = findViewById(R.id.choice3);
        option4 = findViewById(R.id.choice4);

        option1.setOnClickListener(answer);
        option2.setOnClickListener(answer);
        option3.setOnClickListener(answer);
        option4.setOnClickListener(answer);

        //Datenbank initialisieren
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        fragenliste = dbHelper.getAllQuestions();
        questionCountTotal = fragenliste.size();

        if(currentQuestion == null)
            showNextQuestion();

        Collections.shuffle(fragenliste);

        hinweis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop up hinweis
                //erstmal ein Toast?
                //Toast.makeText(getApplicationContext(), currentQuestion.getHinweis(), Toast.LENGTH_SHORT).show();
                popUpHinweis();
            }
        });


       quit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finishQuiz();
           }
       });

        ////////////////////////////////////////////////
        //Timer zuweisen
        textview_timer = findViewById(R.id.textview_timer);
        //Timer Dauer

        textColor_countdown = textview_timer.getTextColors();

        textview_level= findViewById(R.id.text_view_level);

        textview_leben3 = findViewById(R.id.textview_leben3);
        textview_leben2 = findViewById(R.id.textview_leben2);
        textview_leben1 = findViewById(R.id.textview_leben1);

        textView_lebenAnzeige = findViewById(R.id.textview_LebenCount);


        if(timerRunning){
            pauseCountdown();
            restartCountdown();
        }
        startCountdown();

        //////////////////////////////////////////////////////////

        //popUp
        mydialog = new Dialog(Quiz.this);
        dialogHinweis = new Dialog(Quiz.this);
        dialogLost = new Dialog(Quiz.this);
    }


    /**
     * onClick für Antwortbutton
     */
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
            countlevel();

        }
    };


    private void showNextQuestion() {

        saveQuestion();
        //genutze Frage in Liste speichern
        //Wenn Frage gleich einer Frage in der Liste ist, dann nächste Frage


        if (questionCounter < questionCountTotal) {

            currentQuestion = fragenliste.get(questionCounter);
            frage.setText(currentQuestion.getFragen());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());
            option4.setText(currentQuestion.getOption4());

            questionCounter++;

        } else {

            //Quiz beenden und Liste leeren
            finishQuiz();

        }
    }

    private void saveQuestion(){

        List<String> seenQuestions = new ArrayList<>();
        if(currentQuestion != null)
            seenQuestions.add(currentQuestion.getFragen());

        if(questionCounter < questionCountTotal){
            if(currentQuestion != null) {
                if (seenQuestions.equals(currentQuestion.getFragen())) {
                    fragenliste.iterator().next();
                }
            }
        }

    }

    private void finishQuiz() {
        finish();
    }

    private boolean checkAnswer(int buttoncount, Button btnAnswer){
            //////////////////
            boolean answered = false;
            ///////////////////////////

            if (btnAnswer.isPressed()) {

                //falsche antworten rot
                if(currentQuestion != null) {
                    if (buttoncount == currentQuestion.getAntwort_nr()) {
                        //wenn Antwort richtig
                        //Farbe des Buttons grün
                        answered = true;
                        btnAnswer.setBackgroundTintList(ContextCompat.getColorStateList(Quiz.this, R.color.lightgreen));


                        //nachdem man richtige Antwort anklickt -> nicht mehr drücken
                        option1.setEnabled(false);
                        option2.setEnabled(false);
                        option3.setEnabled(false);
                        option4.setEnabled(false);

                        //debug message
                        //Toast.makeText(getApplicationContext(), "richtig", Toast.LENGTH_LONG).show();

                        // -> Gewonnen Pop Up -> next level
                        CreateNextLevelDialog();

                    } else {
                        //Auswahl wird rot
                        btnAnswer.setBackgroundTintList(ContextCompat.getColorStateList(Quiz.this, R.color.lightred));
                        //falsche Antwort -> leben weg
                        //keine leben mehr -> Antwort anzeigen -> Quiz beenden
                        answered = false;

                    }
                }
            }

        return answered;
    }


    private void countlevel() {
        if(level_count >=1 && level_count <=MAXLEVEL ) {
            //wenn die Antwort richtig ist level hochzählen
            if (this.checkAnswer(1, option1) || this.checkAnswer(2, option2) || this.checkAnswer(3, option3) || this.checkAnswer(4, option4))  {
                this.level_count++;
            }
            else {
                this.leben_count--;
            }
            showLevel();
        }
    }

    private void showLevel(){
        textview_level.setText("Level :"+ this.level_count);
    }

    private void showLeben()
    {
        textView_lebenAnzeige.setText("Leben "+ this.leben_count);
        //3 Textviews sollen da sein
        if(this.leben_count==2){

            //textview_leben3 verstecken
            textview_leben3.setVisibility(View.INVISIBLE);
        }
        if(this.leben_count==1){
            //textview_leben3 und 2 verstecken
            textview_leben3.setVisibility(View.INVISIBLE);
            textview_leben2.setVisibility(View.INVISIBLE);

        }
        if(this.leben_count==0 ){
            textview_leben3.setVisibility(View.INVISIBLE);
            textview_leben2.setVisibility(View.INVISIBLE);
            textview_leben1.setVisibility(View.INVISIBLE);
            popUpVerloren();
        }
    }


    private void countlebenCountdown(){
        showLeben();
        if (verbleibendeZeit <= 500) {
            this.leben_count--;
        }
        showLeben();
    }
    //andere Methode, da sonst jede Sekunde ein Leben abgezogen wird
    private void countlebenAnswer(){
       // if (this.checkAnswer(1, option1)==false || this.checkAnswer(2, option2)== false||
       //         this.checkAnswer(3, option3)== false|| this.checkAnswer(4, option4)== false)  {

            this.leben_count--;
    }
    /**
     * Popup to Next Level
     */
    private void CreateNextLevelDialog(){

        mydialog.setContentView(R.layout.popupnextlevel);
        toNextLevel = (Button) mydialog.findViewById(R.id.nextLevel);
        mydialog.setCanceledOnTouchOutside(false);
        mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        toNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(checkAnswer(1, option1) || checkAnswer(2, option2) || checkAnswer(3, option3) || checkAnswer(4, option4)){ {
                    if(questionCounter < questionCountTotal) {
                        //nächste Frage anzeigen
                        showNextQuestion();
                        //restartCountdown();
                        option1.setEnabled(true);
                        option2.setEnabled(true);
                        option3.setEnabled(true);
                        option4.setEnabled(true);

                        option1.setBackgroundTintList(ContextCompat.getColorStateList(Quiz.this, R.color.lightblue));
                        option2.setBackgroundTintList(ContextCompat.getColorStateList(Quiz.this, R.color.lightblue));
                        option3.setBackgroundTintList(ContextCompat.getColorStateList(Quiz.this, R.color.lightblue));
                        option4.setBackgroundTintList(ContextCompat.getColorStateList(Quiz.this, R.color.lightblue));

                        if (timerRunning) {
                            pauseCountdown();
                            restartCountdown();
                        }
                        startCountdown();
                    }

                mydialog.dismiss();
            }
        });
        //show Popup
        mydialog.show();

    }

    /**
     * Popup für Hinweis
     */
    private void popUpHinweis(){
        dialogHinweis.setContentView(R.layout.popuphinweis);
        hinweisText = (TextView) dialogHinweis.findViewById(R.id.hinweisPopup);
        closesHinweis = (Button) dialogHinweis.findViewById(R.id.closeHinweis);
        dialogHinweis.setCanceledOnTouchOutside(false);
        dialogHinweis.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        hinweisText.setText(currentQuestion.getHinweis());

        closesHinweis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHinweis.dismiss();
            }
        });

        dialogHinweis.show();
    }

    private void popUpVerloren(){
        dialogLost.setContentView(R.layout.popuplost);
        closeLost = (Button) dialogLost.findViewById(R.id.closeVerloren);
        dialogLost.setCanceledOnTouchOutside(false);
        //dialogLost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        closeLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishQuiz();
                dialogLost.dismiss();
            }
        });

        dialogLost.show();
    }

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
                timerRunning = false;
            }
        }.start();

        timerRunning= true;
    }

    @Override
    public void updateCountdownText() {
        int minuten = (int) (verbleibendeZeit / 1000) / 60;
        int sekunden = (int) (verbleibendeZeit / 1000) % 60;

        // String zeitformatiert = String.format(Locale.getDefault(), "\"%02d : %02d", minuten, sekunden);
        String zeitformatiert = String.format(Locale.getDefault(), "%02d", sekunden);

        textview_timer.setText(zeitformatiert);

        if (verbleibendeZeit < 11000) {
            textview_timer.setTextColor(Color.RED);
        } else {
            textview_timer.setTextColor(textColor_countdown);
        }

        countlebenCountdown();
        //showLeben();
    }

    private void pauseCountdown(){
        countdown.cancel();
        timerRunning = false;
    }
    private void restartCountdown()
    {
        verbleibendeZeit= COUNTDOWN_IN_MILLIS;
        updateCountdownText();
        //textview_timer.setText("30");
    }
}