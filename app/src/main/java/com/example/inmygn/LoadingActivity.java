package com.example.inmygn;

import androidx.appcompat.app.AppCompatActivity;
import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity  {
    final String TAG = "LoadingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        ArrayList<TourData> Tours = getJson();


        ArrayList<Location> Tour_Address = new ArrayList<>();
        for(int i = 0 ; i < Tours.size(); i++) {
            Log.d("", "convert  "+Tours.size());
            Tour_Address.add(addrToPoint(this, Tours.get(i).getUser_address()));
        } // 병원 주소만 위도경보로 변환하여 모아놓음

        Intent intent = new Intent(LoadingActivity.this, mapSeason.class);
        intent.putExtra("Tour", Tours);
        intent.putExtra("Tour_addr", Tour_Address);
        startActivity(intent);
    }

    public static Location addrToPoint(Context context, String locationName) {
        Location location = new Location("");
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(locationName, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses != null) {
            for (int i = 0; i < addresses.size(); i++) {
                Address lating = addresses.get(i);
                location.setLatitude(lating.getLatitude());
                location.setLongitude(lating.getLongitude());
            }
        }
        return location;
    }


    public ArrayList<TourData> getJson() {

        ArrayList<TourData> TourList = new ArrayList<>();
        String[] address = {"통영시","고성군","진주시","남해군","하동군","창원시","합천군","창녕군","거제시","함안군"};
        // TODO 삭제
        for (int i=0; i <41 ; i++) {
            int j=(int)((Math.random()*10000)%10);
            TourList.add(new TourData("성민2", "여름", address[j], "성공"));
            if(i>=40){
                Log.d(TAG, "getJson: 끝!");
                return TourList;
            }

        }

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

            //TODO 주석 제거
            TourList = new ArrayList<>();



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
                    TourList.add(new TourData(data_title, Season, data[i], data_content));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return TourList;
    }

    public class findJsonThread extends Thread {
        private String Result;

        public void run() {

            try {
                String key = "7RANMEU5OfGEBQk2nW5QVCcwIdHD0uw0fOcDva5DC%2B6mSbygxfCmdkIcrrxUdCO998L5UTs6ptBItoAfNsfxWg%3D%3D";
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