package com.example.weatherapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText user_location;
    private Button search_button;
    private TextView search_info;
    String key = "998e60ead5535b18eded3c1ae586b811";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_location = findViewById(R.id.user_location);
        search_button = findViewById(R.id.Search_Button);
        search_info = findViewById(R.id.Search_info);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_location.getText().toString().trim().equals("")){ // Проверяю, ввёл ли пользователь название
                    //Если не ввёл, то всплывает окно с запросом на ввод названия.
                    Toast.makeText(MainActivity.this, R.string.Arrr, Toast.LENGTH_LONG).show();
                } else {
                    String city = user_location.getText().toString();
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city
                            + "&APPID=" + key + "&units=metric&lang=ru";

                    new WeatherData().execute(url);

                }
            }
        });
    }

    private class WeatherData extends AsyncTask<String, String, String> {

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}