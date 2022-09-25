package com.example.inmygn;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private String key = "WugO7Pgnqoa7fJuWbJ4nMaaIh%2Bvw2l%2F%2FaGF7MGgIyBl4vRTVhumNtnrqkL%2BJDxh94rXo%2BR8DgPREJu8h6AVefQ%3D%3D";
    private String address = "http://apis.data.go.kr/6480000/gyeongnamtourseason/gyeongnamtourseasonlist";
    private ListView listView;
    private Button btnData;
    ArrayAdapter adapter;

    // 영화 제목을 담을 ArrayList 변수(items) 선언
    ArrayList<String> items = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView1);
        // adapter 스타일 선언 및 items 적용
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        // listView에 adapter 적용
        listView.setAdapter(adapter);
        btnData = (Button)findViewById(R.id.btnData);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        items.clear();


                        String urlAddress = address + "?serviceKey=" + key + "&pageNo=1&numOfRows=55&resultType=json" ;

                        try {
                            System.out.println(urlAddress+"\n\n\n\n");
                            URL url = new URL(urlAddress);
                            InputStream is =  url.openStream();
                            InputStreamReader isr = new InputStreamReader(is);
                            BufferedReader reader = new BufferedReader(isr);

                            StringBuffer buffer = new StringBuffer();
                            String line = reader.readLine();
                            while (line != null) {
                                buffer.append(line + "\n");
                                line = reader.readLine();
                            }

                            String jsonData = buffer.toString();

                            // jsonData를 먼저 JSONObject 형태로 바꾼다.
                            JSONObject obj = new JSONObject(jsonData);

                            // obj의 "gyeongnamtourseasonlist"의 JSONObject를 추출
                            JSONObject gyeongnamtourseasonlist = (JSONObject)obj.get("gyeongnamtourseasonlist");


                            // gyeongnamtourseasonlist "body"의 JSONObject를 추출
                            JSONObject SeasonBody = (JSONObject)gyeongnamtourseasonlist.get("body");

                            // SeasonBody "item"의 JSONObject를 추출
                            JSONObject SeasonItems = (JSONObject)SeasonBody.get("items");



                            // boxOfficeResult의 JSONObject에서 "dailyBoxOfficeList"의 JSONArray 추출
                            JSONArray dailyBoxOfficeList = (JSONArray)SeasonItems.get("item");

                            for (int i = 0; i < dailyBoxOfficeList.length(); i++) {

                                JSONObject temp = dailyBoxOfficeList.getJSONObject(i);

                                String movieNm = temp.getString("data_title");

                                items.add(movieNm);
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        // 리스트뷰의 아이템 클릭 이벤트 > 토스트 메시지 띄우기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = (String)parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
