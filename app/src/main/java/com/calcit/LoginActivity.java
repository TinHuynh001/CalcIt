package com.calcit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;


import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String user_firstname;
    //decls for Google parts
    private GoogleSignInOptions gso;
    private GoogleSignInClient clientG;
    private static final int RESULTCODE_SIGN_IN_GOOGLE = 9001;
    private SignInButton btnGG;


    //decls for Facebook part
    private LoginButton btnFB;
    private CallbackManager callbackManager;
    //private static final int RESULTCODE_SIGN_IN_FACEBOOK= 9002;

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                   AccessToken currentAccessToken)
        {
            if(currentAccessToken == null)
            {
                Toast.makeText(LoginActivity.this, "User signed out.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                loadUserProfile(currentAccessToken);
            }

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Google inits
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



        //Facebook Inits
        btnFB = findViewById(R.id.sign_in_button_Facebook);
        callbackManager = CallbackManager.Factory.create();

        btnFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel()
            {
                Toast.makeText(LoginActivity.this, "Sign in cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error)
            {

            }
        });




    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Build a GoogleSignInClient with the options specified by gso.
        clientG = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //goToMain(account);

        //Also check for logged-in Facebook accounts via the accessToken
        //
        //AccessToken tkn = AccessToken.getCurrentAccessToken();
        //goToMain(tkn);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button_Google:
                signInGoogle();
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        
        switch (requestCode)
        {
            case RESULTCODE_SIGN_IN_GOOGLE:
                //super.onActivityResult(requestCode, resultCode, data);
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
                default:
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            goToMain(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            goToMain((GoogleSignInAccount) null);
        }
    }

    

    //open google sign in
    private void signInGoogle() {
        Intent signInIntent = clientG.getSignInIntent();
        startActivityForResult(signInIntent, RESULTCODE_SIGN_IN_GOOGLE);
    }


    private void loadUserProfile(AccessToken access)
    {
        GraphRequest req = GraphRequest.newMeRequest(access, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response)
            {
                try
                {
                    user_firstname = object.getString("first_name");


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        });

        Bundle params = new Bundle();
        params.putString("fields", "first_name, last_name");
        req.setParameters(params);
        req.executeAsync();
    }


    //
    private void goToMain(@Nullable GoogleSignInAccount account)
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

    private void goToMain(AccessToken token)
    {
        if (token != null && !token.isExpired())
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
