package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Einstellung extends AppCompatActivity {

    // Hintergrundmusik Variable
     private Musik musik;

    // SwitchButton Variable
    private Switch musikSwitch;

    // Shared preferences Variablen
    private static String MY_PREFS = "switch_prefs";
    private static String SWITCH_STATUS = "switch_status";

    boolean switch_status;

    // Zum Speichern des Switchs -> letzten Stand (an/aus)
    SharedPreferences myPreferences;
    SharedPreferences.Editor myEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.einstellung);

        // Hintergrundanimation
        HintergrundAnimation hintergrundAnimation = new HintergrundAnimation(Einstellung.this,4000);

        // Hintergrundmusik (wird gestartet)
        musik = new Musik(this);

        musikSwitch = (Switch) findViewById(R.id.musik);

        myPreferences = getSharedPreferences(MY_PREFS, MODE_PRIVATE);
        myEditor =  getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();

        // default Wert -> false
        switch_status = myPreferences.getBoolean(SWITCH_STATUS, false);

        musikSwitch.setChecked(switch_status);

        musikSwitch.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean defValue) {
                        if (buttonView.isChecked()) {

                            // Musik an
//                            musik.startMusik();

                            // Switch wird auf true gesetzt -> bleibt an
                            myEditor.putBoolean(SWITCH_STATUS, true);
                            myEditor.apply();
                            musikSwitch.setChecked(true);

                        } else {

                            // Musik aus/pausieren
//                            musik.pauseMusik();

                            // Switch wird auf false gesetzt -> bleibt aus
                            myEditor.putBoolean(SWITCH_STATUS, false);
                            myEditor.apply();
                            musikSwitch.setChecked(false);

                        }
                    }
                    }
            );
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
        Intent intent = new Intent(this, Startseite.class);
        startActivity(intent);

    }

}