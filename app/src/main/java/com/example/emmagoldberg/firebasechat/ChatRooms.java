package com.example.emmagoldberg.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ChatRooms extends AppCompatActivity {

    private ListView mChatRoomsListView;
    private CustomAdapter mChatRoomsAdapter;

    public static Intent newIntent(Context packageContext){

        Intent i = new Intent(packageContext, ChatRooms.class);
        return i;


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_rooms);


       mChatRoomsListView = (ListView)findViewById(R.id.chat_rooms_list_view);

       final String [] names = {"Movies", "Relationships", "Music", "Travel", "Gaming"};
       int [] icons = {R.drawable.movies_icon, R.drawable.relationship_icon,
               R.drawable.music_icon, R.drawable.travel_icon, R.drawable.gaming_icon};

       mChatRoomsAdapter = new CustomAdapter(getApplicationContext(), names, icons );

       mChatRoomsListView.setAdapter(mChatRoomsAdapter);





        mChatRoomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nameofRoom = names[position];
                Log.i("msg", "name of room is" + nameofRoom);

                // go to room

                Intent gotoChat = MainActivity.newIntent(ChatRooms.this, nameofRoom);
                startActivity(gotoChat);

            }
        });






    }
}

