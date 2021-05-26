package com.example.testapp.network;

import com.example.testapp.models.Answer;
import com.example.testapp.models.Quiz;
import com.example.testapp.models.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {
    public Quiz getQuestion(String response) throws JSONException {

        ArrayList<Question> allQuestions = new ArrayList<Question>();

        //получаем весь вопросник и из него список вопросов
        JSONObject quizJson = new JSONObject(response);
        JSONArray questionsArray = quizJson.getJSONArray("questions");

        //из каждого вопроса берем текст вопроса, верный овтет и список ответов
        for (int i = 0; i < questionsArray.length(); i++) {
            Question question = new Question();
            ArrayList<Answer> answersList = new ArrayList<Answer>();
            JSONObject jsonObjectQuestion = questionsArray.getJSONObject(i);
            String questionText = jsonObjectQuestion.getString("questionText");
            int correctAnswerIndex = jsonObjectQuestion.getInt("correctAnswerIndex");
            JSONArray jsonAnswers = jsonObjectQuestion.getJSONArray("answers");

            //составляем список верных ответов
            for (int j = 0; j < jsonAnswers.length(); j++) {
                JSONObject answerJson = jsonAnswers.getJSONObject(j);
                Answer answerObj = new Answer();
                answerObj.setAnswerText(answerJson.getString("answerText"));
                answersList.add(answerObj);
            }

            //сохраняем полученные данные в объекте
            question.setAnswers(answersList);
            question.setQuestionText(questionText);
            question.setCorrectAnswerIndex(correctAnswerIndex);

            //сохраняем вопрос в общем списке
            allQuestions.add(question);
        }
        return new Quiz(allQuestions);
    }
}
