package com.letv4545.ajay_mac.quizapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.letv4545.ajay_mac.quizapplication.database.DBUsers;
import com.letv4545.ajay_mac.quizapplication.database.Users;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.buttonMoveToRegister)
    Button buttonMoveToRegister;
    @BindView(R.id.buttonLogin)
    ImageButton buttonLogin;
    @BindView(R.id.switchRememberMe)
    Switch switchRememberMe;
    DBUsers dbUsers;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        dbUsers=new DBUsers(this);
        SharedPreferences myPrefs=getSharedPreferences("rememberDetails",MODE_PRIVATE);
        //final SharedPreferences.Editor editor=myPrefs.edit();
        editEmail.setText(myPrefs.getString("email",null));
        editPassword.setText(myPrefs.getString("password",null));
    }

    @OnClick(R.id.buttonMoveToRegister)
    public void onButtonMoveToRegisterClicked() {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonLogin)
    public void onButtonLoginClicked() {
        String email=editEmail.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        Users users=dbUsers.getUserDetails(email,password);
        if (emailValidation()) {
            if (dbUsers.checkUser(email, password)) {
                rememberMe();
                SharedPreferences loggedUserPrefs = getSharedPreferences("quizloggedUserDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = loggedUserPrefs.edit();
                editor.putString("email",users.getUserEmail());
                editor.putString("name",users.getUserName());
                editor.putString("password",users.getUserPassword());
                editor.putString("gender",users.getUserGender());
                editor.putString("phone",users.getUserPhone());
                editor.apply();
                toastMessage("Welcome " + users.getUserName());
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                alertDialogDisplay("Login Unsuccessful!", "Please enter valid details!");
            }
        }
    }
    public void rememberMe(){
        SharedPreferences myPrefs=getSharedPreferences("rememberDetails",MODE_PRIVATE);
        final SharedPreferences.Editor editor=myPrefs.edit();
        String email=editEmail.getText().toString().trim();
        String password=editPassword.getText().toString().trim();
        //editEmail.setText(myPrefs.getString("email",null));
        //editPassword.setText(myPrefs.getString("password",null));
        if (switchRememberMe.isChecked()){
            editor.putString("email",email);
            editor.putString("password",password);
            editor.apply();
        }
        else{
            editor.remove("email");
            editor.remove("password");
            editor.apply();
            editEmail.setText(null);
            editPassword.setText(null);
        }
    }
    public boolean emailValidation(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email=editEmail.getText().toString();

        if (!email.matches(emailPattern)) {
            //AlertDialogDisplay("Invalid Email!","Please Enter a Valid Email Address");
            editEmail.setTextColor(Color.RED);
            editEmail.startAnimation(shakeError());
            editEmail.requestFocus();
            toastMessage("Please enter a valid Email Address!");
            return false;
        }
        return true;
    }
    public TranslateAnimation shakeError() {
        android.view.animation.TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }
    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    public void alertDialogDisplay(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message)
                .setCancelable(false)
                //.setIcon(R.drawable.ic_invalid_alert)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
