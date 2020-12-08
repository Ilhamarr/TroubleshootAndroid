package com.mobcom.troubleshoot.Activity;

import androidx.annotation.NonNull;
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
  private APIRequestData ardData;
  private SessionManager sessionManager;
  private ActivityLoginBinding activityLoginBinding;
  private GoogleSignInClient mGoogleSignInClient;
  private static final int RC_SIGN_IN = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
    View view = activityLoginBinding.getRoot();
    setContentView(view);

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

    activityLoginBinding.signInButton.setSize(SignInButton.SIZE_WIDE);
    activityLoginBinding.signInButton.setOnClickListener(v -> signInGoogle());

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
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();

        Log.d(TAG, "handleSignInResult: " + personEmail + " " + personGivenName + " " + personFamilyName + " " + personId + " " + personPhoto);

        loginGoogle(personEmail, personId, personGivenName, personFamilyName);
      }

      // Signed in successfully, show authenticated UI.
    } catch (ApiException e) {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
    }
  }

  private void loginGoogle(String email, String oauth_id, String firstname, String lastname) {
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<Login> loginGoogleCall = ardData.loginGoogleResponse(email, oauth_id, firstname, lastname);
    loginGoogleCall.enqueue(new Callback<Login>() {
      @Override
      public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
        assert response.body() != null;
        if (response.body().isStatus() && response.isSuccessful()) {
          sessionManager = new SessionManager(LoginActivity.this);
          LoginData loginData = response.body().getLoginData();
          Log.d(TAG, "onResponse: " + loginData.toString());
          sessionManager.createLoginSession(loginData);

          Toast.makeText(LoginActivity.this, "Anda berhasil masuk", Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
          signOutGoogle();
        }
      }

      @Override
      public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
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

    Call<Login> loginCall = ardData.loginResponse(email, password);
    loginCall.enqueue(new Callback<Login>() {
      @Override
      public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
        assert response.body() != null;
        if (response.body().isStatus() && response.isSuccessful()) {
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
      public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
        Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void signOutGoogle() {
    mGoogleSignInClient.signOut()
            .addOnCompleteListener(this, task -> Log.d(TAG, "onComplete: " + "google account has been sign out"));
  }

  private Boolean validateEmail() {
    String val = activityLoginBinding.etEmailLogin.getEditText().getText().toString();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    if (val.isEmpty()) {
      activityLoginBinding.etEmailLogin.setError("Tidak boleh kosong");
      return false;
    } else if (!val.matches(emailPattern)) {
      activityLoginBinding.etEmailLogin.setError("Alamat email salah");
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
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=\\S+$)" +
            ".{6,}" +               //at least 6 characters
            "$";

    if (val.isEmpty()) {
      activityLoginBinding.etPasswordLogin.setError("Tidak boleh kosong");
      return false;
    } else if (val.length() > 16) {
      activityLoginBinding.etPasswordLogin.setError("Kata sandi terlalu panjang");
      return false;
    } else if (!val.matches(passwordVal)) {
      activityLoginBinding.etPasswordLogin.setError("Minimal 6 karakter");
      return false;
    } else {
      activityLoginBinding.etPasswordLogin.setError(null);
      activityLoginBinding.etPasswordLogin.setErrorEnabled(false);
      return true;
    }
  }
}