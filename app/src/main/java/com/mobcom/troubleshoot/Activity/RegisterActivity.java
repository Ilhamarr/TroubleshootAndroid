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
import com.mobcom.troubleshoot.databinding.ActivityRegisterBinding;
import com.mobcom.troubleshoot.models.Register.Register;
import com.mobcom.troubleshoot.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
  private String firstName, lastname, email, password;
  APIRequestData ardData;
  ActivityRegisterBinding activityRegisterBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
    View view = activityRegisterBinding.getRoot();
    setContentView(view);

    //    status bar hide start
    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    //    status bar hide end

    activityRegisterBinding.btnRegister.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        firstName = activityRegisterBinding.etFirstNameReg.getEditText().getText().toString();
        lastname = activityRegisterBinding.etLastNameReg.getEditText().getText().toString();
        email = activityRegisterBinding.etEmailReg.getEditText().getText().toString();
        password = activityRegisterBinding.etPasswordReg.getEditText().getText().toString();
        register(firstName, lastname, email, password);
      }
    });

    activityRegisterBinding.gotoLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
      }
    });

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
        if (response.body().isStatus() && response.isSuccessful()) {
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
    String val = activityRegisterBinding.etFirstNameReg.getEditText().getText().toString();
    String noWhiteSpace = "\\A\\w{4,20}\\z";
    String anyletter = "(^[a-zA-Z]+$)";

    if (val.isEmpty()) {
      activityRegisterBinding.etFirstNameReg.setError("Field cannot be empty");
      return false;
    } else if (!val.matches(anyletter)) {
      activityRegisterBinding.etFirstNameReg.setError("Otherwise letters are not allowed");
      return false;
    } else if (!val.matches(noWhiteSpace)) {
      activityRegisterBinding.etFirstNameReg.setError("White Spaces are not allowed");
      return false;
    } else {
      activityRegisterBinding.etFirstNameReg.setError(null);
      activityRegisterBinding.etFirstNameReg.setErrorEnabled(false);
      return true;
    }
  }

  private Boolean validateLastName() {
    String val = activityRegisterBinding.etLastNameReg.getEditText().getText().toString();
    String anyletter = "(^[a-zA-Z]+$)";
    String noWhiteSpace = "\\A\\w{4,20}\\z";

    if (val.isEmpty()) {
      activityRegisterBinding.etLastNameReg.setError("Field cannot be empty");
      return false;
    } else if (!val.matches(anyletter)) {
      activityRegisterBinding.etLastNameReg.setError("Otherwise letters are not allowed");
      return false;
    } else if (!val.matches(noWhiteSpace)) {
      activityRegisterBinding.etLastNameReg.setError("White Spaces are not allowed");
      return false;
    } else {
      activityRegisterBinding.etLastNameReg.setError(null);
      activityRegisterBinding.etLastNameReg.setErrorEnabled(false);
      return true;
    }
  }

  private Boolean validateEmail() {
    String val = activityRegisterBinding.etEmailReg.getEditText().getText().toString();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    if (val.isEmpty()) {
      activityRegisterBinding.etEmailReg.setError("Field cannot be empty");
      return false;
    } else if (!val.matches(emailPattern)) {
      activityRegisterBinding.etEmailReg.setError("Invalid email address");
      return false;
    } else {
      activityRegisterBinding.etEmailReg.setError(null);
      activityRegisterBinding.etEmailReg.setErrorEnabled(false);
      return true;
    }
  }

  private Boolean validatePassword() {
    String val = activityRegisterBinding.etPasswordReg.getEditText().getText().toString();
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
      activityRegisterBinding.etPasswordReg.setError("Field cannot be empty");
      return false;
    } else if (val.length() > 16) {
      activityRegisterBinding.etPasswordReg.setError("Password too long");
      return false;
    } else if (!val.matches(passwordVal)) {
      activityRegisterBinding.etPasswordReg.setError("Password is too weak");
      return false;
    } else if (!val.matches(noWhiteSpace)) {
      activityRegisterBinding.etPasswordReg.setError("White Spaces are not allowed");
      return false;
    } else {
      activityRegisterBinding.etPasswordReg.setError(null);
      activityRegisterBinding.etPasswordReg.setErrorEnabled(false);
      return true;
    }
  }
}