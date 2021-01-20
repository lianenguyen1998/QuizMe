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

    /***
     * das neue startCoundown
     */
    public CountdownQuiz(TextView _textview_timer, TextView _timerTest){
        this.countdown= new CountDownTimer(COUNTDOWN_ZEIT,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Zeit herunterzählen
                verbleibendeZeit = millisUntilFinished;
                //auf der Textview anzeigen
                updateCountdownText(_textview_timer, _timerTest);
            }

            @Override
            public void onFinish() {
                //Wenn der Timer abgelaufen ist
                //Zeit auf 0 setzen
                verbleibendeZeit = 0;
                //auf der Textview anzeigen
                updateCountdownText( _textview_timer,_timerTest);

                //Der Countdown läuft nicht weiter
                timerRunning = false;
            }
        }.start();
        //Der Countdown läuft
        timerRunning= true;

    }

    /***
     * Hier wird der Countdown auf 30 Sekunden gesetzt und gestartet
     * Der Countdown wird heruntergezählt und auf 0 gesetzt

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
    } */

    public void updateCountdownText(TextView _textview_timer, TextView _timerTest) {
        //umrechnen der verbleibenden Zeit in Sekunden
        int sekunden = (int) (verbleibendeZeit / 1000) % 60;

        //die verbleibende Zeit wird als String formatiert
        String zeitformatiert = String.format(Locale.getDefault(), "%02d", sekunden);

        //Die String mit der Zeit wird in die Textview gesetzt
        _textview_timer.setText(zeitformatiert);
        //Zeit auch im unteren Layout anzeigen
        _timerTest.setText("übrige Zeit: " + zeitformatiert);

        //In den letzten 10 Sekunden wird die Anzeige rot
        countdownTextRed( _textview_timer, _timerTest);

        //Wenn der Countdown abläuft wird hier ein Leben abgezogen
        ///////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////////
        //countlebenCountdown();
    }


    private void countdownTextRed(TextView _textview_timer, TextView _timerTest){
        //letzeen 10 Sekunden, also rot
        if (verbleibendeZeit < 11000) {
            _textview_timer.setTextColor(Color.RED);
            _timerTest.setTextColor(Color.RED);

            //Ansonsten bleibt die Anzeige wie voher
        } else {
            ////////////////////////////////////////////////////////////////////
            _textview_timer.setTextColor(Color.BLACK);
        }
    }


    private void pauseCountdown(){
        countdown.cancel();

        //Der Countdown läuft nicht weiter
        timerRunning = false;
    }


    private void restartCountdown(TextView _textview_timer, TextView _timerTest) {
        verbleibendeZeit=30000;

        //die Anzeige der Textview aktualisieren
        updateCountdownText( _textview_timer,  _timerTest);
    }
}
