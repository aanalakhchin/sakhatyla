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
 * Created by Smalley on 2/17/2016.
 */
public class Question {

    private String questionText;
    private String correctAnswer;
    private String[] answers;

    public Question(String qText, String cAnswer, String[] qAnswers){
        questionText = qText;
        correctAnswer = cAnswer;
        answers = qAnswers;
    }

    public Question(){

    }

    public void setQuestionText(String qText){
        questionText = qText;
    }
    public String getQuestionText(){
        return questionText;
    }

    public void setCorrectAnswer(String cAnswer){
        correctAnswer = cAnswer;
    }
    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public void setAnswers(String[] qAnswers){
        for(int i = 0; i < answers.length; i++){
            answers[i]= qAnswers[i];
        }
    }
    public String[] getAnswers(){
        return answers;
    }


}

