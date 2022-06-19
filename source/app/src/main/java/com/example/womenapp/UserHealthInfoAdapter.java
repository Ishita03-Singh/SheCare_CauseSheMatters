package com.example.womenapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class UserHealthInfoAdapter  extends RecyclerView.Adapter<UserHealthInfoAdapter .UserHealthInfoViewHolder> {

    ArrayList<UserHealthInfo> UserHealthInfoList;
    Context context;

    public UserHealthInfoAdapter (Context context , ArrayList<UserHealthInfo> UserHealthInfoList){
        this.UserHealthInfoList = UserHealthInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHealthInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item , parent ,false);
        return new UserHealthInfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHealthInfoViewHolder holder, int position) {
        UserHealthInfo userHealthInfo = UserHealthInfoList.get(position);

        holder.BloodPressure.setText(userHealthInfo.getBloodPressure());
        holder.Age.setText(userHealthInfo.getDateOfBirth());
        holder.HeartRate.setText(userHealthInfo.getHeartRate());
        holder.Height.setText(userHealthInfo.getHeight());
        holder.LastPeriodDate.setText(userHealthInfo.getLastPeriodDate());
        holder.UserName.setText(userHealthInfo.getUserName());
        holder.Weight.setText(userHealthInfo.getWeight());
    }

    @Override
    public int getItemCount() {
        return UserHealthInfoList.size();
    }

    public static class UserHealthInfoViewHolder extends RecyclerView.ViewHolder{

        TextView UserName,LastPeriodDate,Height,Weight,BloodPressure,HeartRate,Age;

        public UserHealthInfoViewHolder(@NonNull View textview) {
            super(textview);
            BloodPressure = textview.findViewById(R.id.blood_pressureCardView);
            Age = textview.findViewById(R.id.ageCardView);
            HeartRate = textview.findViewById(R.id.heart_rateCardView);
            Height = textview.findViewById(R.id.heightCardView);
            LastPeriodDate = textview.findViewById(R.id.last_period_dateCardView);
            UserName = textview.findViewById(R.id.usernameCardView);
            Weight = textview.findViewById(R.id.weightCardView);
        }
    }
}