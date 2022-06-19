package com.example.womenapp;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExcerciseActivity extends AppCompatActivity {
    RelativeLayout mmidExerciseRL,mlateExerciseRL;
    private TextView mEarlyTV,mlatetv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excercise);

        mEarlyTV=findViewById(R.id.EarlyTV);
        mlatetv=findViewById(R.id.lateTV);
        mmidExerciseRL=findViewById(R.id.midExerciseRL);
        mlateExerciseRL=findViewById(R.id.lateExerciseRL);

        mEarlyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowearlyExcercises();
            }
        });
        mlatetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowlateExcercises();
            }
        });
    }
    private void ShowearlyExcercises() {
        mlateExerciseRL.setVisibility(View.GONE);
        mmidExerciseRL.setVisibility(View.VISIBLE);

        mlatetv.setTextColor(getResources().getColor(R.color.white));
        mlatetv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        mEarlyTV.setTextColor(getResources().getColor(R.color.white));
        mEarlyTV.setBackgroundResource(R.drawable.sharp_rect02);





    }

    private void ShowlateExcercises () {
        mlateExerciseRL.setVisibility(View.VISIBLE);
        mmidExerciseRL.setVisibility(View.GONE);

        mlatetv.setTextColor(getResources().getColor(R.color.white));
        mlatetv.setBackgroundResource(R.drawable.sharp_rect02);

        mEarlyTV.setTextColor(getResources().getColor(R.color.white));
        mEarlyTV.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    }
}