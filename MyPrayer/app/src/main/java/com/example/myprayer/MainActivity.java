package com.example.myprayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "https://api.aladhan.com/v1/";
    private static final String TAG = "MainActivity";
    private TextView dayGregorian;
    private TextView monthGregorian;
    private TextView dayHijri;
    private TextView monthHijri;
    private TextView dateTextView;
    private RecyclerView recyclerView;
    private TextView dayTextView;
    private TextView countryCityTextView;
    private ArrayList<Prayer> prayers = new ArrayList<>();
    public static final int CHANGE_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dayGregorian = findViewById(R.id.day_gregorian);
        monthGregorian = findViewById(R.id.month_gregorian);
        dayHijri = findViewById(R.id.day_hijri);
        monthHijri = findViewById(R.id.month_hijri);
        dateTextView = findViewById(R.id.date);
        recyclerView = findViewById(R.id.recyclerview);
        dayTextView=findViewById(R.id.weekday);
        countryCityTextView=findViewById(R.id.country);
        String defaultCity="cairo";
        String defaultCountry="egypt";
        int defaultMethod=3;
        init(defaultCity, defaultCountry, defaultMethod);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_item:
                Toast.makeText(this, "to do", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, CHANGE_SETTINGS);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHANGE_SETTINGS && resultCode == RESULT_OK) {
            String country = data.getStringExtra(SettingsActivity.COUNTRY_EXTRA);
            String city = data.getStringExtra(SettingsActivity.CITY_EXTRA);
            int method = Integer.parseInt(data.getStringExtra(SettingsActivity.METHOD_EXTRA));
            init(city, country, method);
        } else {
            Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
        }
    }

    public void init(String city, String country, int method) {
        PrayerAdapter prayerAdapter = new PrayerAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(prayerAdapter);
        countryCityTextView.setText(city+", "+country);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PrayerApi prayerApi = retrofit.create(PrayerApi.class);
        Call<Response> call = prayerApi.getTimings(city, country, method);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d(TAG, "onResponse: " + response.body());
                Hijri hijri= response.body().getData().getDate().getHijri();
                Gregorian gregorian= response.body().getData().getDate().getGregorian();
                String dayH = hijri.getDay();
                String monthH =hijri.getMonth().getAr();
                String date =  gregorian.getDate();
                String dayG =gregorian.getDay();
                String monthG =gregorian.getMonth().getEn();
                Timings timing = response.body().getData().getTimings();

                String fajr = timing.getFajr();
                String sunrise = timing.getSunrise();
                String zuhr = timing.getDhuhr();
                String asr = timing.getAsr();
                String magrib = timing.getMaghrib();
                String isha = timing.getIsha();
                String weekDay=response.body().getData().getDate().getGregorian().getWeekday().getEn();
                dayHijri.setText(String.valueOf(dayH));
                monthHijri.setText(monthH);
                dayGregorian.setText(dayG);
                monthGregorian.setText(monthG);
                dateTextView.setText(date);
                dayTextView.setText(weekDay);
                prayerAdapter.setPrayer(new Prayer("Fajr", fajr));
                prayerAdapter.setPrayer(new Prayer("Sunrise", sunrise));
                prayerAdapter.setPrayer(new Prayer("Zuhr", zuhr));
                prayerAdapter.setPrayer(new Prayer("Asr", asr));
                prayerAdapter.setPrayer(new Prayer("Magrib", magrib));
                prayerAdapter.setPrayer(new Prayer("Isha", isha));


            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });
    }
}
