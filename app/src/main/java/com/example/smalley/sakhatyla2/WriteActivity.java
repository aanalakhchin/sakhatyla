package com.example.smalley.sakhatyla2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WriteActivity extends AppCompatActivity {
    private static Context context;
    int pointsEarned;
    int lessonNumber;
    int roundNumber;
    int questionNumber;
    String word;
    String correctAnswer;
    String userAnswer;

    private ProgressBar mProgress;
    private int mProgressStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        WriteActivity.context = getApplicationContext();
        //getting lessonNumber, roundNumber, questionNumber
        Bundle bundle = getIntent().getExtras();

        pointsEarned = bundle.getInt("pointsEarned");
        lessonNumber = bundle.getInt("lessonNumber");
        roundNumber = bundle.getInt("roundNumber");
        questionNumber = bundle.getInt("questionNumber");
        //we will start again, since it is different set of questions


        //progress bar stuff
        mProgress = (ProgressBar) findViewById(R.id.progressBar3);
        mProgressStatus = pointsEarned;
        mProgress.setProgress(mProgressStatus);

        final Question question = getQuestion(lessonNumber, roundNumber, questionNumber);
        correctAnswer = question.getCorrectAnswer();
        word = question.getQuestionText();
        TextView textView = (TextView) findViewById(R.id.writeTextView);
        textView.setText("Write '" + word + "' in Sakha");

        final EditText mEditText = (EditText) findViewById(R.id.editText);
        mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        mEditText.setImeActionLabel("Submit", KeyEvent.KEYCODE_ENTER);

        final Button submitButton = (Button) findViewById(R.id.submitButtonWrite);
        submitButton.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View view){
                        userAnswer = mEditText.getText().toString();
                        //just to make sure case doesn't matter if answer is right
                        userAnswer = userAnswer.toLowerCase();
                        onClickSubmitButton(view);
                        submitButton.setEnabled(false);
                    }
                }
        );
    }

    public void onClickSubmitButton(View v){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.correct_wrong_toast_layout,
                (ViewGroup) findViewById(R.id.cw_toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);

        if(!correctAnswer.equals(userAnswer)){
            text.setText("Wrong. Correct answer is " + correctAnswer + ".");

        }
        else {
            text.setText("Correct!");
            pointsEarned++;

        }

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
        //if it is the last question in the lesson
        if(questionNumber == 3) {
            //open LessonResultActivity
            final Intent goToResultsIntent = new Intent(WriteActivity.this, LessonResultActivity.class);

            Bundle resultsBundle = new Bundle();
            //adding data to the bundle
            resultsBundle.putInt("lessonNumber", lessonNumber);
            resultsBundle.putInt("roundNumber", roundNumber);
            resultsBundle.putInt("questionNumber", 0);
            resultsBundle.putInt("pointsEarned", pointsEarned);

            goToResultsIntent.putExtras(resultsBundle);

            Handler x = new Handler();

            x.postDelayed(new Runnable() {
                public void run() {

                    startActivity(goToResultsIntent);
                }
            }, 2000);

        }
        else {
            questionNumber++;
            //intent to go to the next question
            final Intent intent = new Intent(WriteActivity.this, WriteActivity.class);
            //start bundle
            Bundle bundle = new Bundle();
            //add data to bundle
            bundle.putInt("lessonNumber", lessonNumber);
            bundle.putInt("roundNumber", roundNumber);
            bundle.putInt("questionNumber", questionNumber);
            bundle.putInt("pointsEarned", pointsEarned);

            intent.putExtras(bundle);


            Handler x = new Handler();

            x.postDelayed(new Runnable() {
                public void run() {

                    startActivity(intent);
                }
            }, 2000);
        }


    }

    public static Context getAppContext() {
        return WriteActivity.context;
    }

    public String loadJSON() {
        String line = "";
        StringBuilder returnString = new StringBuilder();
        InputStream fIn = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        Context context = getAppContext();
        try {
            fIn = context.getResources().openRawResource(R.raw.valid);
            isr = new InputStreamReader(fIn);
            input = new BufferedReader(isr);

            while ((line = input.readLine())!=null){
                returnString.append(line);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
        String s = (String)returnString.toString();
        return s;
    }

    public Question getQuestion(int lessonIndex, int roundIndex, int questionIndex) {
        String lessonsStr = "lessons";
        String roundsStr = "rounds";
        String questionsStr = "questions";
        String questionTextStr = "questionText";
        String answersStr = "answers";
        String answerTextStr = "answerText";
        String isCorrectAnswerStr = "isCorrectAnswer";

        // fullQuestion structure: questionText, rightAnswer, answers.
        String[] answers = new String[4];
        String questionText;
        String rightAnswer = "";

        Question question;
        String isCorrectAnswerString;

        try {
            //Log.v("getQuestion", "It's working!");
            String s = loadJSON();
            JSONObject jsonQuiz = new JSONObject(s);
            //Log.v("jsonQuiz", "It's working!");
            JSONArray jsonLessons = jsonQuiz.getJSONArray(lessonsStr);
            //Log.v("jsonLessons", "It's working!");
            JSONObject jsonLesson = jsonLessons.getJSONObject(lessonIndex);
            //Log.v("jsonLesson", "It's working!");
            JSONArray jsonRounds = jsonLesson.getJSONArray(roundsStr);
            //Log.v("jsonRounds", "It's working!");
            JSONObject round = jsonRounds.getJSONObject(roundIndex);
            //Log.v("round", "It's working!");
            JSONArray jsonQuestions = round.getJSONArray(questionsStr);
            //Log.v("jsonQuestions", "It's working!");
            JSONObject jsonQuestion = jsonQuestions.getJSONObject(questionIndex);
            //JSONArray jsonQuestion = jsonQuestions.getJSONArray(questionIndex);
            // Log.v("jsonQuestion", "It's working!");

            questionText = jsonQuestion.getString(questionTextStr);
            // Log.v("actual string", questionText);

            JSONArray jsonAnswers = jsonQuestion.getJSONArray(answersStr);
            // JSONArray jsonAnswers = jsonQuestion.getJSONArray(answersStr);
            //Log.v("jsonAnswers", "It's working! " + jsonAnswers.toString());

            JSONObject jsonAnswer;
            String jsonAnswerText;

            //String drawableLocation;

            for(int i = 0; i < answers.length; i++){
                jsonAnswer = jsonAnswers.getJSONObject(i);
                Log.v("jsonAnswer", "It's working!" + jsonAnswer.toString());
                jsonAnswerText = jsonAnswer.getString(answerTextStr);
                // jsonAnswerText = jsonAnswer.getJSONObject(0);
                Log.v("jsonAnswerText", "It's working!");
                answers[i] = jsonAnswerText;

                isCorrectAnswerString = jsonAnswer.getString(isCorrectAnswerStr);
                //jsonIsCorrectAnswer = jsonAnswer.getJSONObject(1);
                if(isCorrectAnswerString.equals("true")){
                    rightAnswer = answers[i];
                }


            }

            //now filling question object
            question = new Question(questionText, rightAnswer, answers);

        } catch (JSONException e){
            String LOGTAG = "JSONException";
            Log.v(LOGTAG, "JSON exception in getQuestion");
            return null;
        }

        return question;
    }

    @Override
    public void onBackPressed() {
    }
}
