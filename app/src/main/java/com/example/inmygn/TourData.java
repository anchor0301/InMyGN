package com.example.inmygn;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class TourData implements Serializable {

    //여행 주제
    private String data_title;
    //계절명
    private String Season;
    //여행지
    private String user_address;
    //여행 내용
    private String data_content;


    public TourData(String data_title, String season, String user_address, String data_content) {
        this.data_title = data_title;
        this.Season = season;
        this.user_address = user_address;
        this.data_content = data_content;
    }

    public void setData_title(String data_title) {
        this.data_title = data_title;
    }
    public String getData_title() {
        return data_title;
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
