package com.example.inmygn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {


    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<SeasonTourData> SeasonDTO;

    public MyAdapter(Context context, ArrayList<SeasonTourData> data) {
        mContext = context;
        SeasonDTO = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return SeasonDTO.size();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public SeasonTourData getItem(int i) {
        return SeasonDTO.get(i);
    }


    @Override
    public View getView(int i, View converView, ViewGroup viewGroup) {


        View view = mLayoutInflater.inflate(R.layout.listview_item, null);

        //화면에 보여질 데이터를참조한다
        TextView data_title = (TextView) view.findViewById(R.id.data_title);
        TextView user_address = (TextView) view.findViewById(R.id.user_address);

        //데이터를 set해준다
        data_title.setText(SeasonDTO.get(i).getData_title());
        user_address.setText(SeasonDTO.get(i).getUser_address());


        return view;


    }


}
