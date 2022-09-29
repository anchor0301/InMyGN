package com.example.inmygn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SeasonTour> SeasonDTO;

    public MyAdapter(Context context, ArrayList<SeasonTour> data) {
        mContext = context;
        SeasonDTO = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return SeasonDTO.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public SeasonTour getItem(int position) {
        return SeasonDTO.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.activity_new_table_season, null);

        //여행 컨셉
        TextView data_title = (TextView)view.findViewById(R.id.data_title);
        //여행지
        TextView user_address = (TextView)view.findViewById(R.id.user_address);

        data_title.setText(SeasonDTO.get(position).getData_title());
        user_address.setText(SeasonDTO.get(position).getUser_address());

        return view;
    }
}
