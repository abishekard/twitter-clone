package com.example.ac_twitterclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class othersTweet extends AppCompatActivity {

    private ListView userList;
    private ArrayList<HashMap<String,String>> tweetlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_tweet);
        setTitle("Tweets");
         final ProgressDialog progressDialog = new ProgressDialog(this);
         progressDialog.setMessage("loading...");
         progressDialog.show();
        userList =findViewById(R.id.userList);
        tweetlist = new ArrayList<>();
        final SimpleAdapter tweetAdapter = new SimpleAdapter(othersTweet.this,tweetlist,
                android.R.layout.simple_list_item_2,new String[]{"tweetUserName","values"},new int[]{android.R.id.text1,android.R.id.text2});
        try{

        ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("myTweets");
        parseQuery.whereContainedIn("user", ParseUser.getCurrentUser().getList("fanof"));
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(objects.size()>0 && e == null){
                    for(ParseObject uTweets : objects){
                        HashMap<String,String> tweets =new HashMap<>();
                        tweets.put("tweetUserName",uTweets.getString("user"));
                        tweets.put("values",uTweets.getString("tweet"));
                        tweetlist.add(tweets);
                    }
                      userList.setAdapter(tweetAdapter);
                      progressDialog.dismiss();
                }
            }
        });
    }
      catch (Exception e){
            e.printStackTrace();

      }
    }
}
