package com.alok.testasync;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;


public class DisplayMessageActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    SignInButton signInButton;
    private GoogleSignInClient mSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent0 = getIntent();

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
         GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton = findViewById(R.id.google_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launches the sign in flow, the result is returned in onActivityResult
                //   Intent intent1 = Auth.GoogleSignInApi.getSignInIntent(mSignInClient.asGoogleApiClient());

                 Intent intent = mSignInClient.getSignInIntent();
                 startActivityForResult( intent, RC_SIGN_IN);
            }
        });
    }

//    private void signIn() {
//
//
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

//                    GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (task.isSuccessful()) {
                // Sign in succeeded, proceed with account
                //startActivity(new Intent(DisplayMessageActivity.this, ThirdActivity.class));

                 GoogleSignInAccount acct = task.getResult();

            }

//            if(result.isSuccess()){
//                GoogleSignInAccount acct = result.getSignInAccount();
//               // startActivity(new Intent(DisplayMessageActivity.this, ThirdActivity.class));
//            }

            else {
                // Sign in failed, handle failure and update UI

                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
