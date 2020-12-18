package com.example.quizme_layout;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
    private boolean answered;
    private QuizMeModel currentQuestion;

    /////////////////////////////////////////
    private ColorStateList textColor_countdown;
    private CountDownTimer countdown;
    private boolean timerRunning;
    private long verbleibendeZeit;
    private TextView textview_timer;

    private TextView textview_level;
    private int level_count =1;
    static final int MAXLEVEL = 50;

    private TextView textview_leben3;
    private TextView textview_leben2;
    private TextView textview_leben1;
    //muss noch gesetzt werden
    private int leben_count;
    final String prefNameFirstStart = "firstAppStart";
    ///////////////////////////////////////////

    //Pop-up Window Variables
    private Dialog mydialog;
    private Button toNextLevel;
    private TextView congrats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        frage = findViewById(R.id.question);
        hinweis = findViewById(R.id.hinweis);
        quit = findViewById(R.id.quit);

        /*
        for (int i = 0; i < btn.length; i++) {
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setOnClickListener(answer);
        }*/


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
                Toast.makeText(getApplicationContext(), currentQuestion.getHinweis(), Toast.LENGTH_SHORT).show();
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

        //textview_leben3 =
        //textview_leben2 =
        //textview_leben1
        if(timerRunning){
            pauseCountdown();
            restartCountdown();
        }

        //startCountdown();
        if(timerRunning){
            pauseCountdown();
            restartCountdown();
        }
        startCountdown();

        //////////////////////////////////////////////////////////

        //popUp
        mydialog = new Dialog(Quiz.this);
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
            /////////////////////////////////////////////////////////////
            //startCountdown();
            //////////////////////////////////////////////////////
            countlevel();

        }
    };


    private void showNextQuestion() {

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
            answered = false;
            saveQuestion();

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
                        btnAnswer.setBackgroundColor(Color.parseColor("#98FB98"));
                        answered = true;

                        Toast.makeText(getApplicationContext(), "richtig", Toast.LENGTH_LONG).show();

                        //Popup zum nächsten Level
                        CreateNextLevelDialog();

                        // -> Gewonnen Pop Up -> next level

                    } else {
                        btnAnswer.setBackgroundColor(Color.parseColor("#FF6347"));
                        //leben weg -> falsche antwort
                        //keine leben mehr -> Antwort anzeigen -> Quiz beenden
                        answered = false;
                    }

                    //nachdem man eine Antwort anklickt -> nicht mehr drücken
                    option1.setEnabled(false);
                    option2.setEnabled(false);
                    option3.setEnabled(false);
                    option4.setEnabled(false);

                }

            }

        return answered;
    }


    private void countlevel() {
        if(level_count >=1 && level_count <=MAXLEVEL ) {
            //wenn die Antwort richtig ist level hochzählen
            if (this.checkAnswer(1, option1)== true || this.checkAnswer(2, option2)== true||
                    this.checkAnswer(3, option3)== true|| this.checkAnswer(4, option4)== true)  {
                this.level_count++;
            }
            else {
            }
            showLevel();
        }
    }

    private void showLevel(){
        textview_level.setText("Level :"+ this.level_count);
    }

    private void showLeben()
    {
        //3 Textviews sollen da sein
        if(this.leben_count==3){
            //nichts verstecken
        }
        if(this.leben_count==2){

            //textview_leben3 verstecken
        }
        if(this.leben_count==1){
            //textview_leben3 verstecken
            //textview_leben2 verstecken
        }
        if(this.leben_count!=3 || this.leben_count!=2 || this.leben_count!=1 ){
            //Problemm
        }
    }

    private void countleben(){

    }

    /**
     * Popup to Next Level
     */
    private void CreateNextLevelDialog(){

        mydialog.setContentView(R.layout.popupnextlevel);
        toNextLevel = (Button) mydialog.findViewById(R.id.nextLevel);
        congrats = (TextView) mydialog.findViewById(R.id.congrats);
        mydialog.setCanceledOnTouchOutside(false);
        toNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //nächste Frage anzeigen

                showNextQuestion();
                //restartCountdown();
                option1.setEnabled(true);
                option2.setEnabled(true);
                option3.setEnabled(true);
                option4.setEnabled(true);

                mydialog.dismiss();

                if(timerRunning){
                    pauseCountdown();
                    restartCountdown();
                }
                startCountdown();


            }
        });
        //show Popup
        mydialog.show();

    }

    ////////////////////////////////////////////////////////////////////
    public boolean firstAppStart()
    {
        //Mode_private nur unsere App kann zugreifenauf die Preference
        //bei public können auch andere Apps zugreifen
        SharedPreferences preferences = getSharedPreferences(prefNameFirstStart, MODE_PRIVATE);

        if(preferences.getBoolean(prefNameFirstStart, true))
        {   //man möchte einen boolean haben, wenn in prefNameFirstStart kein boolean ist, soll true zurückgegeben

            //um den boolean der Prefernce zu verändern
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(prefNameFirstStart, false);
            //damit die preference geändert wird
            editor.commit();
            ///startCountdown();
            return true;
        }
        else
        {
            return false;       //die App wurde schon gestartet
        }
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