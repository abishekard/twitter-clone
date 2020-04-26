package com.example.ac_twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class twits extends AppCompatActivity implements View.OnClickListener {
    private Button sendT;
    private EditText tweet;
    private Button oTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twits);
        setTitle("Tweet");
        sendT = findViewById(R.id.sendT);
        tweet = findViewById(R.id.tweet);
        oTweet = findViewById(R.id.oTweet);
        oTweet.setOnClickListener(this);
        sendT.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.oTweet: Intent intent = new Intent(twits.this,othersTweet.class);
                               startActivity(intent);
                break;
            case R.id.sendT:
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("sending...");
                progressDialog.show();
                if(tweet.getText().toString().equals("")){
                FancyToast.makeText(this,"tweet can't be empty",FancyToast.INFO,FancyToast.LENGTH_SHORT,false).show();
            }
                            else{
                             ParseObject parseObject = new ParseObject("myTweets");
                             parseObject.put("tweet",tweet.getText().toString());
                             parseObject.put("user", ParseUser.getCurrentUser().getUsername());
                             parseObject.saveInBackground(new SaveCallback() {
                                 @Override
                                 public void done(ParseException e) {
                                     if(e == null){
                                         FancyToast.makeText(twits.this,"sent",FancyToast.SUCCESS,FancyToast.LENGTH_SHORT,false).show();
                                     }
                                     else{
                                         FancyToast.makeText(twits.this,e.getMessage(),FancyToast.ERROR,FancyToast.LENGTH_SHORT,false).show();
                                     }
                                 }
                             });}
                            progressDialog.dismiss();
                            tweet.setText("");
                break;
        }
    }
}
