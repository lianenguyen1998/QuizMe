package com.example.quizme_layout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Datenbank.DatabaseFragenliste;
import com.example.Datenbank.DatabaseHighscorelist;
import com.example.Datenbank.HighscoreModel;
import com.example.Datenbank.QuizMeModel;
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

    //Databank variables
    private List<QuizMeModel> fragenliste;
    private int questionCounter;
    private int questionCountTotal;
    private QuizMeModel currentQuestion;

    //Countdown Variables
    private TextView textView_Countdown_unten;
    private TextView textview_Countdown;
    private CountDownTimer countdown;
    private boolean countdownLaeuft;
    private long verbleibendeZeit;
    static final long COUNTDOWN_IN_MILLIS = 45000;

    private Chronometer chronometer;

    //Level Variables
    private TextView textview_level;
    private int level_count = 1;

    //Leben Variables
    private TextView textview_leben3;
    private TextView textview_leben2;
    private TextView textview_leben1;
    private TextView textView_lebenAnzeige;
    private int leben_count = 3;

    //Pop-up Window Variables - Next
    private Dialog mydialog;

    //Pop-up Window Variables - Verloren
    private Dialog dialogLost;

    //Pop-up Window Variables - Gewonnen
    private Dialog dialogWin;

    //Hintergrundmusik
     private Musik musik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        //Hintergrundmusik (wird gestartet)
        musik = new Musik(this);

         //Hintergrundanimation
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(Quiz.this, 4000);

        frage = findViewById(R.id.question);

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
        DatabaseFragenliste dbHelper = new DatabaseFragenliste(this);
        //liste mit allen Fragen aus der Datenbank
        fragenliste = dbHelper.getAllQuestions();
        questionCountTotal = fragenliste.size();
        //Zufällige Fragen aus der Liste wählen
        Collections.shuffle(fragenliste);

        if (currentQuestion == null) {
            showNextQuestion();
            minigames();
        }

        Button quit = findViewById(R.id.quit);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zurStartseite();
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

        //Countdown
        textview_Countdown = findViewById(R.id.textview_Countdown);
        textView_Countdown_unten = findViewById(R.id.textview_Countdown_unten);

        //starten des Countdowns
        if (countdownLaeuft) {
            stoppCountdown();
            restartCountdown();
        }
        startCountdown();

        //Chronometer (Zeit)
        chronometer = findViewById(R.id.textview_chronometer);
        createChronometer();

        //popUp
        mydialog = new Dialog(Quiz.this);
        dialogLost = new Dialog(Quiz.this);
        dialogWin = new Dialog(Quiz.this);

        //SlidingUpPanel zum hochziehen für den Hinweis
        createPanel();
    }

    /***
     * Eine Zufallszahl wird für die Auswahl des Minigames generiert
     * @return die zufallszahl zwischen 1 und 3
     */
    private int zufallszahl()
    {
        int zahl;
        //Zufallsobjekt
        Random zufallszahl = new Random();
        //zahl zwischen 1 und 3
        zahl = 1 + zufallszahl.nextInt(3);
        return zahl;
    }

    /***
     * Hier wird je nach Zufallszahl das passende Minigame gestartet. Die nicht ausgewählten Minispiele sind
     * unsichtbar
     */
    private void minigames(){
        int zahl = zufallszahl();

        //Minigame 1 (unsichtbar)
        Minigame1_SwipeButtons minigame1 = new Minigame1_SwipeButtons(Quiz.this);
        minigame1.invisible();

        //Minigame 2
        Minigame2_SwipeCards minigame2 = new Minigame2_SwipeCards(Quiz.this, nextQuestion());

        //Minigame1 wurde ausgewählt
        if(zahl == 1){
            //es wird sichtbar
            minigame1.visible();
            //es wird gestartet
            minigame1.spielen();
        }
        //Minigame2 wurde ausgewählt
        else if (zahl == 2) {
            minigame2.createCards();
            if(nextQuestion()){
                minigame2.reset();
            }
        }
        //Minigame3 wurde ausgewählt
        else {
//            minigame3_pressButton();
//            //es wird sichtbar
//            minigame3.visible();
//            //es wird gestartet
//            minigame3.clickMe();
        }
    }

    private void minigame2_swipeCardsGame(){

        Minigame2_SwipeCards cards = new Minigame2_SwipeCards(Quiz.this, nextQuestion());

    }


    /**
     * Methode um den zurück-Button zu steuern
     *      -die Musik wird gestoppt
     *      -zurück zur Startseite
     */
    @Override
    public void onBackPressed() {
        //Musik stoppen
        musik.endMusik();

        //zur Startseite
        zurStartseite();
        finish();
    }

    public String getStringTime(){
        return chronometer.getText().toString();
    }

    public int getLevelCount(){
        return this.level_count;
    }

    /***
     * Methode, um zurück zur Startseite zu gelangen
     */
    private void zurStartseite() {
        Intent intent = new Intent(this, Startseite.class);
        startActivity(intent);
    }

    /***
     * Methode, um zurück zur Highscoreliste zu gelangen
     */
    private void zurHighscoreliste(){
        Intent intent = new Intent(Quiz.this, Highscoreliste.class);
        startActivity(intent);
    }

    /***
     * Das SlidingUpPanel zum hochziehen für den Hinweis wird hier erstellt
     */
    private void createPanel()
    {
            //das SlideupPanel dem Hintergrund zuweisen
            SlidingUpPanelLayout layout = findViewById(R.id.hintergrundQuiz_id);
            layout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {

                //damit das Panel hochgezogen werden kann
                @Override
                public void onPanelSlide(View panel, float slideOffset) {
                    findViewById(R.id.textview_panel).setAlpha(1 - slideOffset);
                }
                //zur Überprüfung in welchem Zustand sich das Panel befindet
                @Override
                public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                    //wenn es hochgezogen wurde
                    if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        //den Hinweis ausgeben
                        hinweis = findViewById(R.id.textview_hinweis);
                        hinweis.setText(currentQuestion.getHinweis());
                    }
                }
            });
    }

    /***
     *diese Methode startet den Chronometer
     * @throws NullPointerException
     */
    private void createChronometer() throws NullPointerException{
        try {
            this.chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
                @Override
                public void onChronometerTick(Chronometer _chronometer) {
                    chronometer = _chronometer;
                }
            });
            //starten
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

            checkAllAnswers();
        }
    };


    private void showNextQuestion() {

        saveQuestion();

        //aktuelle Frage anzeigen wenn es noch Fragen gibt
        //Frage mit Antworten und Hinweis aus der Datenbank holen
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

                        stoppCountdown();

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

    private boolean nextQuestion(){
        return this.checkAnswer(1, option1) || this.checkAnswer(2, option2) || this.checkAnswer(3, option3) || this.checkAnswer(4, option4);
    }
    /**
     * Wenn ein Button mit der richtigen Antwort gedrückt wird, kommt man in das nächste Level,
     * also wird das Level hochgezählt
     * Wenn ein Button mit der falschen Antwort gedrückt wird, bekommt man ein Leben abgezogen
     */
    private void checkAllAnswers() {
        //das maximale Level
        int MAXLEVEL = 50;
        if(level_count >=1 && level_count <= MAXLEVEL) {

            if (this.checkAnswer(1, option1) || this.checkAnswer(2, option2) || this.checkAnswer(3, option3) || this.checkAnswer(4, option4))  {
                //wenn die Antwort richtig ist Level hochzählen
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
     *
     * wenn alle Leben verbraucht sind, endet das Spiel
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
            stoppCountdown();
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

    /**
     * Hier werden die Leben abgezogen, die man dadurch verliert, dass der Countdown abläuft
     * Außerdem kommt der Spieler zur nächsten Frage und der Countdown startet neu
     */
    private void CountdownAbgelaufen(){
        //Aktuelle Anzahl der Leben anzeigen
        showLeben();
            //Wenn der Countdown abläuft ein Leben abziehen
            if (verbleibendeZeit <= 10) {
                this.leben_count--;
                //neu anzeigen
                showLeben();
                //eine neue Frage soll erscheinen
                showNextQuestion();
                //und der Countdown muss wieder neu starten
                stoppCountdown();
                restartCountdown();
                //minigames
                minigames();
                //start
                startCountdown();
            }
    }

    /**
     * Popup zum nächsten Level
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

                    //neues Minigame
                    minigames();

                    //Button wieder klicken können, weil bei richtiger Antwort Buttons gesperrt werden
                    option1.setEnabled(true);
                    option2.setEnabled(true);
                    option3.setEnabled(true);
                    option4.setEnabled(true);

                    //Farbe wieder normal setzen (da vorher vllt rot oder grün war)
                     option1.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.button_round));
                     option2.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.button_round));
                     option3.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.button_round));
                     option4.setBackground(ContextCompat.getDrawable(Quiz.this, R.drawable.button_round));

                    if (countdownLaeuft) {
                        stoppCountdown();
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

    /***
     * hier wird der Zurückbutton für das jeweilige PopUp gesteuert.
     * Die Musik wird gestoppt und der Spieler gelangt zur Startseite
     * @param _popUp das jeweilige PopUp
     */
    private void setzeZuruekButton(Dialog _popUp){
        //wenn das PopUp verlassen wird (durch den Zurückbutton des Tablets)
        _popUp.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //schließen des PopUps
                dialog.dismiss();
                //beenden der Musik
                musik.endMusik();
                //weiterleiten zur Startseite
                zurStartseite();
            }
        });
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

        setzeZuruekButton(dialogLost);

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

       setzeZuruekButton(dialogWin);

        //Popup anzeigen lassen, wenn es nicht schließt
        if(!isFinishing())
            dialogWin.show();
    }

    private void popupInsertName(){
        AlertDialog.Builder insertUsername = new AlertDialog.Builder(this, R.style.AlertDialog);
        insertUsername.setTitle("Bitte Name eingeben");
        insertUsername.setMessage("Um auf die Highscoreliste zu gelangen, geben Sie bitte Ihren Namen ein:");

        final EditText username = new EditText(Quiz.this);
        //festlegen was man eingeben kann
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

        //wenn das PopUp verlassen wird (durch den Zurückbutton des Tablets)
        insertUsername.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //schließen des PopUps
                dialog.dismiss();
                //beenden der Musik
                musik.endMusik();
                //weiterleiten zur Startseite
                zurStartseite();
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

        // AlertDialog Hintergrund
        insertDialog.getWindow().setBackgroundDrawableResource(R.drawable.insertname_round);
        insertDialog.getWindow().setLayout(720,222);
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
                setzeCountdownText();
            }

            @Override
            public void onFinish() {
                //Wenn der Timer abgelaufen ist
                //Zeit auf 0 setzen
                verbleibendeZeit = 0;
                //auf der Textview anzeigen
                setzeCountdownText();

                //Der Countdown läuft nicht weiter
                countdownLaeuft = false;
            }
        }.start();
        //Der Countdown läuft
        countdownLaeuft= true;
    }

    /***
     * Hier wird der zu sehende Text der Textview aktualisiert
     * Die Zeit wird in Sekunden heruntergezählt
    */
    public void setzeCountdownText() {
        //umrechnen der verbleibenden Zeit in Sekunden
        int sekunden = (int) (verbleibendeZeit / 1000) % 60;

        //die verbleibende Zeit wird als String formatiert
        String zeitformatiert = String.format(Locale.getDefault(), "%02d", sekunden);

        //Die String mit der Zeit wird in die Textview gesetzt
        textview_Countdown.setText(zeitformatiert);
        //Zeit auch im unteren Layout anzeigen
        textView_Countdown_unten.setText("übrige Zeit: " + zeitformatiert);

        //In den letzten 10 Sekunden wird die Anzeige rot
        countdownTextRed();

        //Wenn der Countdown abläuft wird hier ein Leben abgezogen
        CountdownAbgelaufen();
    }

    /***
     * In den letzten 10 Sekunden wird die Anzeige des Countdowns rot rot
     */
    private void countdownTextRed(){
        //letzeen 10 Sekunden, also rot
        if (verbleibendeZeit < 11000) {
            textview_Countdown.setTextColor(Color.RED);
            textView_Countdown_unten.setTextColor(Color.RED);

        //Ansonsten bleibt die Anzeige wie voher
        } else {
            textview_Countdown.setTextColor(Color.BLACK);
            textView_Countdown_unten.setTextColor(Color.BLACK);
        }
    }

    /***
     * Der Countdown soll abgebrochen werden
    */
    private void stoppCountdown(){
        countdown.cancel();

        //Der Countdown läuft nicht weiter
        countdownLaeuft = false;
    }

    /***
     * Wenn der Countdown zurückgesetzt wird, muss die verbleibende Zeit wieder auf 30 Sekunden gesetzt werden
    */
    private void restartCountdown() {
        verbleibendeZeit= COUNTDOWN_IN_MILLIS;

        //die Anzeige der Textview aktualisieren
        setzeCountdownText();
    }
}