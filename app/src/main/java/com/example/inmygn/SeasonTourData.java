package com.example.inmygn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

public class SeasonTourData implements ClusterItem, Serializable {
    //여행 주제
    private String data_title;
    //계절명
    private String Season;
    //여행지
    private String user_address;
    //여행 내용
    private String data_content;

    private LatLng mPosition;


    public SeasonTourData(String data_title, String Season, String user_address, String data_content) {
        /**
         * data_title = 여행 주제
         * Season = 계절
         * user_address = 여행지
         * data_content = 여행 내용
         **/
        this.data_title = data_title;
        this.Season = Season;
        this.user_address = user_address;
        this.data_content = data_content;
    }

    public SeasonTourData(double lat, double lng, String data_title, String Season, String user_address, String data_content) {
        /**
         * lat 위도
         * lng 경도
         * data_title = 여행 주제
         * Season = 계절
         * user_address = 여행지
         * data_content = 여행 내용
         **/

        mPosition = new LatLng(lat, lng);
        this.data_title = data_title;
        this.Season = Season;
        this.user_address = user_address;
        this.data_content = data_content;
    }


    @NonNull
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Nullable
    @Override
    public String getTitle() {
        return null;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }

    @Override
    public String toString() {
        return "SeasonTour{" +
                "data_title='" + data_title + '\'' +
                ", Season='" + Season + '\'' +
                ", user_address='" + user_address + '\'' +
                ", data_content='" + data_content + '\'' +
                '}';
    }

    public String getData_title() {
        return data_title;
    }

    public void setData_title(String data_title) {
        this.data_title = data_title;
    }

    public String getSeason() {
        return Season;
    }

    public void setSeason(String season) {
        Season = season;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getData_content() {
        return data_content;
    }

    public void setData_content(String data_content) {
        this.data_content = data_content;
    }


}
