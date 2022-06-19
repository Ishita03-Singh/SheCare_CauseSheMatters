package com.example.womenapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private Button mupdatebtn;
    private EditText mnameEV,mPhoneEV,mcrendential;




    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mupdatebtn=findViewById(R.id.savechangesbtn);

        mcrendential=findViewById(R.id.credentialtv);
        mPhoneEV=findViewById(R.id.Phonetv);
        mnameEV=findViewById(R.id.NameTV);



        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        checkUser();

        mupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start updating profile
                inputData();
            }
        });
    }

    private String fullname,phoneno,credential;
    private void inputData() {
        //input data;
        fullname=mnameEV.getText().toString().trim();
        phoneno=mPhoneEV.getText().toString().trim();
        credential=mcrendential.getText().toString().trim();

        updateProfile();

    }
    private void updateProfile() {
        progressDialog.setMessage("Saving Account info...");
        progressDialog.show();
        String timeStamp=""+System.currentTimeMillis();

            //save info without image
            //setup data to save
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("name",""+fullname);
            hashMap.put("phone",""+phoneno);
            hashMap.put("credential",""+credential);
            //update to db
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getUid()).updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //db updated
                            progressDialog.dismiss();
                            Toast.makeText(ProfileActivity.this, "Profile updated successfully..,", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //failed to update db
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });



    }
    private void checkUser() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
            finish();

        }else{
            loadmyInfo();
        }
    }
    private void loadmyInfo() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                            String name=""+ds.child("name").getValue();
                            String phone=""+ds.child("phone").getValue();
                            String profileImage=""+ds.child("profileImage").getValue();
                            String creden=""+ds.child("credential").getValue();
                            String timestamp=""+ds.child("timestamp").getValue();
                            String uid=""+ds.child("uid").getValue();

                            mnameEV.setText(name);
                            mPhoneEV.setText(phone);
                            mcrendential.setText(creden);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }





}