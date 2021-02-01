package com.example.Datenbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.Datenbank.HighscorelistTable.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasse der Datenbank für Einträge in dder Highscoreliste
 */
public class DatabaseHighscorelist extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "highscore.db";
    private SQLiteDatabase db;

    public DatabaseHighscorelist(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * Initalisiert die Datenbank
     * @param db Datenbank
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    this.db = db;
    final String SQL_CREATE_HIGHSCORELIST = "CREATE TABLE " +
            HighscoreTable.TABLE_NAME + " ( " +
            HighscoreTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            HighscoreTable.COLUMN_NAME + " TEXT, " +
            HighscoreTable.COLUMN_ZEIT + " TEXT, " +
            HighscoreTable.COLUMN_LEVEL + " INTEGER" +
            ")";

    db.execSQL(SQL_CREATE_HIGHSCORELIST);
    }

    /**
     * Updated Datenbank bei neuen Versionen
     * @param db Datenbank
     * @param oldVersion alter Version
     * @param newVersion neue Version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HighscoreTable.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Hinzufügen von Werten in die Datenbank
     * @param model Highscormodelobjket
     * @return boolean, um zu sehen ob sich etwas eingefügt hat
     */
    public boolean add(HighscoreModel model){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(HighscoreTable.COLUMN_NAME, model.username);
        cv.put(HighscoreTable.COLUMN_ZEIT, model.userzeit);
        cv.put(HighscoreTable.COLUMN_LEVEL, model.levelanzahl);
        long insert = db.insert(HighscoreTable.TABLE_NAME, null, cv);

        if(insert == -1){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Einträge werden in einer Liste gespeichert
     * @return Highscoreliste nach level geordnet
     */
    public List<HighscoreModel> getHighscorelist(){
        List<HighscoreModel> highscoreliste = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + HighscoreTable.TABLE_NAME + " ORDER BY " + HighscoreTable.COLUMN_LEVEL + " DESC", null);

        if(c.moveToFirst()){
            do{
                HighscoreModel highscore = new HighscoreModel();
                highscore.setUsername(c.getString(c.getColumnIndex(HighscoreTable.COLUMN_NAME)));
                highscore.setUserzeit(c.getString(c.getColumnIndex(HighscoreTable.COLUMN_ZEIT)));
                highscore.setLevelanzahl(c.getInt(c.getColumnIndex(HighscoreTable.COLUMN_LEVEL)));
                highscoreliste.add(highscore);
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return highscoreliste;
    }
}
