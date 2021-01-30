package com.example.quizme_layout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.SwipeButton;
import com.example.Datenbank.DatabaseHelper;
import com.example.Datenbank.DatabaseHighscorelist;
import com.example.Datenbank.HighscoreModel;
import com.example.Datenbank.QuizMeModel;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Quiz extends AppCompatActivity {

    private TextView frage;
    private TextView hinweis;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private Button quit;

    //Databank variables
    private List<QuizMeModel> fragenliste;
    private int questionCounter;
    private int questionCountTotal;
    private QuizMeModel currentQuestion;

    private List<HighscoreModel> highscoreliste;

    //Countdown Variables
    private TextView textView_timer_unten;
    private TextView textview_timer;
    private CountDownTimer countdown;
    private boolean timerRunning;
    private long verbleibendeZeit;
    static final long COUNTDOWN_IN_MILLIS = 45000;

    private Chronometer chronometer;

    //Level Variables
    private TextView textview_level;
    private int level_count = 1;
    private final int MAXLEVEL = 50;

    //Leben Variables
    private TextView textview_leben3;
    private TextView textview_leben2;
    private TextView textview_leben1;
    private TextView textView_lebenAnzeige;
    private int leben_count = 3;

    //Pop-up Window Variables - Next
    private Dialog mydialog;

    //Pop-up Window Variables - Hinweis
    private Dialog dialogHinweis;

    //Pop-up Window Variables - Verloren
    private Dialog dialogLost;

    //Pop-up Window Variables - Gewonnen
    private Dialog dialogWin;


    Minigame3_pressTheButton game3;
    Button klickMich_b;
    TextView klickZaehler_tv;
    TextView klickMax_tv;

    Musik musik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.musik);
        musik = new Musik(mediaPlayer);

        //starten der Hintergrundanimation
        //muss vor dem Countdown und dem Chronometer passieren
        //android:background="@drawable/hintergrund_liste"
        backgroundAnimation();

        frage = findViewById(R.id.question);

        // *********************** Vorläufig auskommentiert
        //hinweis = findViewById(R.id.hinweis);


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
        //liste mit allen Fragen aus der Datenbank
        fragenliste = dbHelper.getAllQuestions();
        questionCountTotal = fragenliste.size();
        //Zufällige Fragen aus der Liste wählen
        Collections.shuffle(fragenliste);

        if (currentQuestion == null) {
            showNextQuestion();
            minigames();
        }

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishQuiz();
                musik.endMusik();
            }
        });

        //Level
        textview_level = findViewById(R.id.text_view_level);

        //Leben
        textview_leben3 = findViewById(R.id.textview_leben3);
        textview_leben2 = findViewById(R.id.textview_leben2);
        textview_leben1 = findViewById(R.id.textview_leben1);
        textView_lebenAnzeige = findViewById(R.id.textview_LebenCount);

        //Timer
        textview_timer = findViewById(R.id.textview_timer);

        //popUp
        mydialog = new Dialog(Quiz.this);
        dialogHinweis = new Dialog(Quiz.this);
        dialogLost = new Dialog(Quiz.this);
        dialogWin = new Dialog(Quiz.this);

        textView_timer_unten = findViewById(R.id.textview_timerTest);
        chronometer = findViewById(R.id.textview_chronometer);
        createChronometer();
        createPanel();

        if (timerRunning) {
            pauseCountdown();
            restartCountdown();
        }
        startCountdown();
    }
    private int zufallszahl()
    {
        int zahl;
        Random zufallszahl = new Random();
        zahl = 1 + zufallszahl.nextInt(3);
        return zahl;
    }

    private void minigames(){


        int zahl = zufallszahl();
        minigame1_swipe(zahl);
        minigame2_swipeCardsGame(zahl);
        minigame3_pressButton(zahl);

    }

    private void resetMinigame(){

    }


    private void minigame2_swipeCardsGame(int zahl){

        ArrayList<String> card = new ArrayList<>();
        SwipeFlingAdapterView swipeAdapter = (SwipeFlingAdapterView) findViewById(R.id.cards);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Quiz.this, R.layout.karten, R.id.textview_kartenzahl, card);
        Minigame2_SwipeCards cards = new Minigame2_SwipeCards(Quiz.this, swipeAdapter, card, arrayAdapter);
        swipeAdapter.setVisibility(View.INVISIBLE);

        if(zahl == 2) {
            cards = new Minigame2_SwipeCards(Quiz.this, swipeAdapter, card, arrayAdapter);
            swipeAdapter.setVisibility(View.VISIBLE);
        } else {
            cards.reset();
        }

        cards.add();
        cards.createCards();

    }



    private void minigame3_pressButton(int zahl){
        klickMich_b =  findViewById(R.id.klickMich);
        klickZaehler_tv = findViewById(R.id.klickZaehler);
        klickMax_tv = findViewById(R.id.klickMax);

        klickMich_b.setVisibility(View.INVISIBLE);
        klickZaehler_tv.setVisibility(View.INVISIBLE);
        klickMax_tv.setVisibility(View.INVISIBLE);

        if(zahl == 1) {
            game3 = new Minigame3_pressTheButton(klickMich_b, klickZaehler_tv, klickMax_tv);
            game3.clickMe();
        }
    }


    private void minigame1_swipe(int zahl){
        SwipeButton swipeButton1 = (SwipeButton) findViewById(R.id.swipeButton1);
        SwipeButton swipeButton2 = (SwipeButton) findViewById(R.id.swipeButton2);
        SwipeButton swipeButton3 = (SwipeButton) findViewById(R.id.swipeButton3);

        swipeButton1.setVisibility(View.INVISIBLE);
        swipeButton2.setVisibility(View.INVISIBLE);
        swipeButton3.setVisibility(View.INVISIBLE);
        //WENN DAS MINISPIEL KOMMEN SOLL

        Minigame1 minigame1;

        if(zahl == 3) {
            minigame1 = new Minigame1(swipeButton1, swipeButton2, swipeButton3);
            minigame1.spielen();
        }
            //minigame1.reset(swipeButton1, swipeButton2, swipeButton3);

    }


    public String getStringTime(){
        return chronometer.getText().toString();
    }

    private void createPanel()
    {
            //das SlideupPanel dem Hintergrund zuweisen
            SlidingUpPanelLayout layout = findViewById(R.id.hintergrundQuiz_id);
            layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
                @Override
                public void onPanelSlide(View panel, float slideOffset) {
                    findViewById(R.id.textview_panel).setAlpha(1 - slideOffset);
                }
                @Override
                public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                    if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        hinweis = findViewById(R.id.textview_hinweis);
                        hinweis.setText(currentQuestion.getHinweis());
                    }
                }
            });
    }
    /***
     * Die Hintergrundanimation, welche aus aus einer Animationsliste besteht, soll hier in ihrer Dauer
     * angepasst und gestartet werden
     */
    private void backgroundAnimation(){
        RelativeLayout constraintLayout = findViewById(R.id.hintergrundQuiz_id_relative);

        //der Hintergrund beinhaltet die Animationsliste (siehe quiz.xml),diese muss als Variable definiert werden, um sie starten zu können
        AnimationDrawable animation= (AnimationDrawable) constraintLayout.getBackground();
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(animation, 4000);
    }

    private void createChronometer() throws NullPointerException{
        try {
            this.chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer _chronometer) {
                    chronometer = _chronometer;
                }
            });
            chronometer.start();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    /**
     * onClick für Antwortbutton
     */
    private final View.OnClickListener answer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.choice1:
                    checkAnswer(1,option1);
                    //Button nicht doppelt klicken können
                    option1.setEnabled(false);
                    break;
                case R.id.choice2:
                    checkAnswer(2,option2);
                    option2.setEnabled(false);
                    break;
                case R.id.choice3:
                    checkAnswer(3,option3);
                    option3.setEnabled(false);
                    break;
                case R.id.choice4:
                    checkAnswer(4,option4);
                    option4.setEnabled(false);
                    break;
            }

            countlevel();
        }
    };


    private void showNextQuestion() {

        saveQuestion();

        //aktuelle Frage anzeigen wenn es noch Fragen gibt
        if (questionCounter < questionCountTotal) {

            currentQuestion = fragenliste.get(questionCounter);
            frage.setText(currentQuestion.getFragen());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());
            option4.setText(currentQuestion.getOption4());

            questionCounter++;
        }
    }

    private void saveQuestion(){

        //genutze Frage in Liste speichern
        List<String> seenQuestions = new ArrayList<>();
        // Wenn Frage gleich einer Frage in der Liste ist, dann nächste Frage
        if(currentQuestion != null)
            seenQuestions.add(currentQuestion.getFragen());

        if(questionCounter < questionCountTotal) {
            if (currentQuestion != null) {
                if (seenQuestions.equals(currentQuestion.getFragen())) {
                    fragenliste.iterator().next();
                }
            }
        }
    }

    public int getLevelCount(){
        return this.level_count;
    }


    //Zurück zur Startseite
    private void finishQuiz() {
        Intent intent = new Intent(this, Startseite.class);
        startActivity(intent);
        intent.putExtra("currentQuestion", currentQuestion);
    }

    /**
     * Kontrollieren ob eine Antwort richtig oder Falsch ist
     * @param buttoncount Nummer des Button
     * @param btnAnswer der ausgewählte Button
     * @return Bei falsch wird der Button rot, Bei richtig wird er grün
     */
    private boolean checkAnswer(int buttoncount, Button btnAnswer){
            //////////////////
            boolean answered = false;
            ///////////////////////////

            if (btnAnswer.isPressed()) {

                if(currentQuestion != null) {
                    //Wenn Buttonnr mit Antwortnr aus der Datenbank übereinstimmt, ist es die richtige Antwort
                    if (buttoncount == currentQuestion.getAntwort_nr()) {
                        //wenn Antwort richtig
                        //Farbe des Buttons grün
                        answered = true;
                        btnAnswer.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.button_green));

                        //nachdem man richtige Antwort anklickt -> nicht mehr drücken können
                        option1.setEnabled(false);
                        option2.setEnabled(false);
                        option3.setEnabled(false);
                        option4.setEnabled(false);

                        //debug message
                        //Toast.makeText(getApplicationContext(), "richtig", Toast.LENGTH_LONG).show();

                        //der Countdown muss abgebrochen werden, da man sonst ein weiteres Leben verliert während sich das
                        //gewonnen Pop up öffnet

                        //////////
                        pauseCountdown();

                        // -> nachste Frage Pop Up -> next level
                        if(questionCounter < questionCountTotal)
                        createNextLevelDialog();

                        //Keine level mehr -> Gewonnen Popup
                        if(questionCounter == questionCountTotal)
                            popUpGewonnen();



                    } else {
                        //falsche antworten rot
                        //Auswahl wird rot
                        btnAnswer.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.button_red));
                        answered = false;

                    }
                }
            }

        return answered;
    }

    /***
     * Wenn ein Button mit der richtigen Antwort gedrückt wird, kommt man in das nächste Level,
     * also wird das Level hochgezählt
     * Wenn ein Button mit der falschen Antwort gedrückt wird, bekommt man ein Leben abgezogen
     */
    private void countlevel() {
        if(level_count >=1 && level_count <=MAXLEVEL ) {
            //wenn die Antwort richtig ist level hochzählen
            if (this.checkAnswer(1, option1) || this.checkAnswer(2, option2) || this.checkAnswer(3, option3) || this.checkAnswer(4, option4))  {
                this.level_count++;
            }
            else {
                //Sonst wird ein Leben abgezogen
                this.leben_count--;
            }
            showLevel();
        }
    }

    /**
     * Die Anzahl der Leben wird in die zugehörige Tetview gesetzt
     */
    private void showLevel(){
        textview_level.setText("Level :"+ this.level_count);
    }

    /**
     * Soll dafür sorgen, dass angezeigt wird wie viele Leben noch da sind.
     * Außerdem verschwinden je nach Leben die Textview-Herzen
     * Wenn man 0 Leben hat, dann kommt das Verloren-Pop-Up
     */
    private void showLeben()
    {   //Die Anzahl der Leben auf der Textview ausgeben
        textView_lebenAnzeige.setText("Leben: "+ this.leben_count);

        //Bei 3 Leben sind alle Textviews noch da

        if(this.leben_count==2){
            //textview_leben3 verstecken
            textview_leben3.setVisibility(View.INVISIBLE);
        }
        if(this.leben_count==1){
            //textview_leben3 und 2 verstecken
            textview_leben3.setVisibility(View.INVISIBLE);
            textview_leben2.setVisibility(View.INVISIBLE);
        }
        //keine Leben, also hat man verloren
        if(this.leben_count==0 ){
            pauseCountdown();
            //alle Leben-Textviews verschwinden
            textview_leben3.setVisibility(View.INVISIBLE);
            textview_leben2.setVisibility(View.INVISIBLE);
            textview_leben1.setVisibility(View.INVISIBLE);

            //die allgemeine zeit wird gestoppt
            chronometer.stop();

            //Das Verloren-Pop-Up erscheint
            popUpVerloren();
        }
    }

    /***
     * Hier werden die Leben abgezogen, die man dadurch verliert, dass der Countdown abläuft
     */
    private void countlebenCountdown(){
        //Aktuelle Anzahl der Leben anzeigen
        showLeben();
            //Wenn der Countdown abläuft ein Leben abziehen
            if (verbleibendeZeit <= 100) {
                this.leben_count--;
                //neu anzeigen
                showLeben();
                //eine neue Frage soll erscheinen
                showNextQuestion();
                //und der Countdown muss wieder neu starten
                pauseCountdown();
                restartCountdown();
                //minigames
                minigames();
                //start
                startCountdown();
            }
    }

    /**
     * Popup to Next Level
     */
    private void createNextLevelDialog(){

        if(questionCounter < questionCountTotal) {
            mydialog.setContentView(R.layout.popupnextlevel);
            Button toNextLevel = (Button) mydialog.findViewById(R.id.nextLevel);
            //damit man es nicht verschwindet wenn man außerhalb des Popups klickt
            mydialog.setCanceledOnTouchOutside(false);
            mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            toNextLevel.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {

                    //nächste Frage anzeigen
                    showNextQuestion();
                    //resetMinigame();
                    minigames();

                    //Button wieder klicken können, weil bei richtiger Antwort Buttons gesperrt werden
                    option1.setEnabled(true);
                    option2.setEnabled(true);
                    option3.setEnabled(true);
                    option4.setEnabled(true);

                    //Farbe wieder normal setzen (da vorher vllt rot oder grün war)
                     option1.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.rounded_corners));
                     option2.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.rounded_corners));
                     option3.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.rounded_corners));
                     option4.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.rounded_corners));

                    if (timerRunning) {
                        pauseCountdown();
                        restartCountdown();
                    }
                    startCountdown();

                    mydialog.dismiss();
                }
            });
        }

        //show Popup
        if(!isFinishing())
            mydialog.show();
    }


    private void popUpVerloren(){

        dialogLost.setContentView(R.layout.popuplost);
        Button closeLost = (Button) dialogLost.findViewById(R.id.closeVerloren);
        dialogLost.setCanceledOnTouchOutside(false);
        dialogLost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        closeLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Zum Popup um Name einzutragen
                popupInsertName();
                //Popup schließen
                dismissWithTryCatch(dialogLost);

            }
        });

        //wenn es nicht schließt, Popup anzeigen lassen
    if(!isFinishing())
        dialogLost.show();
    }

    /**
     *  Fehler fangen, damit Popup richtig schließen kann (kein Crashen der App)
     */
    public void dismissWithTryCatch(Dialog dialog) {
        try {
            dialog.dismiss();
        } catch (final IllegalArgumentException e) {
            // Do nothing.
        } catch (final Exception e) {
            // Do nothing.
        } finally {
            dialog = null;
        }
    }

    private void popUpGewonnen(){

        dialogWin.setContentView(R.layout.popup_gewonnen);
        Button closeWin = (Button) dialogWin.findViewById(R.id.closeGewonnen);
        dialogWin.setCanceledOnTouchOutside(false);
        dialogWin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        closeWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Popup aufrufen um Namen einzutragen
                popupInsertName();
                //Popup schließen
                dismissWithTryCatch(dialogWin);
                musik.endMusik();
            }
        });
        //Popup anzeigen lassen, wenn es nicht schließt
        if(!isFinishing())
            dialogWin.show();
    }

    private void zurHighscoreliste(){
        Intent intent = new Intent(Quiz.this, Highscoreliste.class);
        startActivity(intent);
    }

    private void popupInsertName(){
        AlertDialog.Builder  insertUsername = new AlertDialog.Builder(this);
        insertUsername.setTitle("Bitte geben Sie Ihren Namen ein, um in die Highscoreliste zu gelangen:");

        final EditText username = new EditText(Quiz.this);
        username.setInputType(InputType.TYPE_CLASS_TEXT );
        insertUsername.setView(username);

        //Datenbank Highscoreliste
        DatabaseHighscorelist dbHighscore = new DatabaseHighscorelist(Quiz.this);

        insertUsername.setPositiveButton("Sichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Edit Text in Datenbank speichern
                HighscoreModel model;

                try {
                    if(username.getText().toString()!= null) {
                        model = new HighscoreModel(username.getText().toString(), getStringTime(), getLevelCount());

                        //Daten einfügen in die Datenbank
                        boolean success = dbHighscore.add( model);
                        //Test
                        //Toast.makeText(Quiz.this, "Success " + success, Toast.LENGTH_SHORT).show();

                        //Zur Highscoreseite
                        zurHighscoreliste();
                        musik.endMusik();


                    } else {
                        Toast.makeText(getApplicationContext(),"Vorgang abgebrochen", Toast.LENGTH_SHORT).show();
                        dialog.cancel();

                    }

                } catch (Exception e){
                    model = new HighscoreModel("Error", "Error", 0);
                    Toast.makeText(Quiz.this, model.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        });

        insertUsername.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //ohne Speichern zur Highscoreliste
                zurHighscoreliste();
                musik.endMusik();
            }
        });

        //man kann nicht außerhalb des Popups klicken
        Dialog insertDialog = insertUsername.create();
        insertDialog.setCanceledOnTouchOutside(false);

        //Popup anzeigen
        insertDialog.show();

        insertDialog.getWindow().setBackgroundDrawableResource(R.drawable.hintergrundbild2_round);
        insertDialog.getWindow().setLayout(720,450);

    }

    /***
     * Hier wird der Countdown auf 30 Sekunden gesetzt und gestartet
     * Der Countdown wird heruntergezählt und auf 0 gesetzt
     * */
    public void startCountdown(){
        //den Countdown zuweisen auf 30 Sekunden
        countdown = new CountDownTimer(COUNTDOWN_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Zeit herunterzählen
                verbleibendeZeit = millisUntilFinished;
                //auf der Textview anzeigen
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                //Wenn der Timer abgelaufen ist
                //Zeit auf 0 setzen
                verbleibendeZeit = 0;
                //auf der Textview anzeigen
                updateCountdownText();

                //Der Countdown läuft nicht weiter
                timerRunning = false;
            }
        }.start();
        //Der Countdown läuft
        timerRunning= true;
    }

    /***
     * Hier wird der zu sehende Text der Textview aktualisiert
     * Die Zeit wird in Sekunden heruntergezählt
    */
    public void updateCountdownText() {
        //umrechnen der verbleibenden Zeit in Sekunden
        int sekunden = (int) (verbleibendeZeit / 1000) % 60;

        //die verbleibende Zeit wird als String formatiert
        String zeitformatiert = String.format(Locale.getDefault(), "%02d", sekunden);

        //Die String mit der Zeit wird in die Textview gesetzt
        textview_timer.setText(zeitformatiert);
        //Zeit auch im unteren Layout anzeigen
        textView_timer_unten.setText("übrige Zeit: " + zeitformatiert);

        //In den letzten 10 Sekunden wird die Anzeige rot
        countdownTextRed();

        //Wenn der Countdown abläuft wird hier ein Leben abgezogen
        countlebenCountdown();
    }

    /***
     * In den letzten 10 Sekunden wird die Anzeige des Countdowns rot rot
     */
    private void countdownTextRed(){
        //letzeen 10 Sekunden, also rot
        if (verbleibendeZeit < 11000) {
            textview_timer.setTextColor(Color.RED);

        //Ansonsten bleibt die Anzeige wie voher
        } else {
            textview_timer.setTextColor(Color.BLACK);
        }
    }

    /***
     * Der Countdown soll abgebrochen werden
    */
    private void pauseCountdown(){
        countdown.cancel();

        //Der Countdown läuft nicht weiter
        timerRunning = false;
    }

    /***
     * Wenn der Countdown zurückgesetzt wird, muss die verbleibende Zeit wieder auf 30 Sekunden gesetzt werden
    */
    private void restartCountdown() {
        verbleibendeZeit= COUNTDOWN_IN_MILLIS;

        //die Anzeige der Textview aktualisieren
        updateCountdownText();
    }
}