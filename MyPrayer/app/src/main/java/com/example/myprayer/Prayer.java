package com.example.myprayer;

public class Prayer {
    private String prayer;
    private String prayerTime;

    public Prayer(String prayer, String prayerTime) {
        this.prayer = prayer;
        this.prayerTime = prayerTime;
    }

    public String getPrayer() {
        return prayer;
    }

    public String getPrayerTime() {
        return prayerTime;
    }
}
