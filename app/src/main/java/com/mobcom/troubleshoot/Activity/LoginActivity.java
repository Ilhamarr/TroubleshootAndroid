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
import com.mobcom.troubleshoot.models.Login.Login;
import com.mobcom.troubleshoot.models.Login.LoginData;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
  private TextInputLayout etEmail, etPassword;
  private Button btnLogin;
  private TextView tvRegist;
  private String email, password;
  APIRequestData ardData;
  SessionManager sessionManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    //    status bar hide start
    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //    status bar hide end

    etEmail = findViewById(R.id.etEmailLogin);
    etPassword = findViewById(R.id.etPasswordLogin);
    btnLogin = findViewById(R.id.btnLogin);
    tvRegist = findViewById(R.id.gotoRegister);
    btnLogin.setOnClickListener(this);
    tvRegist.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnLogin:
        email = etEmail.getEditText().getText().toString();
        password = etPassword.getEditText().getText().toString();
        login(email, password);
        break;
      case R.id.gotoRegister:
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
        break;
    }
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

  }

  private Boolean validateEmail() {
    String val = etEmail.getEditText().getText().toString();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    if (val.isEmpty()) {
      etEmail.setError("Field cannot be empty");
      return false;
    } else if (!val.matches(emailPattern)) {
      etEmail.setError("Invalid email address");
      return false;
    } else {
      etEmail.setError(null);
      etEmail.setErrorEnabled(false);
      return true;
    }
  }

  private Boolean validatePassword() {
    String val = etPassword.getEditText().getText().toString();
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
      etPassword.setError("Field cannot be empty");
      return false;
    } else if (val.length() > 16) {
      etPassword.setError("Password too long");
      return false;
    } else if (!val.matches(passwordVal)) {
      etPassword.setError("Password is too weak");
      return false;
    } else if (!val.matches(noWhiteSpace)) {
      etPassword.setError("White Spaces are not allowed");
      return false;
    } else {
      etPassword.setError(null);
      etPassword.setErrorEnabled(false);
      return true;
    }
  }

}