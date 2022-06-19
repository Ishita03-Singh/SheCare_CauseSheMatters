package com.example.womenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class SchemesActivity extends AppCompatActivity {
TextView mlink_bbbp,mlink_oscs,mlink_whs,mlink_ujjwala,mlink_wwh,mlink_swadhar,mlink_mpv,mlink_msk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemes);
        mlink_bbbp=findViewById(R.id.link_bbbp);
        mlink_oscs=findViewById(R.id.link_oscs);
        mlink_whs=findViewById(R.id.link_whs);
        mlink_ujjwala=findViewById(R.id.link_ujjwala);
        mlink_wwh=findViewById(R.id.link_wwh);
        mlink_swadhar=findViewById(R.id.link_swadhar);
        mlink_mpv=findViewById(R.id.link_mpv);
        mlink_msk=findViewById(R.id.link_msk);

        mlink_bbbp.setMovementMethod(LinkMovementMethod.getInstance());
        mlink_oscs.setMovementMethod(LinkMovementMethod.getInstance());
        mlink_whs.setMovementMethod(LinkMovementMethod.getInstance());
        mlink_ujjwala.setMovementMethod(LinkMovementMethod.getInstance());
        mlink_wwh.setMovementMethod(LinkMovementMethod.getInstance());
        mlink_swadhar.setMovementMethod(LinkMovementMethod.getInstance());
        mlink_mpv.setMovementMethod(LinkMovementMethod.getInstance());
        mlink_msk.setMovementMethod(LinkMovementMethod.getInstance());

    }
}