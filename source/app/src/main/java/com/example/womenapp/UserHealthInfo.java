package com.example.womenapp;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserHealthInfo {

    private String UserName;
    private String DateOfBirth;
    private String Height;
    private String Weight;
    private String LastPeriodDate;
    private String HeartRate;
    private String BloodPressure;

    public UserHealthInfo(String userName, String dateOfBirth, String height, String weight, String lastPeriodDate, String heartRate, String bloodPressure) {
        UserName = userName;
        DateOfBirth = dateOfBirth;
        Height = height;
        Weight = weight;
        LastPeriodDate = lastPeriodDate;
        HeartRate = heartRate;
        BloodPressure = bloodPressure;
    }

    public UserHealthInfo() {

    }
    public String getBloodPressure() {
        return BloodPressure;
    }
    public String getDateOfBirth() {
        return DateOfBirth;
    }
    public String getHeartRate() {
        return HeartRate;
    }
    public String getHeight() {
        return Height;
    }
    public String getLastPeriodDate() {
        return LastPeriodDate;
    }
    public String getUserName() {
        return UserName;
    }
    public String getWeight() {
        return Weight;
    }

}
