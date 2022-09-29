package com.example.inmygn;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

    ArrayList<SeasonTour> SeasonDTO = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_season);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(mapSeason.this);


        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        Context context = this;

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

            String jsonData = buffer.toString();

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
                    SeasonDTO.add(new SeasonTour(data_title, Season, data[j], data_content));

                }

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Iterator 통한 전체 조회
        Iterator<SeasonTour> iterator = SeasonDTO.iterator();

        while (iterator.hasNext()) {
            SeasonTour e = iterator.next();//hasNext를 해서 다음요소가 있는지 확인해야지만, e.next해서 그 요소를 불러올 수가 있다.

            //지역을 콘솔창에 출력
            System.out.println(e);
            //지역이름을 찾아서 좌표값을 리턴
            Location cityHallLocation = addrToPoint(context, e.getUser_address());
            //지역 위도, 경도 저장
            LatLng location = new LatLng(cityHallLocation.getLatitude(), cityHallLocation.getLongitude());
            //맵 마커 생성
            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(location)        //좌표 입력
                    .title(e.getData_title())       //마커 타이틀명
                    .snippet(e.getData_content());    //부가 설명
            googleMap.addMarker(markerOptions);     // 마커 등록

        } //end While


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.4605301, 128.2131958), 9));//카메라 위치 설정
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);     // 지도 유형 설정
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(YN, 13));

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



//
//    @Override
//    public void onMapReady(final GoogleMap googleMap) {
//        mMap = googleMap;
//        Context context = this;
//
//
//
//        double latitude = 35.1655662;
//        double longitude = 128.0987056;
//        LatLng YN = new LatLng(latitude, longitude);
//
//        MarkerOptions markerOptions = new MarkerOptions();         // 마커 생성
//        markerOptions.position(YN)
//                .snippet("한국의 자랑")         // 마커 설명
//                .title("연암공대");                         // 마커 제목
//        mMap.addMarker(markerOptions);
//
//
///***
// - zoom Size
// 1: 세계
// 5: 대륙
// 10: 도시
// 15: 거리
// 20: 건물
//
//
//
// - setMapType
//
// https://developers.google.com/maps/documentation/android-sdk/reference/com/google/android/libraries/maps/GoogleMap?hl=ko#public-void-setmaptype-int-type
//
// MAP_TYPE_NORMAL: 기본 지도.
// MAP_TYPE_SATELLITE: 위성 이미지입니다.
// MAP_TYPE_HYBRID: 도로와 라벨이 포함된 위성 이미지입니다.
// MAP_TYPE_TERRAIN: 지형 데이터입니다.
// MAP_TYPE_NONE: 기본 지도 타일이 없습니다.
// ***/
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(YN, 13));
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL );     // 지도 유형 설정
//
//    }


}

