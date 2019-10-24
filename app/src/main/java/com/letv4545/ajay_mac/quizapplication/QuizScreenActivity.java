package com.letv4545.ajay_mac.quizapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.letv4545.ajay_mac.quizapplication.database.DBQuizReport;
import com.letv4545.ajay_mac.quizapplication.database.DatabaseHelper;
import com.letv4545.ajay_mac.quizapplication.database.QuestionAnswers;
import com.letv4545.ajay_mac.quizapplication.database.QuizReport;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizScreenActivity extends AppCompatActivity {

    @BindView(R.id.textScore)
    TextView textScore;
    @BindView(R.id.textQuestionNo)
    TextView textQuestionNo;
    @BindView(R.id.textCountdown)
    TextView textCountdown;
    @BindView(R.id.rbOption1)
    RadioButton rbOption1;
    @BindView(R.id.rbOption2)
    RadioButton rbOption2;
    @BindView(R.id.rbOption3)
    RadioButton rbOption3;
    @BindView(R.id.rbOption4)
    RadioButton rbOption4;
    @BindView(R.id.rgOptions)
    RadioGroup rgOptions;
    @BindView(R.id.textQuestion)
    TextView textQuestion;
    @BindView(R.id.imageButton)
    ImageButton imageButton;
    @BindView(R.id.textCategory)
    TextView textCategory;
    @BindView(R.id.textCorrectAns)
    TextView textCorrectAns;

    private DBQuizReport dbQuizReport;

    private List<QuestionAnswers> quesList;
    private int quesCounter;
    private int quesCountTotal;
    private QuestionAnswers currentQuestion;
    private ColorStateList rbDefaultColor;
    public static final String quizScore = "moreScore";
    private static final long countdownTime = 35000;
    private ColorStateList timerDefaultColor;
    private CountDownTimer countDownTimer;
    private long timeLeft;
    int quizCount = 1;
    String userEmail;
    String quizStartTime;
    String quizEndTime;
    private Calendar mcurrentDate;
    private Calendar ecurrentDate;
    int day, month, year, hour, minute, second;
    int endDay, endMonth, endYear, endHour, endMinute, endSecond;

    private long backPressedTime;
    private int score;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen);
        ButterKnife.bind(this);

        mcurrentDate = Calendar.getInstance();
        hour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentDate.get(Calendar.MINUTE);
        second = mcurrentDate.get(Calendar.SECOND);
        day = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mcurrentDate.get(Calendar.MONTH);
        year = mcurrentDate.get(Calendar.YEAR);

        quizStartTime = (day + "/" + month + "/" + year + "     " + hour + ":" + minute + ":" + second);


        dbQuizReport = new DBQuizReport(this);

        rbDefaultColor = rbOption1.getTextColors();
        timerDefaultColor = textCountdown.getTextColors();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        SharedPreferences preferences = getSharedPreferences("quizCountPrefs", MODE_PRIVATE);
        quizCount = preferences.getInt("quizCount", 1);
        //score = preferences.getInt("score", 0);

        SharedPreferences loggedUserPrefs = getSharedPreferences("quizloggedUserDetails", MODE_PRIVATE);
        userEmail = loggedUserPrefs.getString("email", null);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String category = bundle.getString("quesCategory");
        textCategory.setText("Category:" + category);
        quesList = databaseHelper.getQuestions(category);
        quesCountTotal = 10;
        Collections.shuffle(quesList);

        displayNextQues();

    }

    private void displayNextQues() {
        rbOption1.setTextColor(rbDefaultColor);
        rbOption2.setTextColor(rbDefaultColor);
        rbOption3.setTextColor(rbDefaultColor);
        rbOption4.setTextColor(rbDefaultColor);
        rgOptions.clearCheck();
        textCorrectAns.setVisibility(View.INVISIBLE);

        if (quesCounter < quesCountTotal) {
            currentQuestion = quesList.get(quesCounter);
            textQuestion.setText(currentQuestion.getQuestion());
            rbOption1.setText(currentQuestion.getOption1());
            rbOption2.setText(currentQuestion.getOption2());
            rbOption3.setText(currentQuestion.getOption3());
            rbOption4.setText(currentQuestion.getOption4());

            quesCounter++;
            textQuestionNo.setText("Question :" + quesCounter + "/" + quesCountTotal);
            answered = false;
            imageButton.setImageResource(R.drawable.submit_answer);

            timeLeft = countdownTime;
            startTimer();
        } else {
            finishQuiz();
        }

    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountdownText();
            }

            @Override
            public void onFinish() {
                timeLeft = 0;
                updateCountdownText();
                verifyAns();

            }
        }.start();
    }

    private void updateCountdownText() {
        int min = (int) ((timeLeft) / 1000) / 60;
        int sec = (int) ((timeLeft) / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", min, sec);
        textCountdown.setText(timeFormatted);

        if (timeLeft < 1000) {
            textCountdown.setTextColor(Color.RED);
        } else {
            textCountdown.setTextColor(timerDefaultColor);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void displayCorrectAns() {
        rbOption1.setTextColor(Color.RED);
        rbOption2.setTextColor(Color.RED);
        rbOption3.setTextColor(Color.RED);
        rbOption4.setTextColor(Color.RED);
        textCorrectAns.setVisibility(View.VISIBLE);

        switch (currentQuestion.getAnswerNo()) {
            case 1:
                rbOption1.setTextColor(Color.BLACK);
                textCorrectAns.setText("Option 1 is Correct");
                break;
            case 2:
                rbOption2.setTextColor(Color.BLACK);
                textCorrectAns.setText("Option 2 is Correct");
                break;
            case 3:
                rbOption3.setTextColor(Color.BLACK);
                textCorrectAns.setText("Option 3 is Correct");
                break;
            case 4:
                rbOption4.setTextColor(Color.BLACK);
                textCorrectAns.setText("Option 4 is Correct");
                break;
        }
        if (quesCounter < quesCountTotal) {
            imageButton.setImageResource(R.drawable.log_in);
        }
        else {
            imageButton.setImageResource(R.drawable.finish_quiz);
        }
    }

    private void finishQuiz() {
        quizCount++;
        /*Intent intent=new Intent(QuizScreenActivity.this,HomeScreenActivity.class);
        intent.putExtra("quizScore",Integer.parseInt(textScore.getText().toString()));
        setResult(RESULT_OK,intent);
        startActivity(intent);*/
        /*ecurrentDate=Calendar.getInstance();
        endHour=ecurrentDate.get(Calendar.HOUR_OF_DAY);
        endMinute=ecurrentDate.get(Calendar.MINUTE);
        endSecond=ecurrentDate.get(Calendar.SECOND);
        endDay=ecurrentDate.get(Calendar.DAY_OF_MONTH);
        endMonth=ecurrentDate.get(Calendar.MONTH);
        endYear=ecurrentDate.get(Calendar.YEAR);
        quizEndTime=(endDay+"/"+endMonth+"/"+endYear+"     "+endHour+":"+endMinute+":"+endSecond);*/
        SharedPreferences preferences = getSharedPreferences("quizCountPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("quizCount", quizCount);
        editor.putInt("score",score);
        editor.apply();

        Intent resultIntent = new Intent();
        resultIntent.putExtra(quizScore, score);

        setResult(RESULT_OK, resultIntent);
        //startActivity(resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            toastMessage("Press Back Button again to exit");
        }

        backPressedTime = System.currentTimeMillis();
    }

    private void verifyAns() {
        answered = true;
        RadioButton rbUserAns = findViewById(rgOptions.getCheckedRadioButtonId());
        int ansNo = rgOptions.indexOfChild(rbUserAns) + 1;

        if (ansNo == currentQuestion.getAnswerNo()) {
            score++;
            textScore.setText(String.valueOf(score));
        }
        displayCorrectAns();
        countDownTimer.cancel();

        final QuizReport quizReport = new QuizReport();
        quizReport.setUserEmail(userEmail);
        //quizReport.setQuizStartTime(quizStartTime);
        //quizReport.setQuizEndTime(quizEndTime);
        //quizReport.setCorrectAnswersCount(score);
        //quizReport.setWrongAnswersCount((20-score));
        quizReport.setQuestion(currentQuestion.getQuestion());
        quizReport.setOption1(currentQuestion.getOption1());
        quizReport.setOption2(currentQuestion.getOption2());
        quizReport.setOption3(currentQuestion.getOption3());
        quizReport.setOption4(currentQuestion.getOption4());
        quizReport.setAnswerNo(currentQuestion.getAnswerNo());
        quizReport.setCategory(currentQuestion.getCategory());
        quizReport.setUserAns(ansNo);
        quizReport.setQuizNo(quizCount);
        boolean insertData = dbQuizReport.addReport(quizReport);
        /*if (insertData) {
            toastMessage("Added to report");
        } else {
            toastMessage("Not added to Report");
        }*/
    }

    @OnClick(R.id.imageButton)
    public void onViewClicked() {
        if (!answered) {
            if (rbOption1.isChecked() || rbOption2.isChecked() || rbOption3.isChecked() || rbOption4.isChecked()) {
                verifyAns();
            } else {
                toastMessage("No answer selected !");
            }
        } else {
            displayNextQues();
        }
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
