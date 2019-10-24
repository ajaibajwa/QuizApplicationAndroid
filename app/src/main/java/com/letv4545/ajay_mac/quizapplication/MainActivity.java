package com.letv4545.ajay_mac.quizapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.letv4545.ajay_mac.quizapplication.database.DBQuizReportData;
import com.letv4545.ajay_mac.quizapplication.database.QuizReportData;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    final Context context = this;
    @BindView(R.id.textHighScore)
    TextView textHighScore;
    @BindView(R.id.textHi)
    TextView textHi;
    @BindView(R.id.buttonStartQuiz)
    ImageButton buttonStartQuiz;
    Button btnQuizResult;


    private long backPressedTime;

    private static final int REQUEST_CODE_QUIZ = 1;
    @BindView(R.id.spinnerCategory)
    Spinner spinnerCategory;
    //public static final String SHARED_PREFS="sharedPrefs";
    //public static final String KEY_HIGHSCORE="keyHighScore";
    private Spinner spnCategory;

    private int highScore;
    private DBQuizReportData dbQuizReportData;
    String quizStartTime;
    private static String userEmail = " ";
    private Calendar mcurrentDate;
    int score;
    int day, month, year, hour, minute, second;

    //private FragmentManager fragmentManager;
    //private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);

        btnQuizResult=findViewById(R.id.buttoQuiuzResult);


        displayHighScore();


        String[] categories = {"General Knowledge", "Maths", "Computer Science"};

        SharedPreferences loggedUserPrefs = getSharedPreferences("quizloggedUserDetails", MODE_PRIVATE);
        userEmail = loggedUserPrefs.getString("email", null);

        String userName = loggedUserPrefs.getString("name", null);
        textHi.setText("Hi," + userName);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);


        dbQuizReportData = new DBQuizReportData(this);

        //int highScore=Integer.parseInt(textHighScore.getText().toString());
        //Intent mIntent=getIntent();
        //Bundle bundle=mIntent.getExtras();
        //int quizScore=bundle.getInt("quizScore");
        //if (quizScore>highScore){
        //    textHighScore.setText(String.valueOf(quizScore));
        //}
        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        score = bundle.getInt("quizScore",0);
        //SharedPreferences preferencess = getSharedPreferences("quizCountPrefs", MODE_PRIVATE);
        //score = preferencess.getInt("score",0);
        if (score > highScore) {
            setNewHighScore(score);

        }*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, new AboutUsFragment(),"fragment_about_us");
        //fragmentTransaction.add(new AboutUsFragment(), "fragment_about_us");
        fragmentTransaction.commit();*/
        btnQuizResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inttent=new Intent(MainActivity.this,CurrentQuizResultActivity.class);
                //inttent.putExtra("quizResScore",score);
                startActivity(inttent);
            }
        });
    }


    @OnClick(R.id.buttonStartQuiz)
    public void onViewClicked() {

        String category = spinnerCategory.getSelectedItem().toString();

        mcurrentDate = Calendar.getInstance();
        hour = mcurrentDate.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentDate.get(Calendar.MINUTE);
        second = mcurrentDate.get(Calendar.SECOND);
        day = mcurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mcurrentDate.get(Calendar.MONTH);
        year = mcurrentDate.get(Calendar.YEAR);

        quizStartTime = (day + "/" + month + "/" + year + "     " + hour + ":" + minute + ":" + second);

        final QuizReportData quizReportData = new QuizReportData();

        //quizReport.setUserEmail(userEmail);
        //quizReport.setQuizNo(quizCount);
        quizReportData.setUserEmail(userEmail);
        quizReportData.setQuizStartTime(quizStartTime);
        quizReportData.setCategory(category);
        boolean insertData = dbQuizReportData.addReportData(quizReportData);
        /*if (insertData) {
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Added", Toast.LENGTH_SHORT).show();
        }*/

        Intent intent = new Intent(this, QuizScreenActivity.class);
        intent.putExtra("quesCategory", category);
        //intent.putExtra("userEmail",userEmail);
        startActivityForResult(intent, REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(QuizScreenActivity.quizScore, 0);
                if (score > highScore) {
                    setNewHighScore(score);
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }


        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            SharedPreferences exitTokenPrefs = getSharedPreferences("exitToken", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = exitTokenPrefs.edit();
            editor1.putBoolean("tokenValue", true);
            editor1.apply();
            finishAffinity();
        } else {
            Toast.makeText(this, "Press Back Button again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();


    }


    private void displayHighScore() {
        SharedPreferences prefs = getSharedPreferences("quizHighScore", MODE_PRIVATE);
        highScore = prefs.getInt("highScore", 0);
        textHighScore.setText(String.valueOf(highScore));
    }

    public void setNewHighScore(int highScoreNew) {
        highScore = highScoreNew;
        textHighScore.setText(String.valueOf(highScore));

        SharedPreferences prefs = getSharedPreferences("quizHighScore", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("highScore", highScore);
        editor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

            SharedPreferences exitTokenPrefs = getSharedPreferences("exitToken", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = exitTokenPrefs.edit();
            editor1.putBoolean("tokenValue", false);
            editor1.apply();
            Intent mIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(mIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //fragmentTransaction = fragmentManager.beginTransaction();

        if (id == R.id.nav_quiz_reports) {
            Intent intent = new Intent(MainActivity.this, MyQuizReports.class);
            startActivity(intent);
        } else if (id == R.id.nav_logOut) {
            SharedPreferences exitTokenPrefs = getSharedPreferences("exitToken", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = exitTokenPrefs.edit();
            editor1.putBoolean("tokenValue", false);
            editor1.apply();
            Intent mIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(mIntent);
            //fragmentTransaction.replace(R.id.container, new ContactUsFragment(),"fragment_contact_us");
        } else if (id == R.id.nav_user_profile) {
            Intent mIntent = new Intent(MainActivity.this, UserProfile.class);
            startActivity(mIntent);
            //Crash if fragment not found by mentioned tag name
            //fragmentTransaction.remove(fragmentManager.findFragmentByTag("fragment_about_us"));

        } else if (id == R.id.nav_help) {
            Intent mIntent = new Intent(MainActivity.this, HelpScreenActivity.class);
            startActivity(mIntent);
        }else if (id==R.id.nav_contactMe){
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_contact);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
            //TextView text = (TextView) dialog.findViewById(R.id.text);
            //text.setText("Android custom dialog example!");
            //ImageView image = (ImageView) dialog.findViewById(R.id.image);
            //image.setImageResource(R.drawable.ic_launcher);

            Button dialogButton = (Button) dialog.findViewById(R.id.buttonnnnnnnn);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
        //fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}