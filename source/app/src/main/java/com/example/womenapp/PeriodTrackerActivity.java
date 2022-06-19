package com.example.womenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PeriodTrackerActivity extends AppCompatActivity {
    private Button new_user_details_button, database_details_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_period_tracker);
        new_user_details_button = (Button) findViewById(R.id.new_user_details);
        database_details_button = (Button) findViewById(R.id.database_details);
        new_user_details_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PeriodTrackerActivity.this, DetailsActivity.class);

                startActivity(intent1);
            }
        });
        database_details_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(PeriodTrackerActivity.this, InformationActivity.class);

                startActivity(intent2);
            }
        });
    }
}