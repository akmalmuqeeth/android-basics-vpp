package com.example.akmalmuqeeth.myquiz;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    public void startAgain(View view){
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
        finish();
    }

    public void share(View view)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"I took an exciting quiz and my score was " + score);
        startActivity(intent);
    }
}
