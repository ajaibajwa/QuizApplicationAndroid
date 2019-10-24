package com.letv4545.ajay_mac.quizapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.letv4545.ajay_mac.quizapplication.database.DBUsers;
import com.letv4545.ajay_mac.quizapplication.database.Users;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.editREmail)
    EditText editREmail;
    @BindView(R.id.editRName)
    EditText editRName;
    @BindView(R.id.editRPassword)
    EditText editRPassword;
    @BindView(R.id.editRConfirmPasssword)
    EditText editRConfirmPasssword;
    @BindView(R.id.editRPhone)
    EditText editRPhone;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.buttonRegister)
    ImageButton buttonRegister;
    @BindView(R.id.buttonMoveToLogin)
    Button buttonMoveToLogin;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFemale)
    RadioButton rbFemale;
    @BindView(R.id.rbOther)
    RadioButton rbOther;
    private Context context=this;

    private DBUsers dbUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        dbUsers = new DBUsers(this);


        editRConfirmPasssword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String password=editRPassword.getText().toString();
                String confirmPassword=editRConfirmPasssword.getText().toString();
                if (!password.equals(confirmPassword)){
                    editRConfirmPasssword.setTextColor(Color.RED);
                    editRConfirmPasssword.startAnimation(shakeError());
                    ToastMessage("Password fields do not match");
                    //this.getCurrentFocus().clearFocus();
                    //editRConfirmPasssword.requestFocus();
                }
                else {
                        editRConfirmPasssword.setTextColor(Color.DKGRAY);
                        ToastMessage("Password Ok");
                    }
            }
        });
    }
    @OnClick(R.id.buttonRegister)
    public void onButtonRegisterClicked() {
        if (emptyFieldValidation()) {
            if (emailValidation()) {
                if (confirmPasswordValidation()) {
                    if (phoneCountLess()) {
                        final Users user = new Users();
                        user.setUserEmail(editREmail.getText().toString());
                        user.setUserName(editRName.getText().toString());
                        user.setUserPassword(editRPassword.getText().toString());
                        int id=rgGender.getCheckedRadioButtonId();
                        RadioButton rbGender=findViewById(id);
                        user.setUserGender(rbGender.getText().toString());
                        /*if (rgGender.getCheckedRadioButtonId() == R.id.rbMale) {
                            user.setUserGender("MALE");
                        } else if (rgGender.getCheckedRadioButtonId() == R.id.rbFemale) {
                            user.setUserGender("FEMALE");
                        } else {
                            user.setUserGender("Other");
                        }*/
                        user.setUserPhone((editRPhone.getText().toString()));

                        //dbUsers.add(user);
                        boolean insertData = dbUsers.add(user);
                        if (insertData) {
                            ToastMessage("Welcome " + editRName.getText().toString());
                            Intent intent=new Intent(this,LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }

    }

    @OnClick(R.id.buttonMoveToLogin)
    public void onButtonMoveToLoginClicked() {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public boolean emailValidation(){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String email=editREmail.getText().toString();

        if (!email.matches(emailPattern)) {
            //AlertDialogDisplay("Invalid Email!","Please Enter a Valid Email Address");
            editREmail.setTextColor(Color.RED);
            editREmail.startAnimation(shakeError());
            editREmail.requestFocus();
            ToastMessage("Please enter a valid Email Address!");
            return false;
        }
        return true;
    }
    public boolean emptyFieldValidation(){
        String email=editREmail.getText().toString();
        String name=editRName.getText().toString();
        String password=editRPassword.getText().toString();
        String confirmPassword=editRConfirmPasssword.getText().toString();
        String phone =editRPhone.getText().toString();

        if (email.isEmpty()||name.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()||((rgGender.getCheckedRadioButtonId())<=0)||phone.isEmpty()){
            AlertDialogDisplay("Empty Field Alert!","Please enter values in all the fields.");
            return false;
        }
        return true;
    }

    public boolean confirmPasswordValidation(){
        String password=editRPassword.getText().toString();
        String confirmPassword=editRConfirmPasssword.getText().toString();
        if (!password.equals(confirmPassword)){
            editRConfirmPasssword.setTextColor(Color.RED);
            editRConfirmPasssword.startAnimation(shakeError());
            ToastMessage("Password fields do not match");
            //this.getCurrentFocus().clearFocus();
            //editRConfirmPasssword.requestFocus();
            return false;
        }
        return true;
    }
    public boolean phoneCountLess(){
        if (editRPhone.getText().toString().length()<10){
            ToastMessage("Total Phone Number digits must be equal to 10");
            return false;
        }
        return true;
    }
    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    public void AlertDialogDisplay(String title,String message){
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
    public void ToastMessage(String message){
        Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
