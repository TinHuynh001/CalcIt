package com.calcit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

import android.view.View;

public class LoginActivity extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient clientG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();

        // Build a GoogleSignInClient with the options specified by gso.

        clientG = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Build a GoogleSignInClient with the options specified by gso.
        clientG = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        //updateUI(account);
    }

    public void loginGoogle(View view)
    {

    }
}
