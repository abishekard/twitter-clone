package com.example.ac_twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtName,edtEmail,edtPass;
    private Button btnSignUp,btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        if(ParseUser.getCurrentUser() != null){
            transition();
            finish();
        }
        edtPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogIn:
                Intent intent = new Intent(this,logIn.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnSignUp:
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("signing up..");
                if(edtName.getText().toString().equals("") || edtEmail.getText().toString().equals("")|| edtPass.getText().toString().equals("") ){
                    FancyToast.makeText(MainActivity.this,"no field can be left empty",FancyToast.INFO,
                            FancyToast.LENGTH_SHORT,false).show();

                }
                else{
                    final ParseUser parseUser =new ParseUser();
                      parseUser.setUsername(edtName.getText().toString());
                      parseUser.setPassword(edtPass.getText().toString());
                      parseUser.setEmail(edtEmail.getText().toString());
                      parseUser.signUpInBackground(new SignUpCallback() {
                          @Override
                          public void done(ParseException e) {
                              if(e == null){
                                  FancyToast.makeText(MainActivity.this,parseUser.getUsername()+" successfulY signed up",
                                          FancyToast.SUCCESS,FancyToast.LENGTH_SHORT,false).show();
                                  transition();
                              }
                              else{
                                  FancyToast.makeText(MainActivity.this,e.getMessage(),FancyToast.ERROR,FancyToast.LENGTH_SHORT,false).show();
                              }
                          }
                      });
                }
                progressDialog.dismiss();
                break;
        }
    }
    private void transition(){
        Intent intent = new Intent(MainActivity.this,twitterUser.class);
        startActivity(intent);

    }
   public void layoutTap(View view){
           try{
               InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
               inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
           }
           catch (Exception e){
               e.printStackTrace();
           }
   }

}