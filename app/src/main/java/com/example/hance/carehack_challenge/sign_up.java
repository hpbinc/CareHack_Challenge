package com.example.hance.carehack_challenge;

    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.support.annotation.NonNull;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;
     import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.FirebaseException;
    import com.google.firebase.FirebaseTooManyRequestsException;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.auth.PhoneAuthCredential;
    import com.google.firebase.auth.PhoneAuthProvider;
    import java.io.IOException;
    import java.util.concurrent.TimeUnit;

    import okhttp3.FormBody;
    import okhttp3.OkHttpClient;
    import okhttp3.RequestBody;
    import okhttp3.Request;
    import okhttp3.Response;

public class sign_up extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "hpbPrefsFile";

    EditText MobileNumber,OTPEditview,name,email, address;
    Button Submit,OTPButton;
    String mobile_number,Name,Email,Address;
    View view2,view3,view4;
      // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    boolean mVerificationInProgress = false;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        MobileNumber = (EditText) findViewById(R.id.mobileNumber);
        Submit = (Button) findViewById(R.id.submit);
        OTPEditview = (EditText) findViewById(R.id.otp_editText);
        OTPButton = (Button) findViewById(R.id.otp_button);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        view2 = (View)findViewById(R.id.view2);
        view3 = (View)findViewById(R.id.view3);
        view4 = (View)findViewById(R.id.view4);

        mAuth = FirebaseAuth.getInstance();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("mobile_number", mobile_number);
                editor.apply();
                Intent i = new Intent(getApplicationContext(),first.class);
                startActivity(i);
                finish();
                Toast.makeText(sign_up.this,"Verified"+ phoneAuthCredential,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(sign_up.this,"Verification Fail",Toast.LENGTH_LONG).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    Toast.makeText(sign_up.this,"Invalid mobile number",Toast.LENGTH_LONG).show();
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                   // Toast.makeText(sign_up.this,"quta over" ,Toast.LENGTH_LONG).show();
                    // [END_EXCLUDE]
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided ic_phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                //Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(sign_up.this,"Verification code sent to mobile",Toast.LENGTH_LONG).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                MobileNumber.setVisibility(View.GONE);
                Submit.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                address.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                view4.setVisibility(View.GONE);
                OTPButton.setVisibility(View.VISIBLE);
                OTPEditview.setVisibility(View.VISIBLE);
                // ...
            }
        };



        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Name = name.getText().toString();
                Email = email.getText().toString();
                mobile_number = MobileNumber.getText().toString();
                Address = address.getText().toString();

                //async
                new AsyncTask<Void,Void,String>(){

                    @Override
                    protected String doInBackground(Void... params) {



                        Log.e("EXCEPTION ", "at registertoken");


                        OkHttpClient client = new OkHttpClient();
                        RequestBody body = new FormBody.Builder()
                                .add("name", Name)
                                .add("email",Email)
                                .add("phone",mobile_number)
                                .add("address",Address)
                                .build();
                        Request request = new Request.Builder()
                                .url("http://www.mimos.96.lt/appsend.php")
                                .post(body)
                                .build();

                        Log.e("hance","afterpost");

                        try {
                            Response response = client.newCall(request).execute();
                            //Log.d(TAG, "doInBackground() called with: " + "params = [" + response.body().string() + "]");
                            return response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }
                }.execute();



                mobile_number = MobileNumber.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91"+MobileNumber.getText().toString(),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        sign_up.this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks
            }
        });

        OTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, OTPEditview.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }
        });

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("mobile_number", mobile_number);
                            editor.putString("email",Email);
                            editor.apply();
                            Toast.makeText(sign_up.this,"Verified",Toast.LENGTH_LONG).show();
                            FirebaseUser user = task.getResult().getUser();
                            Intent i = new Intent(getApplicationContext(),first.class);
                            startActivity(i);
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(sign_up.this,"Invalid OTP",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
}
