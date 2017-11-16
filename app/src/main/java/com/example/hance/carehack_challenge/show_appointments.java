package com.example.hance.carehack_challenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class show_appointments extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_appointments);

        textView = (TextView)findViewById(R.id.text);



        int id =1;
        if (getIntent().getExtras() != null) {
            for (String a : getIntent().getExtras().getStringArrayList("items_to_parse")) {

                textView.append("["+id+"] " + "" + a + "\n\n");
                id++;
                Log.e("hance", "Data :::: " + a);
            }
        }

    }

}

