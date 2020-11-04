package com.mobcom.troubleshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.databinding.ActivityLoginBinding;
import com.mobcom.troubleshoot.models.Login.Login;
import com.mobcom.troubleshoot.models.Login.LoginData;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
  private String email, password;
  APIRequestData ardData; // (webservice)
  SessionManager sessionManager; // buat create session dll
  ActivityLoginBinding activityLoginBinding; // bindview jadi ga usah lagi find view by id

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

    // ini cara manggil id dari layoutnya
    activityLoginBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        email = activityLoginBinding.etEmailLogin.getEditText().getText().toString();
        password = activityLoginBinding.etPasswordLogin.getEditText().getText().toString();
        login(email, password);
      }
    });

    activityLoginBinding.gotoRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
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

          Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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