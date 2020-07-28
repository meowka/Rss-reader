package com.example.test;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;


import com.example.test.Adapter.FeedAdapter;
import com.example.test.Common.HTTPDataHandler;

import com.example.test.Model.RSSObject;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RSSObject rssObject;
    FeedAdapter adapter;
    LinearLayoutManager linearLayoutManager;

//    RSS link
    private final String RSS_link = "https://meduza.io/rss/podcasts/meduza-v-kurse";
    private  final String RSS_to_Json_API = "https://api.rss2json.com/v1/api.json? rss_url=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadRSS();
    }

    private void loadRSS() {
        @SuppressLint("StaticFieldLeak") AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() {

           ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
                rssObject = new Gson().fromJson(s,RSSObject.class);
                adapter = new FeedAdapter(rssObject,getBaseContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };

        loadRSSAsync.execute(RSS_to_Json_API + RSS_link);
    }
    }