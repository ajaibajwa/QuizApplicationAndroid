package com.letv4545.ajay_mac.quizapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.letv4545.ajay_mac.quizapplication.database.Users;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserProfile extends AppCompatActivity {

    String userEmail;
    String userName;
    String password;
    String gender;
    String phoneNumber;

    @BindView(R.id.textEmail)
    TextView textEmail;
    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.textPassword)
    TextView textPassword;
    @BindView(R.id.textGender)
    TextView textGender;
    @BindView(R.id.textPhone)
    TextView textPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        //Employee mEmployee = (Employee)getIntent().getExtras().getSerializable("employee");
        //Users users=(Users)getIntent().getExtras().getSerializable("userDetails");
        //Toast.makeText(this,users.getUserName().toString(),Toast.LENGTH_SHORT).show();
        SharedPreferences loggedUserPrefs = getSharedPreferences("quizloggedUserDetails", MODE_PRIVATE);
        userEmail = loggedUserPrefs.getString("email",null);
        userName=loggedUserPrefs.getString("name",null);
        password=loggedUserPrefs.getString("password",null);
        gender=loggedUserPrefs.getString("gender",null);
        phoneNumber=loggedUserPrefs.getString("phone",null);

        textEmail.setText(userEmail);
        textName.setText(userName);
        textPassword.setText(password);
        textGender.setText(gender);
        textPhone.setText(phoneNumber);

    }
}
