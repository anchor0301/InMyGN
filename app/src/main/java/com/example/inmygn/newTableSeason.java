package com.example.inmygn;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class newTableSeason extends AppCompatActivity {
    String jsonData;
    ListView listView;
    ListItemAdapter adapter;

    ArrayList<SeasonTour> SeasonDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_table_season);


        new Thread(() -> {


            try {
                String key = "WugO7Pgnqoa7fJuWbJ4nMaaIh%2Bvw2l%2F%2FaGF7MGgIyBl4vRTVhumNtnrqkL%2BJDxh94rXo%2BR8DgPREJu8h6AVefQ%3D%3D";
                String address = "http://apis.data.go.kr/6480000/gyeongnamtourseason/gyeongnamtourseasonlist";
                String urlAddress = address + "?serviceKey=" + key + "&pageNo=1&numOfRows=55&resultType=json";
                URL url = new URL(urlAddress);
                InputStream is = url.openStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr);

                StringBuffer buffer = new StringBuffer();
                String line = reader.readLine();
                while (line != null) {
                    buffer.append(line + "\n");
                    line = reader.readLine();
                }

                System.out.println("성민");
                System.out.println("성민" + jsonData);
                jsonData = buffer.toString();

                // jsonData를 먼저 JSONObject 형태로 바꾼다.
                JSONObject obj = new JSONObject(jsonData);

                // obj의 "gyeongnamtourseasonlist"의 JSONObject를 추출
                JSONObject gyeongnamtourseasonlist = (JSONObject) obj.get("gyeongnamtourseasonlist");

                // gyeongnamtourseasonlist "body"의 JSONObject를 추출
                JSONObject SeasonBody = (JSONObject) gyeongnamtourseasonlist.get("body");

                // SeasonBody "item"의 JSONObject를 추출
                JSONObject SeasonItems = (JSONObject) SeasonBody.get("items");

                // boxOfficeResult의 JSONObject에서 "dailyBoxOfficeList"의 JSONArray 추출
                JSONArray dailyBoxOfficeList = (JSONArray) SeasonItems.get("item");

                for (int i = 0; i < dailyBoxOfficeList.length(); i++) {

                    JSONObject temp = dailyBoxOfficeList.getJSONObject(i);

                    //여행지
                    String user_address = temp.getString("user_address");

                    //문자열(슬라이싱)자르기
                    // '/'를 기준으로 문자열을 자른다.
                    String data[] = user_address.split("/");

                    for (int j = 0; j < data.length; j++) {


                        String data_title = temp.getString("data_title");//주제
                        String Season = temp.getString("category_name2");//계절명
                        String data_content = temp.getString("data_content");//내용

                        //SeasonTour객체에 맞춰 ArrayList에 넣기
                        adapter.addItem(new SeasonTour(data_title, Season, data[j], data_content));

                    }

                }

                listView.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();

            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        listView = findViewById(R.id.listView);

        adapter = new ListItemAdapter();


    }

}


