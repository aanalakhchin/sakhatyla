package com.example.smalley.sakhatyla2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    PlayActivity pa;
    private static Context context;
    Drawable[] drawables;
    String userAnswer;
    String correctAnswer;
    //randomNumbers will be generated to help shuffle answers
    Random random;
    int[] randomNumbers = null;

    int pointsEarned;
    int lessonNumber;
    int roundNumber;
    int questionNumber;
    String word;

    boolean isButton1Pink;
    boolean isButton2Pink;
    boolean isButton3Pink;
    boolean isButton4Pink;

    //variables to save 4 Questions and pass to next activity
    /*Question question1;
    Question question2;
    Question question3;
    Question question4;*/

    //variables needed for the progress bar
    private ProgressBar mProgress;
    private int mProgressStatus;

    MediaPlayer[] mediaPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        //setContentView(R.layout.content_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PlayActivity.context = getApplicationContext();
        pa = new PlayActivity();
        //getting lessonNumber, roundNumber, questionNumber
        Bundle bundle = getIntent().getExtras();

        pointsEarned = bundle.getInt("pointsEarned");
        lessonNumber = bundle.getInt("lessonNumber");
        roundNumber = bundle.getInt("roundNumber");
        questionNumber = bundle.getInt("questionNumber");

        //progress bar stuff
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgressStatus = pointsEarned;
        mProgress.setProgress(mProgressStatus);
        //Log.i("POINTS EARNED", "" + pointsEarned);

        final Question question = pa.getQuestion(lessonNumber,roundNumber,questionNumber);
        /*if(questionCounter >= 0 && questionCounter < 4){
            question = getQuestion(0,0,questionCounter);
        }
        else {
            question = getQuestion(0,0,0);
        }*/

        correctAnswer = question.getCorrectAnswer();
        word = question.getQuestionText();

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Which is '" + word + "'?");

        final TextView word1 = (TextView) findViewById(R.id.pic1TextView);
        final TextView word2 = (TextView) findViewById(R.id.pic2TextView);
        final TextView word3 = (TextView) findViewById(R.id.pic3TextView);
        final TextView word4 = (TextView) findViewById(R.id.pic4TextView);

        final Button submitButton = (Button) findViewById(R.id.submitButtonPlay);

        final String[] answers = question.getAnswers();

        /*word1.setText(answers[0]);
        word2.setText(answers[1]);
        word3.setText(answers[2]);
        word4.setText(answers[3]);*/

        random = new Random();
        randomNumbers = new int[4];
        //assigning random numbers to images to make images appear in a random order
        int index = 0;
        while ( index < randomNumbers.length){
            randomNumbers[index] = random.nextInt(randomNumbers.length);
            if(index-1 >= 0 && randomNumbers[index-1] == randomNumbers[index]){
                index--;
            }
            if(index-2 >= 0 && randomNumbers[index-2] == randomNumbers[index]){
                index--;
            }
            if(index-3 >= 0 && randomNumbers[index-3] == randomNumbers[index]){
                index--;
            }
            index++;
        }

        word1.setText(answers[randomNumbers[0]]);
        word2.setText(answers[randomNumbers[1]]);
        word3.setText(answers[randomNumbers[2]]);
        word4.setText(answers[randomNumbers[3]]);

        drawables = new Drawable[4];
        mediaPlayers = new MediaPlayer[4];

        for(int i = 0; i < drawables.length; i++) {
            if(lessonNumber == 0){
                switch (answers[randomNumbers[i]]) {
                    case "choroon":
                        drawables[i] = getResources().getDrawable(R.drawable.choroon);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.choroon);
                        break;
                    case "er kihi":
                        drawables[i] = getResources().getDrawable(R.drawable.er_kihi);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.er_kihi);
                        break;
                    case "kun":
                        drawables[i] = getResources().getDrawable(R.drawable.kun);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.kun);
                        break;
                    case "uu":
                        drawables[i] = getResources().getDrawable(R.drawable.uu);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.uu);
                        break;
                }
            }
            else if(lessonNumber == 1){
                switch (answers[randomNumbers[i]]) {
                    case "biir":
                        drawables[i] = getResources().getDrawable(R.drawable.biir);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.biir);
                        break;
                    case "ikki":
                        drawables[i] = getResources().getDrawable(R.drawable.ikki);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.ikki);
                        break;
                    case "us":
                        drawables[i] = getResources().getDrawable(R.drawable.us);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.us);
                        break;
                    case "tuert":
                        drawables[i] = getResources().getDrawable(R.drawable.tuert);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.tuert);
                        break;
                }
            }
            else if(lessonNumber == 2){
                switch (answers[randomNumbers[i]]) {
                    case "iye":
                        drawables[i] = getResources().getDrawable(R.drawable.iye);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.iye);
                        break;
                    case "a5a":
                        drawables[i] = getResources().getDrawable(R.drawable.a5a);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.a5a);
                        break;
                    case "o5o":
                        drawables[i] = getResources().getDrawable(R.drawable.o5o);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.o5o);
                        break;
                    case "jie kergen":
                        drawables[i] = getResources().getDrawable(R.drawable.jie_kergen);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.jie_kergen);
                        break;
                }
            }
            else if(lessonNumber == 3){
                switch (answers[randomNumbers[i]]) {
                    case "utue kununen":
                        drawables[i] = getResources().getDrawable(R.drawable.utue_kununen);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.utue_kununen);
                        break;
                    case "tuokh sonun":
                        drawables[i] = getResources().getDrawable(R.drawable.tuokh_sonun);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.tuokh_sonun);
                        break;
                    case "khaydakhkhyny":
                        drawables[i] = getResources().getDrawable(R.drawable.khaydakhkhyny);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.khaydakhkhyny);
                        break;
                    case "uchugeybin":
                        drawables[i] = getResources().getDrawable(R.drawable.uchugeybin);
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.uchugeybin);
                        break;
                }
            }
        }



        final ImageButton button1 = (ImageButton) findViewById(R.id.firstImageButton);
        final ImageButton button2 = (ImageButton) findViewById(R.id.secondImageButton);
        final ImageButton button3 = (ImageButton) findViewById(R.id.thirdImageButton);
        final ImageButton button4 = (ImageButton) findViewById(R.id.fourthImageButton);

        isButton1Pink = false;
        isButton2Pink = false;
        isButton3Pink = false;
        isButton4Pink = false;

        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isButton1Pink) {
                    //set the background color to magenta/pink
                    button1.setBackgroundColor(Color.rgb(255, 64, 129));
                    isButton1Pink = true;
                    //set other buttons to grey
                    button2.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton2Pink = false;
                    button3.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton3Pink = false;
                    button4.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton4Pink = false;

                    userAnswer = answers[randomNumbers[0]];
                    mediaPlayers[randomNumbers[0]].start();
                } else {
                    //set background color to grey
                    button1.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton1Pink = false;

                    userAnswer = null;
                }
            }
        });
        //assigning drawable
        //oneImageButton.setImageDrawable(drawables[randomNumbers[0]]);
        button1.setImageDrawable(drawables[0]);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isButton2Pink) {
                    //set the background color to magenta/pink
                    button2.setBackgroundColor(Color.rgb(255, 64, 129));
                    //set other buttons to grey
                    button1.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton1Pink = false;
                    button3.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton3Pink = false;
                    button4.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton4Pink = false;

                    isButton2Pink = true;
                    userAnswer = answers[randomNumbers[1]];
                    mediaPlayers[randomNumbers[1]].start();
                } else {
                    //set background color to grey
                    button2.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton2Pink = false;
                    userAnswer = null;
                }

            }
        });
        //assigning drawable
        //twoImageButton.setImageDrawable(drawables[randomNumbers[1]]);
        button2.setImageDrawable(drawables[1]);

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isButton3Pink) {
                    //set the background color to magenta/pink
                    button3.setBackgroundColor(Color.rgb(255, 64, 129));
                    isButton3Pink = true;
                    //set other buttons to grey
                    button1.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton1Pink = false;
                    button2.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton2Pink = false;
                    button4.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton4Pink = false;

                    userAnswer = answers[randomNumbers[2]];
                    mediaPlayers[randomNumbers[2]].start();
                } else {
                    //set background color to grey
                    button3.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton3Pink = false;
                    userAnswer = null;
                }
            }
        });
        //assigning drawable
        //threeImageButton.setImageDrawable(drawables[randomNumbers[2]]);
        button3.setImageDrawable(drawables[2]);

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!isButton4Pink) {
                    //set the background color to magenta/pink
                    button4.setBackgroundColor(Color.rgb(255, 64, 129));
                    //set other buttons to grey
                    button1.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton1Pink = false;
                    button2.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton2Pink = false;
                    button3.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton3Pink = false;

                    isButton4Pink = true;
                    userAnswer = answers[randomNumbers[3]];
                    mediaPlayers[randomNumbers[3]].start();
                } else {
                    //set background color to grey
                    button4.setBackgroundColor(Color.rgb(214, 215, 215));
                    isButton4Pink = false;
                    userAnswer = null;
                }
            }
        });
        //assigning drawable
        //fourImageButton.setImageDrawable(drawables[randomNumbers[3]]);
        button4.setImageDrawable(drawables[3]);

        //submit button on click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show a toast saying that user needs to choose an answer
                //if none selected
                if(userAnswer == null){
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.correct_wrong_toast_layout,
                            (ViewGroup) findViewById(R.id.cw_toast_layout_root));

                    TextView text = (TextView) layout.findViewById(R.id.text);
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    text.setText("Please select one of answers above.");
                    //toast.setView(layout);
                    toast.show();
                }
                else {
                    onClickImageButton(v);
                    submitButton.setEnabled(false);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickImageButton(View v){

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
            final Intent goToPickIntent = new Intent(PlayActivity.this, PickActivity.class);

            Bundle resultsBundle = new Bundle();
            //adding data to the bundle
            //CHANGING QUESTION NUMBER TO ZERO TO LOOP THROUGH QUESTIONS AGAIN IN OTHER ACTIVITIES
            //questionNumber = 0;
            resultsBundle.putInt("questionNumber", 0);
            resultsBundle.putInt("lessonNumber", lessonNumber);
            resultsBundle.putInt("roundNumber", roundNumber);
            resultsBundle.putInt("pointsEarned", pointsEarned);

            goToPickIntent.putExtras(resultsBundle);

            Handler x = new Handler();

            x.postDelayed(new Runnable() {
                public void run() {

                    startActivity(goToPickIntent);
                }
            }, 2000);

        }
        else {
            questionNumber++;
            //intent to go to the next question
            final Intent intent = new Intent(PlayActivity.this, PlayActivity.class);
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
        return PlayActivity.context;
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

    public Drawable[] setDrawableImages(String[] answers) {

        Drawable[] newDrawables = new Drawable[4];
        for(int i = 0; i < newDrawables.length; i++) {
            switch (answers[i]) {
                case "choroon":
                    newDrawables[i] = getResources().getDrawable(R.drawable.choroon);
                    break;
                case "er kihi":
                    newDrawables[i] = getResources().getDrawable(R.drawable.er_kihi);
                    break;
                case "kun":
                    newDrawables[i] = getResources().getDrawable(R.drawable.kun);
                    break;
                case "uu":
                    newDrawables[i] = getResources().getDrawable(R.drawable.uu);
                    break;
            }
        }
        return newDrawables;
    }

    @Override
    public void onBackPressed() {
    }
}
