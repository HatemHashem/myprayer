package com.example.myprayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private EditText cityEditText;
    private EditText countryEditText;
    private EditText methodEditText;
    private Button changeButton;
    public static final String CITY_EXTRA="com.example.myprayer.CITY_EXTRA";
    public static final String METHOD_EXTRA="com.example.myprayer.METHOD_EXTRA";
    public static final String COUNTRY_EXTRA="com.example.myprayer.COUNTRY_EXTRA";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        cityEditText=findViewById(R.id.city_editext);
        countryEditText=findViewById(R.id.country_edittext);
        methodEditText=findViewById(R.id.method_edittext);
        changeButton=findViewById(R.id.change_button);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                String city=cityEditText.getText().toString().trim();
                String country=countryEditText.getText().toString().trim();
                String method=methodEditText.getText().toString().trim();
                if(!city.isEmpty()&&!country.isEmpty()&&!method.isEmpty()) {
                    intent.putExtra(CITY_EXTRA,city);
                    intent.putExtra(COUNTRY_EXTRA,country);
                    intent.putExtra(METHOD_EXTRA,method);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    Toast.makeText(SettingsActivity.this,"all the fields are required",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
