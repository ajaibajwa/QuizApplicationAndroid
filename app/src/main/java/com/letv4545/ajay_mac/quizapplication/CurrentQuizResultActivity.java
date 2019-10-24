package com.letv4545.ajay_mac.quizapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.letv4545.ajay_mac.quizapplication.database.DBQuizReport;
import com.letv4545.ajay_mac.quizapplication.database.QuizReport;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CurrentQuizResultActivity extends AppCompatActivity {

    @BindView(R.id.textQuestionNo2)
    TextView textQuestionNo2;
    @BindView(R.id.textQuestion2)
    TextView textQuestion2;
    @BindView(R.id.textCategory2)
    TextView textCategory2;
    @BindView(R.id.textResOption1)
    TextView textResOption1;
    @BindView(R.id.textResOption2)
    TextView textResOption2;
    @BindView(R.id.textResOption4)
    TextView textResOption4;
    @BindView(R.id.textResOption3)
    TextView textResOption3;
    @BindView(R.id.textResUserAns)
    TextView textResUserAns;
    @BindView(R.id.textResCorrectAns)
    TextView textResCorrectAns;


    ArrayList<QuizReport> arrayList;
    String userEmail;
    int quizNo = 1;
    int quesCountTotal;
    int quesCounter;
    int score=1;
    @BindView(R.id.ButtonResNextQues)
    ImageButton ButtonResNextQues;
    @BindView(R.id.imageResultSign)
    ImageView imageResultSign;
    @BindView(R.id.textResScore)
    TextView textResScore;
    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.textView17)
    TextView textView17;
    @BindView(R.id.textView18)
    TextView textView18;
    @BindView(R.id.textView19)
    TextView textView19;
    @BindView(R.id.textView20)
    TextView textView20;
    @BindView(R.id.textView21)
    TextView textView21;
    private QuizReport currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_quiz_result);
        ButterKnife.bind(this);

        DBQuizReport dbQuizReport;
        arrayList = new ArrayList<>();


        SharedPreferences loggedUserPrefs = getSharedPreferences("quizloggedUserDetails", MODE_PRIVATE);
        userEmail = loggedUserPrefs.getString("email", null);

        SharedPreferences preferences = getSharedPreferences("quizCountPrefs", MODE_PRIVATE);
        quizNo = preferences.getInt("quizCount", 1) - 1;
        score = preferences.getInt("score", 1);

        dbQuizReport = new DBQuizReport(this);
        arrayList = dbQuizReport.getQuizReport(quizNo, userEmail);
        quesCountTotal = arrayList.size();
        //Collections.shuffle(arrayList);

        displayNextQues();
    }

    private void displayNextQues() {

        if (quesCounter < quesCountTotal) {
            textResScore.setText("Score: "+Integer.toString(score));
            //currentQuestion = quesList.get(quesCounter);
            currentQuestion = arrayList.get(quesCounter);
            textQuestion2.setText(currentQuestion.getQuestion());
            textResOption1.setText(currentQuestion.getOption1());
            textResOption2.setText(currentQuestion.getOption2());
            textResOption3.setText(currentQuestion.getOption3());
            textResOption4.setText(currentQuestion.getOption4());
            textCategory2.setText("Category: " + currentQuestion.getCategory());
            textResUserAns.setText(Integer.toString(currentQuestion.getUserAns()));
            textResCorrectAns.setText(Integer.toString(currentQuestion.getAnswerNo()));

            int userAns = currentQuestion.getUserAns();
            int correctAns = currentQuestion.getAnswerNo();

            if (userAns == correctAns) {

                imageResultSign.setImageResource(R.drawable.correct_ans);
            } else {
                imageResultSign.setImageResource(R.drawable.wrong_ans);
            }

            quesCounter++;
            textQuestionNo2.setText("Question :" + quesCounter + "/" + quesCountTotal);
            //answered = false;
            if (quesCounter < quesCountTotal) {
                ButtonResNextQues.setImageResource(R.drawable.log_in);
            } else {
                ButtonResNextQues.setImageResource(R.drawable.finish_quiz);
            }
            //imageButton.setImageResource(R.drawable.submit_answer);

            //timeLeft = countdownTime;
            //startTimer();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    @OnClick(R.id.ButtonResNextQues)
    public void onViewClicked() {
        displayNextQues();
    }
}
