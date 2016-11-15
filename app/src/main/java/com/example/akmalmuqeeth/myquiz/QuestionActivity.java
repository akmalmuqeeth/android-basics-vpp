package com.example.akmalmuqeeth.myquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.akmalmuqeeth.myquiz.data.Question;
import com.example.akmalmuqeeth.myquiz.data.Questions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {

    private Questions questions;
    private int currentId;

    private Date startOfCurrentSession;
    private Long accumulatedSeconds;


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("currentId", currentId);
        HashMap<Integer, Integer> savedAnswers = new HashMap<Integer,Integer>();
        for(int i =0; i < questions.size(); i++){
            Integer answer = questions.getQuestion(i).getUsersGuess();
            savedAnswers.put(i, answer);
        }

        Date stop = new Date();
        accumulatedSeconds += (stop.getTime() - startOfCurrentSession.getTime()) / 1000;

        savedInstanceState.putSerializable("savedAnswers", savedAnswers);
        savedInstanceState.putLong("accumulatedSeconds", accumulatedSeconds);
    }

    @Override
    public void onStop(){
        super.onStop();


    }

    @Override
    public void onRestart(){
        super.onRestart();
        startOfCurrentSession = new Date();

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        currentId = savedInstanceState.getInt("currentId");

        Map<Integer,Integer> savedAnswers = (Map<Integer,Integer>) savedInstanceState.getSerializable("savedAnswers");
        for (int id: savedAnswers.keySet()){
            Integer answer = savedAnswers.get(id);
            questions.getQuestion(id).setUsersGuess(answer);
        }
        accumulatedSeconds = savedInstanceState.getLong("accumulatedSeconds");
        showQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questions = new Questions();
        currentId = 0;

        startOfCurrentSession = new Date();
        accumulatedSeconds = 0L;

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

        if(questions.getQuestion(currentId).isLastQuestion()){

            Date stop = new Date();
            Long seconds = (stop.getTime() - startOfCurrentSession.getTime()) / 1000;
            seconds += accumulatedSeconds;


            Intent scoreActivityIntent = new Intent(this, ScoreActivity.class);
            //send data to another activity
            scoreActivityIntent.putExtra("score", questions.calculateScore());
            scoreActivityIntent.putExtra("seconds", seconds);
            startActivity(scoreActivityIntent);
            //hitting the back button from the score will take the user back to splash screen
            finish();
            return;
        }


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
