package com.example.Datenbank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.Datenbank.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "FRAGEN.db";

    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    /**
     * Initialisiert die Datenbanktabelle und füllt sie mit Werten
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_FRAGENTABELLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                QuestionsTable.COLUMN_FRAGEN + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANTWORT_NR + " INTEGER, " +
                QuestionsTable.COLUMN_HINWEIS + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_FRAGENTABELLE);
        fillQuestionsTable();
    }

    /**
     * Löscht die Tabelle bei einem neuen Update und initialisiert sie neu
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Füllt die Tabelle mit ihren Werten
     */
    private void fillQuestionsTable() {
        QuizMeModel q1 = new QuizMeModel("Wie heißt die Hauptstadt der Slowakei?", "Sofia", "Prag", "Bratislava", "Ljubljan", "Das Wahrzeichen der Stadt ist die Burg", 3);
        add(q1);
        QuizMeModel q2 = new QuizMeModel("Wie viele Zähne hat ein erwachenser Mensch normalerweise?", "26", "30", "32", "36", "Auf jedenfall um die 30", 3);
        add(q2);
        QuizMeModel q3 = new QuizMeModel("Wer wählt den Bundeskanzler?", "Bundeskanzler", "Bundestag", "Bundesrat", "Bundesversammlung", "Nicht nur eine Person",4);
        add(q3);
        QuizMeModel q4 = new QuizMeModel("Wofür steht die Abkürzung KGaA?", "Kreditgesellschaft auf Aktien", "Kommanditgesellschaft auf Aktien", "Kardinalgesellschaft auf Aktien", "Kompetenzgesellschaft auf Aktien", "Sicher was mit Aktien", 2);
        add(q4);
        QuizMeModel q5 = new QuizMeModel("Welches Land ist kein Mitglied im Sicherheitsrat der Vereinten Nationen?", "USA", "Russland", "Deutschland", "China", "Ein für dich sehr bekanntes Land", 3);
        add(q5);
        QuizMeModel q6 = new QuizMeModel("Welches dieser Tiere hält keinen Winterschlaf?", "Eichhörnchen", "Fledermaus", "Igel", "Siebenschläfer", "Es ist ein Nagetier", 1);
        add(q6);
        QuizMeModel q7 = new QuizMeModel("Wie heißt die Hauptstadt von Thüringen?", "Magdeburg", "Erfurt", "Dresden", "Potsdam", "Nachbarstadt ist Weimar", 2);
        add(q7);
        QuizMeModel q8 = new QuizMeModel("In welcher Einheit wird der elektrische Widerstand gemessen?", "Ohm", "Volt", "Ampere", "Watt", "Ähm Widerstand?", 1);
        add(q8);
        QuizMeModel q9 = new QuizMeModel("Was ist ein Oxymoron?", "Ein innerer Widerspruch", "Ein Versfuß", "Eine Wiederholung", "Eine Frageform", "Das Wort Feuerwasser ist ein Oxymoron", 1);
        add(q9);
        QuizMeModel q10 = new QuizMeModel("Wofür steht das L in RTL?", "London", "Luxenbourg", "Liechtenstein", "Los Angeles", "Eine Stadt in Deutschland? oder so ähnlich", 2);
        add(q10);
        QuizMeModel q11 = new QuizMeModel("Wo fanden die Olympischen Spiele 1996 statt?", "Atlanta", "Turin", "Barcelona", "Los Angeles", "Irgendwo in Amerika", 1);
        add(q11);
        QuizMeModel q12 = new QuizMeModel("Wie heißt die Hauptstadt von Äthiopien?", "Nairobi", "Mogadischu", "Harare", "Addis Adeba", "Es hat mindestens zwei a's im Namen", 4);
        add(q12);
        QuizMeModel q14 = new QuizMeModel("Was soll Cäsar gesagt haben, als er den Rubikon überquerte?", "veni, vidi, vici", "divide et empera", "alea iacta est", "et tu, brute", "Er sagt:'Die Würfel sind gefallen'", 3);
        add(q14);
        QuizMeModel q15 = new QuizMeModel("Wie viele Oscars gewann der Film Titanic?", "10", "11", "12", "13", "Mehr als 10 definitiv", 2);
        add(q15);
        QuizMeModel q16 = new QuizMeModel("Teneriffa, Gran Canaria und Fuerteventura gehören zu den ...?", "kanarischen Inseln", "Balearen", "Karibischen Inseln", "Azoren", "Ich meine es sind Inseln", 1);
        add(q16);
        QuizMeModel q17 = new QuizMeModel("Wie beginnt Artikel 1 des deutschen Grundgesetztes?", "Alle Menschen sind vor dem Gesetz gleich.", "Jeder hat das Recht auf die freie Entfaltung seiner Persönlichkeit.", "Jeder hat das Recht, seine Meinung durch Wort, Schrift und Bild frei zu äußern.", "Die Würde des Menschen ist unantastbar.", "Etwas das niemand ändern kann", 4);
        add(q17);
        QuizMeModel q18 = new QuizMeModel("Wer gilt als Verfasser der amerikanischen Unabhängigkeitserklärung?", "Thomas Jefferson", "Benjamin Franklin", "George Washington", "John Adams", "Er war einer der Gründerväter Amerikas", 1);
        add(q18);
        QuizMeModel q19 = new QuizMeModel("Welche Adresse ist mit Sherlock Holmes verbunden?", "Downing Street 10", "Abby Road 42", "221b Baker Street", "Princess Street 7", "Es ist eine 'Street' ", 3);
        add(q19);
        QuizMeModel q20 = new QuizMeModel("Wie lautet das chemische Symbol für Blei?", "Bl", "Pb", "Be", "Pt", "Es fängt mit 'P' an", 2);
        add(q20);
        QuizMeModel q21 = new QuizMeModel("Wie viele Planeten hat das Sonnensystem?", "8", "9", "10", "11", "Nicht mehr als 10", 1);
        add(q21);
        QuizMeModel q22 = new QuizMeModel("In welchem Meer liegt die Insel Hawaii?", "Atlantischer Ozean", "Indischer Ozean", "Karibisches Meer", "Pazifischer Ozean", "Einer der Ozeane auf jedenfall", 4);
        add(q22);
        QuizMeModel q23 = new QuizMeModel("Wer verbreitete das heliozentrische Weltbild?", "Galileo Galilei", "Nikolaus Kopernikus", "Leonardo da Vinci", "Aristoteles", "Er war Astronom und Arzt", 2);
        add(q23);
        QuizMeModel q24 = new QuizMeModel("An welchem Datum fiel die Berliner Mauer?", "3.Oktober 1990", "2.November 1990", "9.November 1989", "8.Oktober 1989", "Es war im November", 3);
        add(q24);
        QuizMeModel q25 = new QuizMeModel("Was ist ein Sonett?", "Eine Stichwaffe", "Musikinstrument", "Ein Pilz", "Eine Gedichtsform", "Es hat ein Rhythmus", 4);
        add(q25);
        QuizMeModel q26 = new QuizMeModel("Wie heißt die Schicht der Atmosphäre, die der Erde am nächsten ist?", "Troposphäre", "Stratosphäre", "Mesosphäre", "Thermosphäre", "Ich meine es fängt mit 'T' an", 1);
        add(q26);
        QuizMeModel q27 = new QuizMeModel("Wie viel Prozent der Erde sind circa von Wasser bedeckt?", "50 Prozent", "60 Prozent", "70 Prozent", "80 Prozent", "Es sollte mehr als die Hälfte der Erde sein", 3);
        add(q27);
        QuizMeModel q28 = new QuizMeModel("wie heißt die Hauptstadt des US-Bundesstaates Kentucky?", "Hamborg", "Frankfort", "Borlin", "Dusseldorf", "In Deutschland gibt es die Stadt mehrmals", 2);
        add(q28);
        QuizMeModel q29 = new QuizMeModel("Wie lang ist die chinesische Mauer?", "12.000 Kilometer", "15.000 Kilometer", "18.000 Kilometer", "21.000 Kilometer", "Es ist einer der höheren Werte", 4);
        add(q29);
        QuizMeModel q30 = new QuizMeModel("Der wievielte US-Präsident war Barack Obama?", "42.", "43.", "44.", "45.", "Etwas in der Mitte", 3);
        add(q30);
        QuizMeModel q31 = new QuizMeModel("Welche Staatsform hat England?", "Parlamentarische Monarchie", "Konstitutionelle Monarchie", "Absolute Monarchie", "Ständische Monarchie", "Es ist keine vollständige Monarchie.", 1);
        add(q31);
        QuizMeModel q32 = new QuizMeModel("Welches ist das höchste Amt in Deutschland?", "Bundespräsident", "Bundeskanzler", "Bundestagspräsident", "Oberster Richter am Bundesverfassungsgericht", "Der Richter sollte immer neutral sein", 1);
        add(q32);
        QuizMeModel q33 = new QuizMeModel("Wie groß ist die Summe der Winkel eines Dreiecks?", "45 Grad", "90 Grad", "180 Grad", "360 Grad", "Ein voller Kreis ist 360 Grad", 3);
        add(q33);
        QuizMeModel q34 = new QuizMeModel("Wie beginnt die Kreiszahl Pi?", "3,1415", "3,8485", "3,3435", "2,6465", "4,... ist definitiv zu viel", 1);
        add(q34);
        QuizMeModel q35 = new QuizMeModel("Wer schrieb die Harry Potter Bücher?", "E.L. James", "J.R.R. Tolkien", "George R.R. Martin", "Joanne K. Rowling", "'J' sollte im Namen vorhanden sein", 4);
        add(q35);
        QuizMeModel q36 = new QuizMeModel("Wie heißt der Assistent von Sherlock Holmes?", "Dr.Jekyll", "Dr.Gibson", "Dr.Watson", "Dr.House", "Dr.Jekyll war ein gutherziger Arzt", 3);
        add(q36);
        QuizMeModel q37 = new QuizMeModel("Ein Schattenporträt aus einem Stück Papier ist ein?", "Scherenschnitt", "Messerschnitt", "Holzschnitt", "Bürstenschnitt", "Mit was schneidet man wohl Papier?", 1);
        add(q37);
        QuizMeModel q38 = new QuizMeModel("In welchem Land in Europa ist die Geschwindigkeit auf Autobahnen nicht generell begrenzt?", "Schweden", "Deutschland", "Italien", "Frankreich", "Wo wohl", 2);
        add(q38);
        QuizMeModel q39 = new QuizMeModel("Was versteht man in der musikalischen Dynamik unter der Abkürzung 'ff?'", "fortissimo", "medioforte", "duoforte", "fortino", "Sollte sehr laut sein", 1);
        add(q39);
        QuizMeModel q40 = new QuizMeModel("Was ist eine veraltete Maßeinheiten für Energie?", "Farbe", "Kalorie", "Exzem", "Kraftstoff", "Farbe ist eine Energie? sicher nicht", 2);
        add(q40);
        QuizMeModel q41 = new QuizMeModel("Wie heißt der Ausfallschritt bei der Landung eines Skispringers?", "Tram", "Trinitus", "Television", "Telemark", "Es ist ebenso eine norwegische Provinz", 4);
        add(q41);
        QuizMeModel q42 = new QuizMeModel("Was dient als Nagelschutz statt Hufbeschlag für Huftiere?", "Barhuf", "Knochenschuh", "Hufschuh", "Hufsandale", "Also wie Schuhe?", 3);
        add(q42);
        QuizMeModel q43 = new QuizMeModel("Eine mitteralterliche Schlagwaffe war der?", "Rittersporn", "Morgenstern", "Stiefelsporn", "Abendstern", "Ich erinnere mich an einen Stern", 2);
        add(q43);
        QuizMeModel q44 = new QuizMeModel("Die übertriebene und ungesunde Beschäftigung mit sich selbst nennt man auch?", "Warzenschau", "Nasenschau", "Leichenschau", "Nabelschau", "Leichenschau wird von einem Arzt durchgeführt", 4);
        add(q44);
        QuizMeModel q45 = new QuizMeModel("Von welchen Tieren wird der Farbstoff 'Sepia' gewonnen?", "Chamäleons", "Purpurschnecken", "Tintenfische", "Schildläuse", "Fachsprachlich auch 'Coleoiden' genannt",3);
        add(q45);
        QuizMeModel q46 = new QuizMeModel("Wie nennt man im Fußball den Versuch eines Spielers, ein 'Foul' vorzutäuschen?", "Ente", "Schwalbe", "Pinguin", "Amsel", "Der Vogel kann fliegen", 2);
        add(q46);
        QuizMeModel q47 = new QuizMeModel("Wie wird eine halb gefrorene Speise aus Fruchtsaft, Fruchtpüree und Zucker genannt?", "Gelee", "Sorbet", "Kalter Hund", "Mousse", "Der Kalte Hund besteht aus Schockolade und Keksen", 2);
        add(q47);
        QuizMeModel q48 = new QuizMeModel("Wo werden entlegene Regionen 'Outback' genannt?", "Südafrika", "China", "Island", "Australien", "Englisch ist dort etwas anders", 4);
        add(q48);
        QuizMeModel q49 = new QuizMeModel("Wie nennt man in Frankreich umgangssprachlich einen Polizisten?", "Flip", "Cloque", "Flac", "Flic", "Cloque passt nicht dazu", 4);
        add(q49);
        QuizMeModel q50 = new QuizMeModel("Welche Gestalt aus der griechischen Mythologie starb, weil sie der Sonne zu nah kam?", "Tantalos", "Sisyphos", "Daidalos", "Ikarus", "Sisyphos hat mehrmals den Tod überlistet", 4);
        add(q50);

    }

    /**
     * Fügt eine Frage mit ihren Möglichkeiten und Antwort hinzu
     * @param model
     */
    public void add(QuizMeModel model) {

        ContentValues cv = new ContentValues();

        cv.put(QuestionsTable.COLUMN_FRAGEN, model.getFragen());
        cv.put(QuestionsTable.COLUMN_OPTION1, model.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, model.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, model.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, model.getOption4());
        cv.put(QuestionsTable.COLUMN_ANTWORT_NR, model.getAntwort_nr());
        cv.put(QuestionsTable.COLUMN_HINWEIS, model.getHinweis());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    /**
     * Gibt alle Fragen in einer Liste zurück
     * @return vollständige Liste mit allen Fragen
     */
    public List<QuizMeModel> getAllQuestions() {
        List<QuizMeModel> fragenliste = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                QuizMeModel fragen = new QuizMeModel();
                fragen.setFragen(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_FRAGEN)));
                fragen.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                fragen.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                fragen.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                fragen.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                fragen.setAntwort_nr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANTWORT_NR)));
                fragen.setHinweis(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_HINWEIS)));
                fragenliste.add(fragen);
            } while (c.moveToNext());
        }

        c.close();
        return fragenliste;
    }

}
