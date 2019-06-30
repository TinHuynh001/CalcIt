package com.calcit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;


import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInOptions gso;
    GoogleSignInClient clientG;
    private static final int RESULTCODE_SIGN_IN_GOOGLE = 9001;

    SignInButton btnGG;
    FacebookButtonBase btnFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //The Google sign-in button to authenticate the user. Note that this class only handles the
        //visual aspects of the button. In order to trigger an action, register a listener using
        // setOnClickListener(OnClickListener).

        //Note that you must explicitly call setOnClickListener(OnClickListener). Do not register a
        // listener via XML, or you won't receive your callbacks!
        btnGG = findViewById(R.id.sign_in_button_Google);
        btnGG.setOnClickListener(this);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();

        // Build a GoogleSignInClient with the options specified by gso.

        clientG = GoogleSignIn.getClient(this, gso);

        btnFB = findViewById(R.id.sign_in_button_Facebook);
        btnFB.setOnClickListener(this);





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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button_Google:
                signInGoogle();
                break;
            case R.id.sign_in_button_Facebook:
                signInFacebook();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        switch (requestCode)
        {
            case RESULTCODE_SIGN_IN_GOOGLE:
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            updateUI(null);
        }
    }

    

    //open google sign in
    private void signInGoogle() {
        Intent signInIntent = clientG.getSignInIntent();
        startActivityForResult(signInIntent, RESULTCODE_SIGN_IN_GOOGLE);
    }

    private void signInFacebook()
    {

    }

    private void updateUI(@Nullable GoogleSignInAccount account)
    {
        if (account != null)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
        else
        {
            Toast.makeText(this, "Sign In failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
