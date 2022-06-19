package com.example.womenapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class HomePage extends AppCompatActivity {
    private TextView nametv;
    private ImageButton mlogoutbtn,meditprofilebtn;
    private ImageView mnotesIv,mreminderIv,mexcerciseIv,mperiodTrackIV;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        nametv=findViewById(R.id.Nametv);
        mlogoutbtn=findViewById(R.id.logoutbtnIV);
        meditprofilebtn=findViewById(R.id.editprofilebtnV);
        mnotesIv=findViewById(R.id.notesIV);
        mexcerciseIv=findViewById(R.id.excerciseIV);
        mreminderIv=findViewById(R.id.reminderIV);
        mperiodTrackIV=findViewById(R.id.periodTrackIV);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth= FirebaseAuth.getInstance();
        checkUser();



        mlogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makemeOffline();

            }
        });
        meditprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start edit profile activity
                startActivity(new Intent(HomePage.this,ProfileActivity.class));
            }
        });
        mexcerciseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start edit profile activity
                startActivity(new Intent(HomePage.this,ExcerciseActivity.class));
            }
        });
        mnotesIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start edit profile activity
                startActivity(new Intent(HomePage.this,SchemesActivity.class));
            }
        });
        mreminderIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start edit profile activity
                startActivity(new Intent(HomePage.this,ReminderActivity.class));
            }
        });
        mperiodTrackIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start edit profile activity
                startActivity(new Intent(HomePage.this,NotesActivity.class));
            }
        });
    }

    private void makemeOffline() {
        progressDialog.setTitle("Logging Out...");
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("online","false");

        //update value to db
        //save to db
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        firebaseAuth.signOut();
                        checkUser();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //failed to update db
                progressDialog.dismiss();
                Toast.makeText(HomePage.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void checkUser() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(HomePage.this,LoginActivity.class));
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

                            nametv.setText(name);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}