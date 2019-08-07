package com.jyamanouchi.mytetris;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonPlay, buttonHighScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the buttons
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonHighScore = (Button) findViewById(R.id.buttonScore);

        //adding a click listener
        buttonPlay.setOnClickListener(this);
        buttonHighScore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonPlay) {
            //the transition from MainActivity to GameActivity
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        }
        if (v == buttonHighScore) {
            //the transition from MainActivity to HighScore activity
            startActivity(new Intent(MainActivity.this, HighScore.class));
        }
    }
}
