package com.example.Datenbank;

import android.provider.BaseColumns;

public class HighscorelistTable {

    public HighscorelistTable(){}

    public static class HighscoreTable implements BaseColumns {
        public static final String TABLE_NAME = "highscore_tabelle";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ZEIT = "zeit";
        public static final String COLUMN_LEVEL = "levelanzahl";
    }
}
