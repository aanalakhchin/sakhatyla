package com.example.smalley.sakhatyla2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LessonResultActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "SakhaTylaScores";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //need to get the questionNumber and pointsEarned from PlayActivity
        Bundle bundle = getIntent().getExtras();

        final int pointsEarned = bundle.getInt("pointsEarned");
        final int questionNumber = bundle.getInt("questionNumber");
        final int lessonNumber = bundle.getInt("lessonNumber");
        final int roundNumber = bundle.getInt("roundNumber");

        //storing scores
        SharedPreferences scores = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = scores.edit();
        if(lessonNumber == 0){
            editor.putInt("basicsScore", pointsEarned);
        }
        else if(lessonNumber == 1){
            editor.putInt("numbersScore", pointsEarned);
        }
        else if(lessonNumber == 2){
            editor.putInt("familyScore", pointsEarned);
        }
        else if(lessonNumber == 3){
            editor.putInt("phrasesScore", pointsEarned);
        }
        editor.commit();

        int totalNumberOfLessonQuestions = 12;

        TextView scoresText = (TextView) findViewById(R.id.scoresTextView);
        scoresText.setText("CONGRATULATIONS!\nYou have completed the lesson.\nYour score: " + pointsEarned + "/" + totalNumberOfLessonQuestions);

        final Button goBackToLessonsButton = (Button) findViewById(R.id.lessonsButton);
        goBackToLessonsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start the intent to go back to Lessons page
                Intent goToLessonsIntent = new Intent(LessonResultActivity.this, LessonsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pointsEarned", pointsEarned);
                bundle.putInt("questionNumber", questionNumber);
                bundle.putInt("lessonNumber", lessonNumber);
                bundle.putInt("roundNumber", roundNumber);

                goToLessonsIntent.putExtras(bundle);

                startActivity(goToLessonsIntent);
            }
        });

    }

    @Override
    public void onBackPressed() {
    }
}
