package com.example.quizmetime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizme_layout.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //für die Preference bei firstAppStart
    final String prefNameFirstStart = "firstAppStart";

    // für die Peference bei safeLevel
    final String prefLevel = "currentLevel";

    int currentLevel;

    //richtet sich nach der Datenbank
    final int maxLevel = 50;



    TextView textview_timer;

    //30 Sekunden
    private static final long COUNTDOWN_IN_MILLIS = 30000;

    private ColorStateList textColor_countdown;
    private CountDownTimer countdown;
    private long verbleibendeZeit;

    @Override
    /***
     *
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        //Timer zuweisen
        textview_timer = findViewById(R.id.);
        //Timer Dauer
        /***long dauer = TimeUnit.MINUTES.toMillis(1);

        new CountDownTimer(dauer, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                String sDauer = String.format(Locale.GERMAN, "%02d : %02d ",
                        TimeUnit.MILLISECONDS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(1),
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(1)));

                textview_timer.setText(sDauer);
            }

            @Override
            public void onFinish() {
                textview_timer.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "Endeee", Toast.LENGTH_LONG).show();
            }
        }.start();
        **/
        textColor_countdown = textview_timer.getTextColors();
    }

    /***
     *
     */
    public void loadLevel() {
        SharedPreferences preferencesLoad = getSharedPreferences(prefLevel, MODE_PRIVATE);
        //Level 1, wenn noch kein Int zurückgegeben wird

        currentLevel = preferencesLoad.getInt(prefLevel, 1);

        //nur wenn es noch weitere Level gibt
        if (currentLevel <= maxLevel)
        {
            //SQLiteDatabase
            //Cursor
        }
    }

    /***
     *
     */
    public void safeLevel()
    {
        SharedPreferences preferencesLevel = getSharedPreferences(prefLevel, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencesLevel.edit();

        //Das Level wird in einem int wert gesetzt
        editor.putInt(prefLevel, currentLevel);
        editor.commit();

    }

    /***
     *
     */
    public void LevelCompleted()
    {

    }

    /***
     *
     * @return
     */
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
            return true;
        }
        else
        {
            return false;       //die App wurde schon gestartet
        }
    }

    /***
     *
     */
    public void createDatabase()
    {
        //wenn die App gestartet wird, soll die Datenbank erstellt werden
        //SQLiteDatabase database = ....
    }

    private void showNextQuestions()
    {
        //Countdown starten
        startCountdown();
    }

    private void startCountdown()
    {
        countdown = new CountDownTimer(verbleibendeZeit, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                verbleibendeZeit = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
              verbleibendeZeit= 0;
              updateCountdownText();
              // checkAnswer();
                //dort countdown.cancel();
            }
        }.start();
    }

    private void updateCountdownText()
    {
        int minuten = (int) (verbleibendeZeit/1000) / 60;
        int sekunden = (int) (verbleibendeZeit/1000)%60;

        String zeitformatiert  = String.format(Locale.getDefault(), "\"%02d : %02d", minuten, sekunden);
        textview_timer.setText(zeitformatiert);

        if(verbleibendeZeit < 10000)
        {
            textview_timer.setTextColor(Color.RED);
        }
        else
        {
            textview_timer.setTextColor(textColor_countdown);
        }
    }

}