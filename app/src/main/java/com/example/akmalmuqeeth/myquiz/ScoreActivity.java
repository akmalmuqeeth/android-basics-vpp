package com.example.akmalmuqeeth.myquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.akmalmuqeeth.myquiz.R.id.score;

public class ScoreActivity extends AppCompatActivity {

    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        Intent intent = this.getIntent();
        score = intent.getExtras().getInt("score");

        TextView scoreView = (TextView)findViewById(R.id.score);

        Resources res = getResources();
        String[] strings = res.getStringArray(R.array.feedback_array);

        scoreView.setText(strings[score]);
    }
}
