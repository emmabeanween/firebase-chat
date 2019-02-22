package com.example.emmagoldberg.firebasechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {


    private Context mContext;
    private String [] mChatRoomNames;
    private int [] mChatRoomIcons;



        public CustomAdapter(Context context, String [] chatRoomNames, int [] chatRoomIcons ) {

            mContext = context;

            mChatRoomNames = chatRoomNames;
            mChatRoomIcons = chatRoomIcons;


        }

    @Override
    public Object getItem(int position) {
        return mChatRoomNames[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return mChatRoomNames.length;
    }

    @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View mview = LayoutInflater.from(mContext).inflate(R.layout.chat_room_layout, parent, false);
            TextView mNameView = (TextView)mview.findViewById(R.id.chat_room_name);
            ImageView mEachView = mview.findViewById(R.id.chat_room_icon);

           String eachName =  mChatRoomNames[position];
           mNameView.setText(eachName);


           int eachPic = mChatRoomIcons[position];
           mEachView.setImageResource(eachPic);




            return mview;
        }

    }








