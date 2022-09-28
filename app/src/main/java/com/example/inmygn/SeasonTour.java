package com.example.inmygn;

public class SeasonTour {
    //여행 주제
    String data_title;
    //계절명
    String Season;
    //여행지
    String user_address;
    //여행 내용
    String data_content;

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

    public SeasonTour(String data_title, String Season, String user_address, String data_content) {
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
}
