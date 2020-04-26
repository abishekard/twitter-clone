package com.example.ac_twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class twitterUser extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> tUser;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_user);
        setTitle("Twitter Users");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loding .... \n slow net Or \n no net connection");
        progressDialog.show();
        listView = findViewById(R.id.listView);
        tUser = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_checked,tUser);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);
        try{
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(objects.size() > 0 && e == null){
                        for(ParseUser user : objects){
                            tUser.add(user.getUsername());
                        }
                        listView.setAdapter(arrayAdapter);
                        for(String users : tUser)
                        {
                            if(ParseUser.getCurrentUser().getList("fanof") != null){
                            if(ParseUser.getCurrentUser().getList("fanof").contains(users)){
                                listView.setItemChecked(tUser.indexOf(users),true);

                            }
                                progressDialog.dismiss();
                            }
                        }

                    }
                }
            });

        }
        catch(Exception e){
            e.getMessage();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.send:
                Intent intent = new Intent(twitterUser.this,twits.class);
                startActivity(intent);
                break;
            case R.id.logout: ProgressDialog progressDialog = new ProgressDialog(this);
                              progressDialog.setMessage("Logging you out ....");
                              progressDialog.show();
                ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    Intent intent = new Intent(twitterUser.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

                break;
            case R.id.about:
                final PrettyDialog dialog = new PrettyDialog(this);
                dialog.setTitle("About").setMessage(" Small social media app developed by 'ABISHEK KUMAR'")
                        .addButton("Ok", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        if(checkedTextView.isChecked())
        {
            FancyToast.makeText(twitterUser.this,tUser.get(position)+" is followed",
                    FancyToast.SUCCESS,FancyToast.LENGTH_SHORT,false).show();
            ParseUser.getCurrentUser().add("fanof",tUser.get(position));
        }
        else
        {
            FancyToast.makeText(twitterUser.this,tUser.get(position) + " is unfollowed",
                    FancyToast.INFO,FancyToast.LENGTH_SHORT,false).show();
               ParseUser.getCurrentUser().getList("fanof").remove(tUser.get(position));
               List currentUserList = ParseUser.getCurrentUser().getList("fanof");
               ParseUser.getCurrentUser().remove("fanof");
               ParseUser.getCurrentUser().put("fanof",currentUserList);

        }
        ParseUser.getCurrentUser().saveInBackground();
    }
}
