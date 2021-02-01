package com.example.Datenbank;

import android.provider.BaseColumns;

/**
 * Tabellenstruktur für Fragendatenbank
 */
public class QuizContract {

    private QuizContract(){}

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "fragen_tabelle";
        public static final String COLUMN_FRAGEN = "fragen";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANTWORT_NR = "antwort_nr";
        public static final String COLUMN_HINWEIS = "hinweis";
    }
}
