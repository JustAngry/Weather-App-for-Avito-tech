package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText user_location;
    private Button search_button;
    private TextView search_info;
    private Switch switchButton;
    String key = "998e60ead5535b18eded3c1ae586b811";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_location = findViewById(R.id.user_location);
        search_button = findViewById(R.id.Search_Button);
        search_info = findViewById(R.id.Search_info);

        String forecastUrl = "https://api.openweathermap.org/data/2.5/forecast?q=";

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_location.getText().toString().trim().equals("")){ // Проверяю, ввёл ли
                    // пользователь название
                    //Если не ввёл, то всплывает окно с запросом на ввод названия.
                    Toast.makeText(MainActivity.this, R.string.Arrr, Toast.LENGTH_LONG).show();
                } else {
                    String city = user_location.getText().toString();
                    String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city
                            + "&APPID=" + key + "&units=metric&lang=ru";

                    new WeatherData().execute(url);

                }
            }
        });
    }

    private class WeatherData extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            search_info.setText("Погодите...");
        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer stringBuffer = new StringBuffer();
                String line = "";

                while((line = reader.readLine()) != null) {
                    stringBuffer.append(line).append("\n");
                    return stringBuffer.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if(httpURLConnection != null){
                    httpURLConnection.disconnect();
                }

                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                search_info.setText("Температура: " + jsonObject.getJSONObject("main")
                        .getDouble("temp") + "\n"

                + "Ощущается как: " + jsonObject.getJSONObject("main")
                        .getDouble("feels like") + "\n"

                + "Давление: " + jsonObject.getJSONObject("main")
                        .getInt("pressure") + "\n"

                + "Скорость ветра: " + jsonObject.getJSONObject("wind")
                        .getDouble("wind"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}