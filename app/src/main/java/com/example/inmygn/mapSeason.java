package com.example.inmygn;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.collections.MarkerManager;

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
import java.util.Iterator;
import java.util.List;

public class mapSeason extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String key = "WugO7Pgnqoa7fJuWbJ4nMaaIh%2Bvw2l%2F%2FaGF7MGgIyBl4vRTVhumNtnrqkL%2BJDxh94rXo%2BR8DgPREJu8h6AVefQ%3D%3D";
    String address = "http://apis.data.go.kr/6480000/gyeongnamtourseason/gyeongnamtourseasonlist";
    String urlAddress = address + "?serviceKey=" + key + "&pageNo=1&numOfRows=55&resultType=json";
    SeasonTourData clinicItem;
    ArrayList<SeasonTourData> SeasonDTO = new ArrayList<SeasonTourData>();
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_season);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapSeason.this);

        //공공데이터 받아오기
        //getJson();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        Context context = this;

        //클러스터 받기
        ClusterManager clusterManager = new ClusterManager<>(this, mMap);

        //맵 로딩이 성공한다면
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {  //맵 제한 범위 설정
                mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(
                        new LatLng(33.986, 125.98), // 남서 방향
                        new LatLng(38.726, 129.60)  // 북동 방향
                ));
                mMap.setMinZoomPreference(9.0f);
                mMap.setMaxZoomPreference(10.0f);


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.4605301, 128.2131958), 9));//카메라 위치 설정


            }
        });

        mMap.setOnCameraIdleListener(clusterManager);

        mMap.setOnMarkerClickListener(clusterManager);

        String[] locationName = {"통영시", "고성군", "진주시", "진주시", "사천시", "사천시", "남해군", "고성군", "진주시", "진주시", "통영시", "통영시", "통영시"};

        //TODO 임시 마커들 삭제해야함
        for (int i = 0; i < locationName.length; i++) {
            //지역 위도, 경도 저장
            //Location location = addrToPoint(context, SeasonDTO.get(i).getTitle());
            Location location = addrToPoint(context, locationName[i]);
            clinicItem = new SeasonTourData(location.getLatitude(), location.getLongitude(), "통영시" + i, "겨울", "통영시", "놀아봐요!");

            clusterManager.addItem(clinicItem);

        } // i 개수만큼 item 추가


        //마커를 클릭시 행동
        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener() {
            @Override
            public boolean onClusterItemClick(ClusterItem item) {

                Log.d("onMarkerClick", "START: ");


                Toast.makeText(mapSeason.this,"클릭 마커", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        //클러스터를 클릭시 행동
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<SeasonTourData>() {
            @Override
            public boolean onClusterClick(Cluster<SeasonTourData> cluster) {

                Log.d("Cluster Click", "START");
                Toast.makeText(mapSeason.this,"클릭 클리스터", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


    }



    //지역 좌표 찾기
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




