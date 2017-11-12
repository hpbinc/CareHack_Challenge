package com.example.hance.carehack_challenge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class first extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "hpbPrefsFile";
    String mobile_number,checker;
    EditText MobileNumber;

    public void login(View v){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mobile_number = prefs.getString("mobile_number", "No name defined");
        checker = MobileNumber.getText().toString();
        if(mobile_number.equals(checker))
        {
            Intent i = new Intent(this,appointment.class);
            startActivity(i);
        }
        else
        {
            Toast.makeText(this,"Mobile Number You Entered is not verified", Toast.LENGTH_SHORT).show();
        }
           }

    public void signup(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        MobileNumber = (EditText) findViewById(R.id.mobileNumber);
    }
}
