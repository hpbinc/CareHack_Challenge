package com.example.hance.carehack_challenge;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class first extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "hpbPrefsFile";
    String mobile_number,checker,emailreturn;
    EditText MobileNumber;
    Boolean isChecked;
    CheckBox checkBox;

    public void login(View v){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mobile_number = prefs.getString("mobile_number", "");
        isChecked = prefs.getBoolean("boolean", Boolean.parseBoolean(""));
        checker = MobileNumber.getText().toString();
        if(mobile_number.equals(checker))
        {
            Intent i = new Intent(this,logIn_appo.class);
            startActivity(i);
            finish();
        }
        else
        {

            Toast.makeText(first.this, "else", Toast.LENGTH_SHORT).show();

            new AsyncTask<Void,Void,String>(){

                @Override
                protected String doInBackground(Void... params) {

                    OkHttpClient client = new OkHttpClient();
                    RequestBody body = new FormBody.Builder()
                            .add("phone",checker)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://www.mimos.96.lt/apprec.php")
                            .post(body)
                            .build();

                    try {
                        Response response = client.newCall(request).execute();
                        emailreturn = response.body().string();

                        Log.e("hance",""+emailreturn);


                        if(emailreturn.equals("999"))
                        {

                            first.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(first.this, "Not a registered user!!!", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                        else
                        {
                            Intent i = new Intent(getApplicationContext(),onlyotp.class);
                            startActivity(i);
                        }

                        return response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                    }
            }.execute();

             }
           }

    public void signup(View view){
        Intent i = new Intent(this,sign_up.class);
        startActivity(i);

    }

    public void onClick(View v) {

        boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()) {
            case R.id.checkbox:
                if (checked)

                {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putBoolean("boolean", true);
                    editor.apply();

                }

            else

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        MobileNumber = (EditText) findViewById(R.id.mobileNumber);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        mobile_number = prefs.getString("mobile_number","");
        isChecked = prefs.getBoolean("boolean",true);
        if(isChecked)
            MobileNumber.setText(""+mobile_number);


    }

}
