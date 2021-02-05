package com.example.Datenbank;

/**
 * Modelklasse für Fragen mit Antworten und Hinweisen
 */
public class QuizMeModel {
    public String fragen;
    public int antwort_nr;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public String hinweis;

    public QuizMeModel(String fragen, String option1, String option2, String option3, String option4, String hinweis, int antwortNr) {
        this.fragen = fragen;
        this.antwort_nr = antwortNr;
        this.hinweis = hinweis;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }

    public QuizMeModel() {
    }

    //setter und getter
    public void setFragen(String fragen) {
        this.fragen = fragen;
    }

    public String getFragen() {
        return fragen;
    }

    public void setAntwort_nr(int antwort_nr) {
        this.antwort_nr = antwort_nr;
    }

    public int getAntwort_nr() {
        return antwort_nr;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setHinweis(String hinweis) {
        this.hinweis = hinweis;
    }

    public String getHinweis() {
        return hinweis;
    }

}