package com.mobcom.troubleshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.databinding.ActivityLoginBinding;
import com.mobcom.troubleshoot.models.Login.Login;
import com.mobcom.troubleshoot.models.Login.LoginData;
import com.mobcom.troubleshoot.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
  private static final String TAG = "LoginActivity";
  private String email, password;
  private APIRequestData ardData; // (webservice)
  private SessionManager sessionManager; // buat create session dll
  private ActivityLoginBinding activityLoginBinding; // bindview jadi ga usah lagi find view by id
  private GoogleSignInClient mGoogleSignInClient;
  private static int RC_SIGN_IN = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // karna pake bind view jadi inflate nya kaya gini
    // sebelumnya kan pake R.id.activity_login sekarang di ubah
    activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
    View view = activityLoginBinding.getRoot();
    setContentView(view);
    // end inflate

    //    status bar hide start
    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //    status bar hide end

    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    // Build a GoogleSignInClient with the options specified by gso.
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    // Check for existing Google Sign In account, if the user is already signed in
    // the GoogleSignInAccount will be non-null.
    GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

    activityLoginBinding.signInButton.setSize(SignInButton.SIZE_STANDARD);
    activityLoginBinding.signInButton.setOnClickListener(v -> signInGoogle());

    // ini cara manggil id dari layoutnya
    activityLoginBinding.btnLogin.setOnClickListener(v -> {
      email = activityLoginBinding.etEmailLogin.getEditText().getText().toString();
      password = activityLoginBinding.etPasswordLogin.getEditText().getText().toString();
      login(email, password);
    });

    activityLoginBinding.gotoRegister.setOnClickListener(v -> {
      Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
      startActivity(intent);
    });
  }

  private void signInGoogle() {
    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
    startActivityForResult(signInIntent, RC_SIGN_IN);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN) {
      // The Task returned from this call is always completed, no need to attach
      // a listener.
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      handleSignInResult(task);
    }
  }

  private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
    try {
      GoogleSignInAccount account = completedTask.getResult(ApiException.class);

      GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
      if (acct != null) {
        String personName = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();

        //Log.d(TAG, "handleSignInResult: "+personName +" "+personGivenName+" "+personFamilyName+" "+personId);
        loginGoogle(personEmail, personId, personGivenName, personFamilyName, personPhoto.toString());
      }

      // Signed in successfully, show authenticated UI.
    } catch (ApiException e) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
    }
  }

  private void loginGoogle(String email, String oauth_id, String firstname, String lastname, String picture){
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<Login> loginGoogleCall = ardData.loginGoogleResponse(email,oauth_id,firstname,lastname,picture);
    loginGoogleCall.enqueue(new Callback<Login>() {
      @Override
      public void onResponse(Call<Login> call, Response<Login> response) {
        if (response.body() != null && response.isSuccessful()) {
          sessionManager = new SessionManager(LoginActivity.this);
          LoginData loginData = response.body().getLoginData();
          sessionManager.createLoginSession(loginData);

          Toast.makeText(LoginActivity.this, "Anda berhasil masuk", Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<Login> call, Throwable t) {
        Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void login(String email, String password) {
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    // form validation
    if (!validateEmail() | !validatePassword()) {
      return;
    }

    // manggil werbservice buat get data dll
    Call<Login> loginCall = ardData.loginResponse(email, password);
    loginCall.enqueue(new Callback<Login>() {
      @Override
      public void onResponse(Call<Login> call, Response<Login> response) {
        if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
          sessionManager = new SessionManager(LoginActivity.this);
          LoginData loginData = response.body().getLoginData();
          sessionManager.createLoginSession(loginData);

          Toast.makeText(LoginActivity.this, "Anda berhasil masuk", Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<Login> call, Throwable t) {
        Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
      }
    });
    // end webservice
  }

  private Boolean validateEmail() {
    String val = activityLoginBinding.etEmailLogin.getEditText().getText().toString();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    if (val.isEmpty()) {
      activityLoginBinding.etEmailLogin.setError("Field cannot be empty");
      return false;
    } else if (!val.matches(emailPattern)) {
      activityLoginBinding.etEmailLogin.setError("Invalid email address");
      return false;
    } else {
      activityLoginBinding.etEmailLogin.setError(null);
      activityLoginBinding.etEmailLogin.setErrorEnabled(false);
      return true;
    }
  }

  private Boolean validatePassword() {
    String val = activityLoginBinding.etPasswordLogin.getEditText().getText().toString();
    String passwordVal = "^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            //"\\A\\w{4,20}\\z" +           //no white spaces
            ".{6,}" +               //at least 6 characters
            "$";
    String noWhiteSpace = "\\A\\w{4,20}\\z";
    if (val.isEmpty()) {
      activityLoginBinding.etPasswordLogin.setError("Field cannot be empty");
      return false;
    } else if (val.length() > 16) {
      activityLoginBinding.etPasswordLogin.setError("Password too long");
      return false;
    } else if (!val.matches(passwordVal)) {
      activityLoginBinding.etPasswordLogin.setError("Password is too weak");
      return false;
    } else if (!val.matches(noWhiteSpace)) {
      activityLoginBinding.etPasswordLogin.setError("White Spaces are not allowed");
      return false;
    } else {
      activityLoginBinding.etPasswordLogin.setError(null);
      activityLoginBinding.etPasswordLogin.setErrorEnabled(false);
      return true;
    }
  }
}