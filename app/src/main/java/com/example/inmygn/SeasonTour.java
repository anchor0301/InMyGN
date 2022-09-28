package com.example.inmygn;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SeasonTour {
    //여행 주제
    String data_title;
    //계절명
    String Season;
    //여행지
    String user_address;
    //여행 내용
    String data_content;


    public SeasonTour(String data_title, String Season, String user_address,String data_content) {
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
