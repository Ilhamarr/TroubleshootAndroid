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
import com.mobcom.troubleshoot.models.Register.Register;
import com.mobcom.troubleshoot.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
  private TextInputLayout etFirstName, etLastName, etEmail, etPassword;
  private String firstName, lastname, email, password;
  private Button btnRegist;
  private TextView tvgotoLogin;
  APIRequestData ardData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    //    status bar hide start
    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //    status bar hide end

    etFirstName = findViewById(R.id.etFirstNameReg);
    etLastName = findViewById(R.id.etLastNameReg);
    etEmail = findViewById(R.id.etEmailReg);
    etPassword = findViewById(R.id.etPasswordReg);
    btnRegist = findViewById(R.id.btnRegister);
    tvgotoLogin = findViewById(R.id.gotoLogin);

    btnRegist.setOnClickListener(this);
    tvgotoLogin.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnRegister:
        firstName = etFirstName.getEditText().getText().toString();
        lastname = etLastName.getEditText().getText().toString();
        email = etEmail.getEditText().getText().toString();
        password = etPassword.getEditText().getText().toString();
        register(firstName, lastname, email, password);
        break;
      case R.id.gotoLogin:
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        break;
    }
  }

  private void register(String firstName, String lastname, String email, String password) {
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    //form validation
    if (!validateFirstName() | !validateLastName() | !validateEmail() | !validatePassword()) {
      return;
    }

    Call<Register> registerCall = ardData.registerResponse(firstName, lastname, email, password);
    registerCall.enqueue(new Callback<Register>() {
      @Override
      public void onResponse(Call<Register> call, Response<Register> response) {
        if (response.body() != null && response.isSuccessful() && response.body().isStatus()) {
          Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<Register> call, Throwable t) {
        Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
      }
    });

    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
    finish();
  }

  private Boolean validateFirstName() {
    String val = etFirstName.getEditText().getText().toString();
    String noWhiteSpace = "\\A\\w{4,20}\\z";
    String anyletter = "(^[a-zA-Z]+$)";

    if (val.isEmpty()) {
      etFirstName.setError("Field cannot be empty");
      return false;
    } else if (!val.matches(anyletter)) {
      etFirstName.setError("Otherwise letters are not allowed");
      return false;
    } else if (!val.matches(noWhiteSpace)) {
      etFirstName.setError("White Spaces are not allowed");
      return false;
    } else {
      etFirstName.setError(null);
      etFirstName.setErrorEnabled(false);
      return true;
    }
  }

  private Boolean validateLastName() {
    String val = etLastName.getEditText().getText().toString();
    String anyletter = "(^[a-zA-Z]+$)";
    String noWhiteSpace = "\\A\\w{4,20}\\z";

    if (val.isEmpty()) {
      etLastName.setError("Field cannot be empty");
      return false;
    } else if (!val.matches(anyletter)) {
      etLastName.setError("Otherwise letters are not allowed");
      return false;
    } else if (!val.matches(noWhiteSpace)) {
      etLastName.setError("White Spaces are not allowed");
      return false;
    } else {
      etLastName.setError(null);
      etLastName.setErrorEnabled(false);
      return true;
    }
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