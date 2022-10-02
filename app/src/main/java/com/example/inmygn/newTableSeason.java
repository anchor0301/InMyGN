package com.example.inmygn;

import android.os.Bundle;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class newTableSeason extends AppCompatActivity {
    String key = "WugO7Pgnqoa7fJuWbJ4nMaaIh%2Bvw2l%2F%2FaGF7MGgIyBl4vRTVhumNtnrqkL%2BJDxh94rXo%2BR8DgPREJu8h6AVefQ%3D%3D";
    String address = "http://apis.data.go.kr/6480000/gyeongnamtourseason/gyeongnamtourseasonlist";
    String urlAddress = address + "?serviceKey=" + key + "&pageNo=1&numOfRows=55&resultType=json";

    ArrayList<SeasonTourData> SeasonDTO = new ArrayList<SeasonTourData>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_table_season);

        //공공데이터 받아오기
        getJson();


        final MyAdapter myAdapter = new MyAdapter(this, SeasonDTO);


        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        SeasonDTO.get(position).getData_title(),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }
    //json 파싱후 어뎁터에 넣음
    public void getJson() {

        try {
            //인터넷을 url thread 객체 생성
            findJsonThread findJsonThread = new findJsonThread();
            //thread 실행
            findJsonThread.start();

            try {
                findJsonThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //TODO 삭제
            //SeasonDTO.add(new SeasonTourData("성민2", "여름", "통영시", "성공"));
            //SeasonDTO.add(new SeasonTourData("성민3", "여름1", "통영시", "성공1"));



            String jsonData = findJsonThread.getResult();
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
                    //SeasonDTO.add(new SeasonTourData(data_title, Season, data[i], data_content));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    public class findJsonThread extends Thread {
        private String Result;

        public void run() {

            try {

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

                Result = buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        //결과값 리턴해주는 Getter
        public String getResult() {
            return this.Result;
        }
    }

}





