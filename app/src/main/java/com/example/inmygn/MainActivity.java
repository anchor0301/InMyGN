package com.example.inmygn;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnMap;
    Button btnArraylist;
    Button btnPager;
    String TAG="aaa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMap = findViewById(R.id.btnMap);
        btnPager = findViewById(R.id.btnPager);
        btnArraylist = findViewById(R.id.btnArraylist);


        ArrayList<TourData> Tours = getJson();
        ArrayList<Location> Tour_Address = new ArrayList<>();
        for (int i = 0; i < Tours.size(); i++) {
            Location location;
            String address = Tours.get(i).getUser_address();
            switch (address) {
                case "창원시":
                    location = new Location("");
                    location.setLatitude(35.2280106);
                    location.setLongitude(128.6818625);

                    Tour_Address.add(location);
                    break;
                case "진주시":
                    location = new Location("");
                    location.setLatitude(35.1799817);
                    location.setLongitude(128.1076213);

                    Tour_Address.add(location);
                    break;
                case "통영시":
                    location = new Location("");
                    location.setLatitude(34.8544227);
                    location.setLongitude(128.433182);

                    Tour_Address.add(location);
                    break;
                case "사천시":
                    location = new Location("");
                    location.setLatitude(35.0037788);
                    location.setLongitude(128.06418499999998);

                    Tour_Address.add(location);
                    break;
                case "김해시":
                    location = new Location("");
                    location.setLatitude(35.2285451);
                    location.setLongitude(128.8893517);

                    Tour_Address.add(location);
                    break;
                case "밀양시":
                    location = new Location("");
                    location.setLatitude(35.5037598);
                    location.setLongitude(128.7464415);

                    Tour_Address.add(location);
                    break;
                case "거제시":
                    location = new Location("");
                    location.setLatitude(34.880642699999996);
                    location.setLongitude(128.6210824);

                    Tour_Address.add(location);
                    break;
                case "양산시":
                    location = new Location("");
                    location.setLatitude(35.3350072);
                    location.setLongitude(129.03716889999998);

                    Tour_Address.add(location);
                    break;
                case "의령군":
                    location = new Location("");
                    location.setLatitude(35.3221896);
                    location.setLongitude(128.26165799999998);

                    Tour_Address.add(location);
                    break;
                case "함안군":
                    location = new Location("");
                    location.setLatitude(35.272559099999995);
                    location.setLongitude(128.4064797);

                    Tour_Address.add(location);
                    break;
                case "창녕군":
                    location = new Location("");
                    location.setLatitude(35.544556299999996);
                    location.setLongitude(128.4922143);

                    Tour_Address.add(location);
                    break;
                case "고성군":
                    location = new Location("");
                    location.setLatitude(34.973);
                    location.setLongitude(128.3222);

                    Tour_Address.add(location);
                    break;
                case "남해군":
                    location = new Location("");
                    location.setLatitude(34.8376721);
                    location.setLongitude(127.89242339999998);

                    Tour_Address.add(location);
                    break;
                case "함양군":
                    location = new Location("");
                    location.setLatitude(35.520461399999995);
                    location.setLongitude(127.7251763);

                    Tour_Address.add(location);
                    break;
                case "하동군":
                    location = new Location("");
                    location.setLatitude(35.0672108);
                    location.setLongitude(127.7512687);

                    Tour_Address.add(location);
                    break;
                case "산청군":
                    location = new Location("");
                    location.setLatitude(35.4155885);
                    location.setLongitude(127.87349809999999);

                    Tour_Address.add(location);
                    break;
                case "거창군":
                    location = new Location("");
                    location.setLatitude(35.6867229);
                    location.setLongitude(127.90951549999998);

                    Tour_Address.add(location);
                    break;
                case "합천군":
                    location = new Location("");
                    location.setLatitude(35.566575799999995);
                    location.setLongitude(128.1657995);

                    Tour_Address.add(location);
                    break;

                default:

                    Tour_Address.add( addrToPoint(this,address));
                    break;


            }
        } // 주소만 위도경보로 변환하여 모아놓음

        btnArraylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), newTableSeason.class);
                intent.putExtra("Tour", Tours);
                intent.putExtra("Tour_addr", Tour_Address);
                startActivity(intent);
            }
        });
        btnPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewpagerActivity.class);
                startActivity(intent);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), mapSeason.class);
                intent.putExtra("Tour", Tours);
                intent.putExtra("Tour_addr", Tour_Address);
                startActivity(intent);
            }
        });
    }
        public static Location addrToPoint (Context context, String locationName){
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


        public ArrayList<TourData> getJson () {
            ArrayList<TourData> TourList = new ArrayList<>();


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
                        TourList.add(new TourData(data_title, Season, data[j], data_content));
                        Log.d(TAG, "getJson: TourList"+ TourList.get(j).getUser_address());
                    }
                }
                        return TourList;

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
