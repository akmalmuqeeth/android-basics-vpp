package com.example.akmalmuqeeth.myquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.akmalmuqeeth.myquiz.data.Question;
import com.example.akmalmuqeeth.myquiz.data.Questions;

public class QuestionActivity extends AppCompatActivity {

    private Questions questions;
    private int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questions = new Questions();
        currentId = 0;

        showQuestion();
    }

    private void showQuestion(){
        Question question = questions.getQuestion(currentId);

        TextView title = (TextView) findViewById(R.id.QuestionTitle);
        title.setText(question.getTitle());
        
        
        TextView questionText = (TextView) findViewById(R.id.QuestionText);
        questionText.setText(question.getText());


        RadioButton answer1 = (RadioButton) findViewById(R.id.answer1);
        answer1.setText(question.getAnswers().get(0));

        RadioButton answer2 = (RadioButton) findViewById(R.id.answer2);
        answer2.setText(question.getAnswers().get(1));

        RadioButton answer3 = (RadioButton) findViewById(R.id.answer3);
        answer3.setText(question.getAnswers().get(2));

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setEnabled(false);

        Button backButton = (Button) findViewById(R.id.backButton);
        if (question.isFirstQuestion()) {
            backButton.setVisibility(View.INVISIBLE);
        } else {
            backButton.setVisibility(View.VISIBLE);
        }

    }


    public void answerSelected(View radioButtonThatWasClicked){
        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setEnabled(true);

        Question currQuestion = questions.getQuestion(currentId);

        /*int idClicked = radioButtonThatWasClicked.getId();
        switch (idClicked) {
            case R.id.answer1 : currQuestion.setUsersGuess(0); break;
            case R.id.answer2 : currQuestion.setUsersGuess(1); break;
            case R.id.answer3 : currQuestion.setUsersGuess(2); break;
        }
        System.out.println(currQuestion.getUsersGuess());*/

        RadioGroup group = (RadioGroup) findViewById(R.id.answers);
        int positionOfClickedButton = group.indexOfChild(radioButtonThatWasClicked);
        currQuestion.setUsersGuess(positionOfClickedButton);

        System.out.println(currQuestion.getUsersGuess());


    }

    public void nextQuestion(View view){

        currentId++;
        if(currentId == questions.size()) {
            currentId = 0;
        }

        RadioGroup group = (RadioGroup) findViewById(R.id.answers);
        group.clearCheck();
        showQuestion();
    }

    public void backButtonClick(View view){
        if(currentId == 0) return;

        currentId--;

        RadioGroup group = (RadioGroup) findViewById(R.id.answers);
        group.clearCheck();
        showQuestion();

    }
}
