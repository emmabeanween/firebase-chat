package com.example.emmagoldberg.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.w3c.dom.Text;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

private DatabaseReference mReference;
private FirebaseListAdapter<Message> mListAdapter;
private ListView mListView;
private String timeDiff;
private TimeUnit mtimeUnit;
private Button mSendButton;
private EditText mMessageEditText;
private MenuInflater menuInflater;

public static Intent newIntent(Context packageContext,  String chosenRoom){

    Intent i = new Intent(packageContext, MainActivity.class);
    i.putExtra("CHOSEN_ROOM", chosenRoom);
    return i;

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

         menuInflater = getMenuInflater();
         menuInflater.inflate(R.menu.menu, menu);
         return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.leave_chat:
                // destroy session and leave the chat
                SessionChat mChat = new SessionChat(getApplicationContext());
                mChat.setSessionUsername("");
                mChat.setRoomLastIn("");
                // log if set
                Log.i("leaving chat", new SessionChat(getApplicationContext()).getUsernameinSession());
                // leave chat
                Intent leaveChat = new Intent(MainActivity.this, PickUsername.class);
                startActivity(leaveChat);

            default:
                return super.onOptionsItemSelected(item);
        }
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseApp.initializeApp(getApplicationContext());








mReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://fir-chat-d76fb.firebaseio.com/");





        String chosenRoom = getIntent().getStringExtra("CHOSEN_ROOM");
        Log.i("room", "in" + chosenRoom);
        // set session with chosen username in case user closes app and comes back
        SessionChat mSesh = new SessionChat(getApplicationContext());
        mSesh.setRoomLastIn(chosenRoom);

        Query queryRef = mReference.orderByChild("category").equalTo(chosenRoom);

        mListAdapter = new FirebaseListAdapter<Message>(this, Message.class, R.layout.message_layout
        , queryRef) {
            @Override
            protected void populateView(View v, final Message model, int position) {

                TextView mUserNameView = (TextView) v.findViewById(R.id.username_text_view);
                TextView mContent = (TextView) v.findViewById(R.id.content_text_view);
                final TextView mTimeTextView = (TextView) v.findViewById(R.id.time_text_view);

                mUserNameView.setText(model.getUsername());
                Log.i("msg", "username in session" + new SessionChat(getApplicationContext()).getUsernameinSession());
                if (model.getUsername() == new SessionChat(getApplicationContext()).getUsernameinSession()){

                    mUserNameView.setTextColor(Color.BLUE);
                }
                else {

                    mUserNameView.setTextColor(Color.RED);

                }

                mContent.setText(model.getContent());

                 final Handler mHandler = new Handler();


                 final Runnable mRun = new Runnable() {
                    @Override
                    public void run() {




                        long diff = new Date().getTime() - model.getSent().getTime();

                       long diffMinutes =  TimeUnit.MILLISECONDS.toMinutes(diff);
                       long diffHours = TimeUnit.MILLISECONDS.toHours(diff);
                       long diffDays = TimeUnit.MILLISECONDS.toDays(diff);


                        if (diffMinutes < 60){

                            timeDiff = diffMinutes + "m";
                        }

                        else if (diffMinutes >= 60 && diffMinutes < 1440){

                            timeDiff = diffHours + "h";
                        }

                        else if (diffMinutes >= 1440 && diffMinutes < 525600) {

                            timeDiff = diffDays + "d";
                        }

                        else {


                            // message is greater than a year old

                            timeDiff = "1y +";



                        }






                        mTimeTextView.setText(timeDiff);
                        mHandler.postDelayed(this, 60000);


                    }
                };


                mHandler.postDelayed(mRun, 1000);








            }


        };





mListView = (ListView)findViewById(R.id.list_view);
mListView.setAdapter(mListAdapter);


mMessageEditText = (EditText)findViewById(R.id.message_content_edit_text);
final String messageContent = mMessageEditText.getText().toString();



mSendButton = (Button)findViewById(R.id.send_button);
mSendButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {




        Message mNewMessage = new Message();
        mNewMessage.setUsername(new SessionChat(getApplicationContext()).getUsernameinSession());
        mNewMessage.setCategory(getIntent().getStringExtra("CHOSEN_ROOM"));
        mNewMessage.setSent(new Date());
        // allow empty messages
        Log.i("content", mMessageEditText.getText().toString());
        mNewMessage.setContent(mMessageEditText.getText().toString());


        mReference.push().setValue(mNewMessage);
        // clear input
        mMessageEditText.setText("");





    }
});






    }






}
