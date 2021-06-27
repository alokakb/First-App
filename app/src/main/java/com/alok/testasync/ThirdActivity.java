package com.alok.testasync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class ThirdActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private ImageView photo;
    private TextView name;
    private TextView Email;
    private TextView ID;
    private Button sign_out;

    private GoogleSignInClient mSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Get the Intent that started this activity and extract the string
//        Intent intent = getIntent();

        photo = findViewById(R.id.photo);
        name = findViewById(R.id.name);
        Email = findViewById(R.id.name);
        ID = findViewById(R.id.ID);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mSignInClient = GoogleSignIn.getClient(this, gso);

        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.GoogleSignInApi.signOut(mSignInClient.asGoogleApiClient()).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (Status.RESULT_SUCCESS.isSuccess())
                            gotoMainActivity();
                        else
                            Toast.makeText(ThirdActivity.this, "Logout unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                });
                ;

            }
        });
    }

    private void gotoMainActivity() {
        startActivity(new Intent(ThirdActivity.this, DisplayMessageActivity.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

   private void handleSignInResult(GoogleSignInResult result)
   {
       if(result.isSuccess())
       {
           GoogleSignInAccount account = result.getSignInAccount();

           name.setText(account.getDisplayName());
           Email.setText(account.getEmail());
           ID.setText(account.getId());

           Picasso.get().load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(photo);
       }
       else
           gotoMainActivity();
   }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mSignInClient.asGoogleApiClient());

        if(opr.isDone())
        {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }
        else
        {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSignInResult(result);
                }
            });
        }
    }
}