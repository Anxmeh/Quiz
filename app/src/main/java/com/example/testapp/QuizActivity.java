package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.testapp.models.Quiz;
import com.example.testapp.models.Question;
import com.example.testapp.network.JsonParser;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    protected Context mContext;
    private JsonParser jsonParser;
    private Question currentQuestion;
    private List<Question> questionList;
    private TextView txtQuestion, txtStatistic;
    private Button btn1, btn2, btn3, btn4, btnNext;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuestion = findViewById(R.id.txtQuestion);
        txtStatistic = findViewById(R.id.txtStatistic);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btnNext = findViewById(R.id.btnNext);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        mContext = QuizActivity.this;
        jsonParser = new JsonParser();

        loadJSONFromAsset(mContext);
        ShowQuesition();
    }

    //обрабатываем файл json,
    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("quiz.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            json = convertStandardJSONString(json);

            // получаем из файла json объект вопросника и список вопросов
            Quiz questions = jsonParser.getQuestion(json);
            questionList = questions.getQuestions();
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    //убираем из строки лишние символы
    public static String convertStandardJSONString(String data_json) {
        data_json = data_json.replaceAll("\\r\\n", "");
        data_json = data_json.replace("\"{", "{");
        data_json = data_json.replace("}\",", "},");
        data_json = data_json.replace("}\"", "}");
        return data_json;
    }

    //Обработка нажатия кнопки "Следующий вопрос". Проверяем текущий вопрос и общее количество. Если
    //еще есть вопросы - показывает следующий, если нет - прячем кнопку следующего вопроса и показываем статистику
    public void OnClickBtnNext(View view) {
        currentQuestionIndex++;
        if (currentQuestionIndex < questionList.size())
            ShowQuesition();
        else {
            btnNext.setVisibility(View.GONE);
            ShowStatistic();
        }
    }

    private void ShowStatistic() {

        //счет пользователя в процентах
        double success = (double) score / questionList.size() * 100;
        //общее количество вопросов
        txtStatistic.setText(getResources().getString(R.string.allQuestions) + questionList.size());
        //количество верных ответов
        txtStatistic.append("\n" + getResources().getString(R.string.correctAnswers) + score);
        //процент успеха
        txtStatistic.append("\n" + getResources().getString(R.string.successPercentage) + Math.round(success * 100.0) / 100.0 + "%");
    }

    @SuppressLint("ResourceAsColor")
    public void ShowQuesition() {

        //устанавливаем текст вопроса и ответов
        currentQuestion = questionList.get(currentQuestionIndex);
        txtQuestion.setText(currentQuestion.getQuestionText());
        btn1.setText(currentQuestion.getAnswers().get(0).getAnswerText());
        btn2.setText(currentQuestion.getAnswers().get(1).getAnswerText());
        btn3.setText(currentQuestion.getAnswers().get(2).getAnswerText());
        btn4.setText(currentQuestion.getAnswers().get(3).getAnswerText());

        //цвет кнопок по умочанию, и возможность снова нажимать кнопки
        btn1.setBackgroundColor(getResources().getColor(R.color.purple_500));
        btn2.setBackgroundColor(getResources().getColor(R.color.purple_500));
        btn3.setBackgroundColor(getResources().getColor(R.color.purple_500));
        btn4.setBackgroundColor(getResources().getColor(R.color.purple_500));
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                if (CheckAnswer(0)) {
                    //если ответ верный - красим кнопку в зеленый, и блокируем остальные
                    btn1.setBackgroundColor(Color.GREEN);
                    btn2.setClickable(false);
                    btn3.setClickable(false);
                    btn4.setClickable(false);
                } else {
                    //если ответ неверный - красим кнопку в красный, и блокируем остальные
                    btn1.setBackgroundColor(Color.RED);
                    btn2.setClickable(false);
                    btn3.setClickable(false);
                    btn4.setClickable(false);
                }
                break;
            case R.id.btn2:
                if (CheckAnswer(1)) {
                    btn2.setBackgroundColor(Color.GREEN);
                    btn1.setClickable(false);
                    btn3.setClickable(false);
                    btn4.setClickable(false);
                } else {
                    btn2.setBackgroundColor(Color.RED);
                    btn1.setClickable(false);
                    btn3.setClickable(false);
                    btn4.setClickable(false);
                }
                break;
            case R.id.btn3:
                if (CheckAnswer(2)) {
                    btn3.setBackgroundColor(Color.GREEN);
                    btn1.setClickable(false);
                    btn2.setClickable(false);
                    btn4.setClickable(false);
                } else {
                    btn3.setBackgroundColor(Color.RED);
                    btn1.setClickable(false);
                    btn2.setClickable(false);
                    btn4.setClickable(false);
                }
                break;
            case R.id.btn4:
                if (CheckAnswer(3)) {
                    btn4.setBackgroundColor(Color.GREEN);
                    btn1.setClickable(false);
                    btn2.setClickable(false);
                    btn3.setClickable(false);
                } else {
                    btn4.setBackgroundColor(Color.RED);
                    btn1.setClickable(false);
                    btn2.setClickable(false);
                    btn3.setClickable(false);
                }
                break;
        }
    }

    private boolean CheckAnswer(int index) {
        //если ответ верный - прибавляем счет
        if (currentQuestion.getCorrectAnswerIndex() == index) {
            score++;
            return true;
        } else
            return false;
    }
}