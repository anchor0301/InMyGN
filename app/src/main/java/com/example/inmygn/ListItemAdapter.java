package com.example.inmygn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {
ArrayList<SeasonTour> SeasonDTO = new ArrayList<>();
Context context;

    @Override
    public int getCount() {
        return SeasonDTO.size();
    }

    @Override
    public Object getItem(int i) {
        return SeasonDTO.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(SeasonTour item){
        SeasonDTO.add(item);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        SeasonTour seasonDTO = SeasonDTO.get(i);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(R.layout.listview_item,viewGroup,false);
        }

        //화면에 보여질 데이터를참조한다
        TextView data_title = view.findViewById(R.id.data_title);
        TextView user_address =view.findViewById(R.id.user_address);

        //데이터를 set해준다
        data_title.setText(seasonDTO.getData_title());
        user_address.setText(seasonDTO.getUser_address());
        return view;
    }


}
