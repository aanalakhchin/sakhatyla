package com.example.smalley.sakhatyla2;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Smalley on 4/10/2016.
 */
public class JsonReader {
    JsonReader pa;
    private static Context context;

    public static Context getAppContext() {
        return JsonReader.context;
    }

    public static String loadJSON() {
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

    public static Question getQuestion(int lessonIndex, int roundIndex, int questionIndex) {
        //pa = new JsonReader();

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

}
