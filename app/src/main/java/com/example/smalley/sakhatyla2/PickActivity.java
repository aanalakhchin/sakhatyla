package com.example.smalley.sakhatyla2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class PickActivity extends AppCompatActivity {
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

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    boolean isButton1Pink;
    boolean isButton2Pink;
    boolean isButton3Pink;
    boolean isButton4Pink;

    //randomNumbers will be generated to help shuffle answers
    Random random;
    int[] randomNumbers = null;

    MediaPlayer[] mediaPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick);

        PickActivity.context = getApplicationContext();
        //getting lessonNumber, roundNumber, questionNumber
        Bundle bundle = getIntent().getExtras();

        pointsEarned = bundle.getInt("pointsEarned");
        lessonNumber = bundle.getInt("lessonNumber");
        roundNumber = bundle.getInt("roundNumber");
        questionNumber = bundle.getInt("questionNumber");
        //we will start again, since it is different set of questions


        //progress bar stuff
        mProgress = (ProgressBar) findViewById(R.id.progressBar2);
        mProgressStatus = pointsEarned;
        mProgress.setProgress(mProgressStatus);



        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        isButton1Pink = false;
        isButton2Pink = false;
        isButton3Pink = false;
        isButton4Pink = false;

        final Question question = getQuestion(lessonNumber, roundNumber, questionNumber);

        final String[] answers = question.getAnswers();
        correctAnswer = question.getCorrectAnswer();

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

        TextView textView = (TextView) findViewById(R.id.pickTextView);
        word = question.getQuestionText();
        textView.setText("Pick '" + word + "' in Sakha");

        button1.setText(answers[randomNumbers[0]]);
        button2.setText(answers[randomNumbers[1]]);
        button3.setText(answers[randomNumbers[2]]);
        button4.setText(answers[randomNumbers[3]]);

        mediaPlayers = new MediaPlayer[4];

        for(int i = 0; i < mediaPlayers.length; i++) {
            if(lessonNumber == 0){
                switch (answers[randomNumbers[i]]) {
                    case "choroon":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.choroon);
                        break;
                    case "er kihi":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.er_kihi);
                        break;
                    case "kun":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.kun);
                        break;
                    case "uu":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.uu);
                        break;
                }
            }
            else if(lessonNumber == 1){
                switch (answers[randomNumbers[i]]) {
                    case "biir":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.biir);
                        break;
                    case "ikki":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.ikki);
                        break;
                    case "us":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.us);
                        break;
                    case "tuert":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.tuert);
                        break;
                }
            }
            else if(lessonNumber == 2){
                switch (answers[randomNumbers[i]]) {
                    case "iye":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.iye);
                        break;
                    case "a5a":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.a5a);
                        break;
                    case "o5o":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.o5o);
                        break;
                    case "jie kergen":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.jie_kergen);
                        break;
                }
            }
            else if(lessonNumber == 3){
                switch (answers[randomNumbers[i]]) {
                    case "utue kununen":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.utue_kununen);
                        break;
                    case "tuokh sonun":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.tuokh_sonun);
                        break;
                    case "khaydakhkhyny":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.khaydakhkhyny);
                        break;
                    case "uchugeybin":
                        mediaPlayers[randomNumbers[i]] = MediaPlayer.create(getBaseContext(), R.raw.uchugeybin);
                        break;
                }
            }
        }


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
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

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
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

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
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

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
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
        //FOR THE BEST VERSION
        //setup onClickHighlightListeners for the buttons
        //setup onClickListener for the submit button
        final Button submitButton = (Button) findViewById(R.id.submitButtonPick);
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

/*    public void changeButtonColor(Button button, boolean isButtonPink){
        if (!isButtonPink) {
            //set the background color to magenta/pink
            button.setBackgroundColor(Color.rgb(255, 64, 129));
            isButtonPink = true;
        } else {
            //set background color to grey
            button.setBackgroundColor(Color.rgb(214, 215, 215));
            isButtonPink = false;
        }
    }*/

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
            final Intent goToPickIntent = new Intent(PickActivity.this, WriteActivity.class);

            Bundle resultsBundle = new Bundle();
            //adding data to the bundle
            resultsBundle.putInt("lessonNumber", lessonNumber);
            resultsBundle.putInt("roundNumber", roundNumber);
            resultsBundle.putInt("questionNumber", 0);
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
            final Intent intent = new Intent(PickActivity.this, PickActivity.class);
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
        return PickActivity.context;
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
