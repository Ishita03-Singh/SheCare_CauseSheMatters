package com.example.womenapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NoInternetActivity extends AppCompatActivity {
    private Button mtryagainbtn;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        mtryagainbtn=findViewById(R.id.tryagainbtn);
        mtryagainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check internet
                //if internet not available stay here
                // else move to login or main activity
                checkinternet();
            }
        });
    }
    private void checkinternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

        if(connected==true) {
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            if(user==null){
                startActivity(new Intent(NoInternetActivity.this,LoginActivity.class));
                finish();
            }else{
                // check user type first
                checkUserType();
            }
        }

    }

    private void checkUserType() {
        startActivity(new Intent(NoInternetActivity.this, HomePage.class));
        finish();
    }
}
