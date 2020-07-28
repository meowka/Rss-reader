package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Description extends AppCompatActivity {

    TextView news;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        news = findViewById(R.id.descriptionNews);
        title = findViewById(R.id.descriptionTitle);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            String name = arguments.get("news").toString();
            String titles = arguments.get("newsTitle").toString();
            title.setText(titles);
            news.setText(name);
        }
    }
}