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
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class singupScreen extends AppCompatActivity {
    private EditText mnametv,memailtv,
            mphonetv,mpasstv,mcpasstv,mcredentialtv;
    private Button registerbtn;




    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup_screen);


        mnametv=findViewById(R.id.Nametv);
        memailtv=findViewById(R.id.emailtext);
        mpasstv=findViewById(R.id.passwordtext);
        mcpasstv=findViewById(R.id.confirmpasswordtext);
        mcredentialtv=findViewById(R.id.credentialTV);

        mphonetv=findViewById(R.id.PhoneTV);
        registerbtn=findViewById(R.id.registerButton);




        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //register user
                inputData();

            }
        });
    }

    private String fullname,emailid,passwrd,cpasswrd,phoneno,credential;
    private void inputData() {
        //input data;
        fullname=mnametv.getText().toString().trim();
        emailid=memailtv.getText().toString().trim();
        passwrd=mpasstv.getText().toString().trim();
        cpasswrd=mcpasstv.getText().toString().trim();
        phoneno=mphonetv.getText().toString().trim();
        credential=mcredentialtv.getText().toString().trim();
        //validate data
        if(TextUtils.isEmpty(fullname)){
            Toast.makeText(this,"Enter full name",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(phoneno)){
            Toast.makeText(this,"Enter Phone No. ",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(credential)){
            Toast.makeText(this,"Enter credential code ",Toast.LENGTH_SHORT).show();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
            Toast.makeText(this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwrd.length()<6){
            Toast.makeText(this,"Password must be atleast 6 characters long",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!passwrd.equals(cpasswrd)){
            Toast.makeText(this,"Password doesnot match",Toast.LENGTH_SHORT).show();
            return;
        }
        createAccount();
    }

    private void createAccount() {
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        //create account
        firebaseAuth.createUserWithEmailAndPassword(emailid,passwrd)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //account created
                        saverFirebasedata();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed creating account
                progressDialog.dismiss();
                Toast.makeText(singupScreen.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void saverFirebasedata() {
        progressDialog.setMessage("Saving Account info...");
        String timeStamp=""+System.currentTimeMillis();

            //save info without image
            //setup data to save
            HashMap<String,Object> hashMap = new HashMap<>();
            hashMap.put("uid",""+firebaseAuth.getUid());
            hashMap.put("email",""+emailid);
            hashMap.put("name",""+fullname);
            hashMap.put("phone",""+phoneno);
            hashMap.put("credential",""+credential);
            hashMap.put("timestamp",""+timeStamp);
            hashMap.put("profileImage","");

            //save to db
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getUid()).setValue(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            //db updated
                            progressDialog.dismiss();
                            startActivity(new Intent(singupScreen.this,HomePage.class));
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {
                    //failed to update db
                    progressDialog.dismiss();
                    startActivity(new Intent(singupScreen.this,HomePage.class));
                    finish();
                }
            });





    }




}