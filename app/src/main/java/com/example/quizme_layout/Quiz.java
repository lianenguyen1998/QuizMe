package com.example.quizme_layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizme3.DatabaseHelper;
import com.example.quizme3.QuizContract;
import com.example.quizme3.QuizMeModel;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Quiz extends AppCompatActivity {

    TextView level;
    TextView leben;
    TextView zeit;
    TextView logo;
    private TextView frage;
    private Button hinweis;
    private Button option1;
    private Button option2;
    private Button option3;
    private Button option4;
    private Button quit;

    private ColorStateList textColorDefault;

    private List<QuizMeModel> fragenliste;
    private int questionCounter;
    private int questionCountTotal;
    private boolean answered;
    private QuizMeModel currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        frage = findViewById(R.id.question);
        hinweis = findViewById(R.id.hinweis);
        option1 = findViewById(R.id.choice1);
        option2 = findViewById(R.id.choice2);
        option3 = findViewById(R.id.choice3);
        option4 = findViewById(R.id.choice4);
        textColorDefault = option1.getTextColors();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        fragenliste = dbHelper.getAllQuestions();
        questionCountTotal = fragenliste.size();
        Collections.shuffle(fragenliste);

        showNextQuestion();
        hinweis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop up hinweis
                //erstmal ein Toast?
                Toast.makeText(getApplicationContext(), currentQuestion.getHinweis() ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showNextQuestion(){
        option1.setTextColor(textColorDefault);
        option2.setTextColor(textColorDefault);
        option3.setTextColor(textColorDefault);
        option4.setTextColor(textColorDefault);


        if(questionCounter < questionCountTotal){
            currentQuestion = fragenliste.get(questionCounter);
            frage.setText(currentQuestion.getFragen());
            option1.setText(currentQuestion.getOption1());
            option2.setText(currentQuestion.getOption2());
            option3.setText(currentQuestion.getOption3());
            option4.setText(currentQuestion.getOption4());

            questionCounter++;
            answered = false;

        } else {
            finishQuiz();
        }
    }

    private void finishQuiz(){
        finish();
    }
}