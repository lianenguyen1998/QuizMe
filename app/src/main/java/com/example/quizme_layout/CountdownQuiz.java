package com.example.quizme_layout;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;
import com.example.quizmetime.Countdown;
import java.util.Locale;

public class CountdownQuiz {


    CountDownTimer countdown;
    private static final long COUNTDOWN_ZEIT = 30000;
    long verbleibendeZeit;
    boolean timerRunning;
    TextView oben;
    TextView unten;

    /***
     * das neue startCoundown
     */
    public CountdownQuiz(TextView _textview_timer, TextView _timerTest){
        this.oben = _textview_timer;
        this.unten = _timerTest;
        this.countdown= new CountDownTimer(COUNTDOWN_ZEIT,1000) {
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


    /// voher start
    public void resetCountdown(){
        //den Countdown zuweisen auf 30 Sekunden
        countdown = new CountDownTimer(30000, 1000) {
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

    public void startCountdown(){
        if (timerRunning) {
            pauseCountdown();
            restartCountdown();
        }
        startCountdown();
    }
    public void updateCountdownText() {
        //umrechnen der verbleibenden Zeit in Sekunden
        int sekunden = (int) (verbleibendeZeit / 1000) % 60;

        //die verbleibende Zeit wird als String formatiert
        String zeitformatiert = String.format(Locale.getDefault(), "%02d", sekunden);

        //Die String mit der Zeit wird in die Textview gesetzt
        this.oben.setText(zeitformatiert);
        //Zeit auch im unteren Layout anzeigen
        this.unten.setText("übrige Zeit: " + zeitformatiert);

        //In den letzten 10 Sekunden wird die Anzeige rot
        countdownTextRed();

        //Wenn der Countdown abläuft wird hier ein Leben abgezogen
        ///////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////
        //countlebenCountdown();
    }


    public void countdownTextRed(){
        //letzeen 10 Sekunden, also rot
        if (verbleibendeZeit < 11000) {
            this.oben.setTextColor(Color.RED);
            this.unten.setTextColor(Color.RED);

            //Ansonsten bleibt die Anzeige wie voher
        } else {
            ////////////////////////////////////////////////////////////////////
            this.oben.setTextColor(Color.BLACK);
            this.unten.setTextColor(Color.BLACK);

        }
    }


    public void pauseCountdown(){
        countdown.cancel();

        //Der Countdown läuft nicht weiter
        timerRunning = false;
    }


    public void restartCountdown() {
        verbleibendeZeit=30000;

        //die Anzeige der Textview aktualisieren
        updateCountdownText();
    }

    public boolean getTimerRunning()
    {
        return this.timerRunning;
    }

    public long getVerbleibendeZeit(){
        return this.verbleibendeZeit;
    }
}
