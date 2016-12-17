package com.example.smalley.sakhatyla2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LessonsActivity extends AppCompatActivity {
    int pointsEarned;
    int lessonNumber;
    int roundNumber;
    int questionNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting lessonNumber, roundNumber, questionNumber
        Bundle bundle = getIntent().getExtras();

        pointsEarned = bundle.getInt("pointsEarned");
        lessonNumber = bundle.getInt("lessonNumber");
        roundNumber = bundle.getInt("roundNumber");
        questionNumber = bundle.getInt("questionNumber");

        //LET US SAY FOR NOW THAT ALL POINTS ARE ANNULLED
        pointsEarned = 0;
        lessonNumber = 0;
        roundNumber = 0;
        questionNumber = 0;

        //shared preferences for displaying previous scores
        SharedPreferences prefs = getSharedPreferences("SakhaTylaScores", Context.MODE_PRIVATE);
        String basicsScoreKey = "basicsScore";
        int basicsScore = prefs.getInt(basicsScoreKey, 0);

        String numbersScoreKey = "numbersScore";
        int numbersScore = prefs.getInt(numbersScoreKey, 0);

        String familyScoreKey = "familyScore";
        int familyScore = prefs.getInt(familyScoreKey, 0);

        String phrasesScoreKey = "phrasesScore";
        int phrasesScore = prefs.getInt(phrasesScoreKey, 0);

        //allow to go to Basics class for every user
        //lessons listed; clicking on the lesson takes you to that lesson page
        Button basicsButton = (Button) findViewById(R.id.basicsButton);
        basicsButton.setText("Basics\nScore: " + basicsScore);

        basicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(LessonsActivity.this, PlayActivity.class);
                Intent intent = new Intent(LessonsActivity.this, PlayActivity.class);
                //start bundle
                Bundle bundle = new Bundle();
                //add data to bundle
                bundle.putInt("lessonNumber", 0);
                bundle.putInt("roundNumber", roundNumber);
                bundle.putInt("questionNumber", questionNumber);
                bundle.putInt("pointsEarned", pointsEarned);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        Button numbersButton = (Button) findViewById(R.id.numbersButton);
        numbersButton.setText("Numbers\nScore: " + numbersScore);
        //check if user passed Basics before allowing to go to Numbers
        //ADD CODE FOR THAT!!!
        numbersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LessonsActivity.this, PlayActivity.class);
                //start bundle
                Bundle bundle = new Bundle();
                //add data to bundle
                bundle.putInt("lessonNumber", 1);
                bundle.putInt("roundNumber", roundNumber);
                bundle.putInt("questionNumber", questionNumber);
                bundle.putInt("pointsEarned", pointsEarned);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        Button familyButton = (Button) findViewById(R.id.familyButton);
        familyButton.setText("Family\nScore: " + familyScore);
        //check if user passed Basics before allowing to go to Numbers
        //ADD CODE FOR THAT!!!
        familyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LessonsActivity.this, PlayActivity.class);
                //start bundle
                Bundle bundle = new Bundle();
                //add data to bundle
                bundle.putInt("lessonNumber", 2);
                bundle.putInt("roundNumber", roundNumber);
                bundle.putInt("questionNumber", questionNumber);
                bundle.putInt("pointsEarned", pointsEarned);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        Button phrasesButton = (Button) findViewById(R.id.phrasesButton);
        phrasesButton.setText("Phrases\nScore: " + phrasesScore);

        phrasesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LessonsActivity.this, PlayActivity.class);
                //start bundle
                Bundle bundle = new Bundle();
                //add data to bundle
                bundle.putInt("lessonNumber", 3);
                bundle.putInt("roundNumber", roundNumber);
                bundle.putInt("questionNumber", questionNumber);
                bundle.putInt("pointsEarned", pointsEarned);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }

    /*@Override
    public void onBackPressed() {
    }*/
}
