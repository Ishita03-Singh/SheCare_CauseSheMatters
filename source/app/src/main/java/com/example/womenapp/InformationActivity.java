package com.example.womenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InformationActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser CurrentUser;
    String uid;
    TextView UpcomingPeriodDate,OvulationDate;
    String TAG = "Message";
    private UserHealthInfoAdapter userHealthInfoAdapter;
    private ArrayList<UserHealthInfo> UserHealthInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        RecyclerView recyclerView = findViewById(R.id.recycledview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserHealthInfoList = new ArrayList<>();
        CurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert CurrentUser != null;
        uid = CurrentUser.getUid();
        userHealthInfoAdapter = new UserHealthInfoAdapter(this, UserHealthInfoList);
        recyclerView.setAdapter(userHealthInfoAdapter);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        UpcomingPeriodDate = findViewById(R.id.upcomingPeriodDateCardView);
        OvulationDate = findViewById(R.id.OvulationDate);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserHealthInfo userHealthInfo = dataSnapshot.child(uid).getValue(UserHealthInfo.class);
                    UserHealthInfoList.add(userHealthInfo);
                }
                userHealthInfoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    String dtStart = zoneSnapshot.child(uid).child("LastPeriodDate").getValue(String.class);
                    @SuppressLint("SimpleDateFormat") DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date UpcomingperioddateDateFormat = sourceFormat.parse(dtStart);
                        Calendar upcomingPeriodDateCalender = Calendar.getInstance();
                        upcomingPeriodDateCalender.setTime(UpcomingperioddateDateFormat);
                        // manipulate date
                        upcomingPeriodDateCalender.add(Calendar.DATE, 27);
                        // convert calendar to date
                        Date UpcomingPeriodmodifiedDate = upcomingPeriodDateCalender.getTime();
                        @SuppressLint("SimpleDateFormat") DateFormat UpcomingPeriodDatedateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String UpcomingperioddateStringFormat = UpcomingPeriodDatedateFormat.format(UpcomingPeriodmodifiedDate);
                        UpcomingPeriodDate.setText(UpcomingperioddateStringFormat);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        Date OvulationDateFormat = sourceFormat.parse(dtStart);
                        Calendar OvulationDateCalender = Calendar.getInstance();
                        OvulationDateCalender.setTime(OvulationDateFormat);
                        // manipulate date
                        OvulationDateCalender.add(Calendar.DATE, 13);
                        // convert calendar to date

                        Date OvulationModifiedDate = OvulationDateCalender.getTime();
                        @SuppressLint("SimpleDateFormat") DateFormat OvulationDatedateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String OvulationdateStringFormat = OvulationDatedateFormat.format(OvulationModifiedDate);
                        OvulationDate.setText(OvulationdateStringFormat);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

}