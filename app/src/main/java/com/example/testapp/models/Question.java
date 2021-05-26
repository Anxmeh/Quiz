package com.example.testapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//модель вопроса со списком ответов
public class Question {
    @SerializedName("questionText")
    @Expose
    private String questionText;
    @SerializedName("answers")
    @Expose
    private List<Answer> answers = null;
    @SerializedName("correctAnswerIndex")
    @Expose
    private Integer correctAnswerIndex;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Integer getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(Integer correctAnswerIndex) {
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public Question(String questionText,  Integer correctAnswerIndex) {
        this.questionText = questionText;

        this.correctAnswerIndex = correctAnswerIndex;
    }

    public Question() {
    }
}
