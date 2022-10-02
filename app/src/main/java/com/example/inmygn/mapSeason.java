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
import com.google.android.gms.maps.model.VisibleRegion;
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

    private ClusterManager<SeasonTourData> clusterManager;
    ArrayList<TourData> Tours;
    ArrayList<Location> Tour_Address;
    Context context = this;
    final String TAG = "LogMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_season);


        Tours = (ArrayList<TourData>)getIntent().getSerializableExtra("Tour");
        Tour_Address = (ArrayList<Location>) getIntent().getSerializableExtra("Tour_addr");


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        //클러스터 받기
        clusterManager = new ClusterManager<>(this, mMap);

        mMap.setOnCameraIdleListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);

        //맵 로딩이 성공한다면
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {  //맵 제한 범위 설정
                mMap.setLatLngBoundsForCameraTarget(new LatLngBounds(
                        new LatLng(33.986, 125.98), // 남서 방향
                        new LatLng(38.726, 129.60)  // 북동 방향
                ));
                mMap.setMinZoomPreference(8.0f);
                mMap.setMaxZoomPreference(12.0f);


                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.4605301, 128.2131958), 9));//카메라 위치 설정
            }
        });


        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                VisibleRegion vr = mMap.getProjection().getVisibleRegion();
                double left = vr.latLngBounds.southwest.longitude;
                double top = vr.latLngBounds.northeast.latitude;
                double right = vr.latLngBounds.northeast.longitude;
                double bottom = vr.latLngBounds.southwest.latitude;
                Log.d("테스트용!", "left = " + String.valueOf(left) +
                        "top = " + String.valueOf(top) +
                        "right = " + String.valueOf(right) +
                        "bottom = " + String.valueOf(bottom) + "\n");
                if (clusterManager != null) clusterManager.clearItems();
                findMarker(left, top, right, bottom);
            } // 카메라 시점 이동
        });

        //마커를 클릭시 행동
        clusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener() {
            @Override
            public boolean onClusterItemClick(ClusterItem item) {

                Log.d("onMarkerClick", "START: ");
                Toast.makeText(mapSeason.this, "클릭 마커", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        //클러스터를 클릭시 행동
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<SeasonTourData>() {
            @Override
            public boolean onClusterClick(Cluster<SeasonTourData> cluster) {
                Log.d("Cluster Click", "START");
                Toast.makeText(mapSeason.this, "클릭 클리스터", Toast.LENGTH_SHORT).show();

                return false;
            }
        });


    }

    public void findMarker(double left, double top, double right, double bottom) {
        for (int i = 0; i < Tour_Address.size(); i++) {
            if (Tour_Address.get(i).getLongitude() >= left && Tour_Address.get(i).getLongitude() <= right) {
                if (Tour_Address.get(i).getLatitude() >= bottom && Tour_Address.get(i).getLatitude() <= top) {
                    Location location = Tour_Address.get(i);
                    SeasonTourData clinicItem = new SeasonTourData(location.getLatitude(), location.getLongitude(),
                            Tours.get(i).getData_title(), Tours.get(i).getSeason(), Tours.get(i).getUser_address(), Tours.get(i).getData_content());
                    clusterManager.addItem(clinicItem);
                }
            }
        }
    } // 경도와 위도 범위 안에 있는 주소정보를 찾아 마커를 추가




}




