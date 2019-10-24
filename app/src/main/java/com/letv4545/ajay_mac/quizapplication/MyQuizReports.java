package com.letv4545.ajay_mac.quizapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.letv4545.ajay_mac.quizapplication.Adapters.MyAdapter;
import com.letv4545.ajay_mac.quizapplication.database.DBQuizReport;
import com.letv4545.ajay_mac.quizapplication.database.DBQuizReportData;
import com.letv4545.ajay_mac.quizapplication.database.QuizReport;
import com.letv4545.ajay_mac.quizapplication.database.QuizReportData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyQuizReports extends AppCompatActivity {
    private static final String TAG = "ReportListActivity";


    @BindView(R.id.spinnerQuizNo)
    Spinner spinnerQuizNo;
    @BindView(R.id.listQuizReport)
    ListView listQuizReport;
    @BindView(R.id.textCategory)
    TextView textCategory;
    @BindView(R.id.textStartTime)
    TextView textStartTime;

    public static final String EXTRA_QUIZ_NO = "extraQuizNo";
    public static final String EXTRA_START_TIME = "extraStartTime";
    public static final String EXTRA_CATEGORY = "extraCategory";

    int quizNo;
    String userEmail;
    String categoryName;
    String quizStartTime;


    DBQuizReport dbQuizReport;
    ArrayList<QuizReport> arrayList;
   //ArrayList<QuizReport> arrayList;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quiz_reports);
        ButterKnife.bind(this);
        dbQuizReport=new DBQuizReport(this);

        arrayList=new ArrayList<>();

        SharedPreferences loggedUserPrefs = getSharedPreferences("quizloggedUserDetails", MODE_PRIVATE);
        userEmail = loggedUserPrefs.getString("email", null);

        displayQuizNo();

        spinnerQuizNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                QuizReportData selectedData = (QuizReportData) spinnerQuizNo.getSelectedItem();
                //int i=selectedData.getQuizId();
                displayreportList();
                //myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void displayQuizNo() {
        DBQuizReportData dbQuizReportData = new DBQuizReportData(this);
       ArrayList<QuizReportData> quizReportData = dbQuizReportData.getReportData(userEmail);
        //int quizNo=dbQuizReportData.getReportData().get(0);

        ArrayAdapter<QuizReportData> adapterReportData = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quizReportData);
        adapterReportData.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQuizNo.setAdapter(adapterReportData);
    }

    private void displayreportList() {
        QuizReportData selectedData = (QuizReportData) spinnerQuizNo.getSelectedItem();
        quizNo = selectedData.getQuizId();
        //id=quizNo;
        categoryName = selectedData.getCategory();
        quizStartTime = selectedData.getQuizStartTime();
        //userEmail=selectedData.getUserEmail();
        textCategory.setText(categoryName);
        textStartTime.setText(quizStartTime);

        arrayList=dbQuizReport.getQuizReport(quizNo,userEmail);
        //ArrayAdapter<QuizReport> arrayAdapterReport=new ArrayAdapter<>(this,android.R.layout.simple_expandable_list_item_1,arrayList);
        //listQuizReport.setAdapter(arrayAdapterReport);
        //arrayAdapterReport.notifyDataSetChanged();
        //DBQuizReportData dbQuizReportData = new DBQuizReportData(this);
        //quizReportList = dbQuizReport.getQuizReport(quizNo,userEmail);
        myAdapter = new MyAdapter(this,arrayList);
        listQuizReport.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();


    }
}
