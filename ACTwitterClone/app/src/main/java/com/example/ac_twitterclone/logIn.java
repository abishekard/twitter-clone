package com.example.ac_twitterclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class logIn extends AppCompatActivity implements View.OnClickListener {
    private Button btnLlogin, btnLsignup;
    private EditText edtLname, edtLpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        btnLlogin = findViewById(R.id.btnLlogin);
        btnLsignup = findViewById(R.id.btnLsignup);
        edtLpass = findViewById(R.id.edtLpass);
        edtLname = findViewById(R.id.edtLname);
        btnLsignup.setOnClickListener(this);
        btnLlogin.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {

            ParseUser.getCurrentUser().logOut();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLsignup: Intent intent = new Intent(logIn.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnLlogin:
               if(edtLname.getText().toString().equals("") ||edtLpass.getText().toString().equals("")){
                    FancyToast.makeText(logIn.this,"please enter username and password",FancyToast.INFO,FancyToast.LENGTH_LONG,false).show();
                }
                else{
                ParseUser.logInInBackground(edtLname.getText().toString(), edtLpass.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null && e == null) {
                            FancyToast.makeText(logIn.this, "logIn successful", FancyToast.SUCCESS, FancyToast.LENGTH_LONG, false).show();
                            transition();
                            finish();
                        } else {
                            FancyToast.makeText(logIn.this, e.getMessage(), FancyToast.ERROR, FancyToast.LENGTH_LONG, false).show();
                        }
                    }
                });

                break;

        }
    }

}
    private void transition(){
        Intent intent = new Intent(logIn.this,twitterUser.class);
        startActivity(intent);

    }
    public void layoutLTap(View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
