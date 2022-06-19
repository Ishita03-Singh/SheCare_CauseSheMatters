package com.example.womenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private EditText username_Button, weight_Button, height_Button, blood_pressure_Button, last_period_date_Button, date_of_birth_Button, heart_rate_Button;
    List<UserHealthInfo> userHealthInfoList;
    Button details_submit;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser CurrentUser;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        details_submit = (Button) findViewById(R.id.details);
        username_Button = findViewById(R.id.username);
        last_period_date_Button = findViewById(R.id.last_period_date);
        heart_rate_Button = findViewById(R.id.heart_rate);
        height_Button = findViewById(R.id.height);
        weight_Button = findViewById(R.id.weight);
        date_of_birth_Button = findViewById(R.id.Dateofbirth);
        blood_pressure_Button = findViewById(R.id.blood_pressure);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("UserHealthInfo");
        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = CurrentUser.getUid();
        userHealthInfoList = new ArrayList<>();


        details_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calling the method addUserHealthInfo()
                //the method is defined below
                //this method is actually performing the write operation
                addUserHealthInfo();
            }
        });
    }

    private void addUserHealthInfo() {
        //getting the values to save
        final String username_string = username_Button.getText().toString().trim();
        final String date_of_birth_string = date_of_birth_Button.getText().toString().trim();
        final String last_period_date_string = last_period_date_Button.getText().toString().trim();
        final String weight_string = weight_Button.getText().toString().trim();
        final String height_string = height_Button.getText().toString().trim();
        final String heart_rate_string = heart_rate_Button.getText().toString().trim();
        final String blood_pressure_string = blood_pressure_Button.getText().toString().trim();


        //checking if the value is provided
        if (!TextUtils.isEmpty(username_string) && !TextUtils.isEmpty(date_of_birth_string) && !TextUtils.isEmpty(height_string) && !TextUtils.isEmpty(weight_string) && !TextUtils.isEmpty(last_period_date_string) && !TextUtils.isEmpty(heart_rate_string) && !TextUtils.isEmpty(blood_pressure_string)) {
            HashMap<String, Object> UserMap = new HashMap<>();


            UserMap.put("UserName", username_string);
            UserMap.put("LastPeriodDate", last_period_date_string);
            UserMap.put("Height", height_string);
            UserMap.put("Weight", weight_string);
            UserMap.put("HeartRate", heart_rate_string);
            UserMap.put("BloodPressure", blood_pressure_string);
            UserMap.put("DateOfBirth", date_of_birth_string);

            myRef.child(uid).setValue(UserMap).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    //getting a unique id using push().getKey() method

                    username_Button.setText("");
                    last_period_date_Button.setText("");
                    weight_Button.setText("");
                    heart_rate_Button.setText("");
                    height_Button.setText("");
                    date_of_birth_Button.setText("");
                    blood_pressure_Button.setText("");
                }
            });
            //displaying a success toast
            Toast.makeText(this, "Your Health Details added", Toast.LENGTH_LONG).show();
            startActivity(new Intent(DetailsActivity.this, PeriodTrackerActivity.class));
            finish();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please Enter your Details", Toast.LENGTH_LONG).show();
        }

    }
}