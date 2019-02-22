package com.example.emmagoldberg.firebasechat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;

public class PickUsername extends AppCompatActivity {

    private TextView mUsernameTextView;
    private EditText mEdittextUsername;
    private TextView mFireBaseTextView;
    private EditText mFireUsernameEdit;
    private Button mSignInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);


        // see if session is set
        if (new SessionChat(getApplicationContext()).getUsernameinSession()!=""){

            Log.i("msg", "log user in with " + new SessionChat(getApplicationContext()).getUsernameinSession());
            Intent newIntent = MainActivity.newIntent(PickUsername.this, new SessionChat(getApplicationContext()
            ).getRoomLastIn());

        }
         else {

            Log.i("msg", "don't log user in");
        }








        mUsernameTextView = (TextView)findViewById(R.id.pick_username_text_view);
        Typeface mFace = Typeface.createFromAsset(getAssets(), "font/summer.ttf");
        mUsernameTextView.setTypeface(mFace);
        mUsernameTextView.setTextSize(40f);


        mFireBaseTextView = (TextView)findViewById(R.id.firebase_text_view);
        Typeface anotherFace = Typeface.createFromAsset(getAssets(), "font/summer.ttf");
        mFireBaseTextView.setTypeface(anotherFace);
        mFireBaseTextView.setTextSize(50f);

        mFireUsernameEdit =   (EditText)(EditText) findViewById(R.id.pick_username_edit_text);
        mFireUsernameEdit.getLayoutParams().width = 850;


        mSignInButton = (Button)findViewById(R.id.sign_in_button);
        mSignInButton.setVisibility(View.INVISIBLE);




                 mEdittextUsername = (EditText)findViewById(R.id.pick_username_edit_text);
                 mEdittextUsername.addTextChangedListener(new TextWatcher() {
                     @Override
                     public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                     }

                     @Override
                     public void onTextChanged(CharSequence s, int start, int before, int count) {

                         if (s.toString()!="") {

                             // set sign in button to be clickable

                             mSignInButton.setVisibility(View.VISIBLE);


                         }

                         else {
                             // before user's picked a username, don't let them sign in
                             mSignInButton.setVisibility(View.INVISIBLE);

                         }

                     }

                     @Override
                     public void afterTextChanged(Editable s) {

                     }
                 });




         mSignInButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 // sign in the user if their username is long enough



                 EditText mUsernameEditText =  (EditText)(EditText) findViewById(R.id.pick_username_edit_text);
                 String chosenUsername = mUsernameEditText.getText().toString();
                 if (chosenUsername.length() < 6){

                     Toast.makeText(PickUsername.this, "username should be longer than six characters",
                             Toast.LENGTH_SHORT).show();
                 }

                 else {

                     SessionChat mSesh = new SessionChat(getApplicationContext());
                     mSesh.setSessionUsername(chosenUsername);
                     // go to chat room selection page

                     // see if set
                     Log.i("msg", "user picked username" + new SessionChat(getApplicationContext()).getUsernameinSession());

                     Intent chatIntent = ChatRooms.newIntent(PickUsername.this);
                     startActivity(chatIntent);

                 }


             }
         });






    }
}
