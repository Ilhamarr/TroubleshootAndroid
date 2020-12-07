package com.mobcom.troubleshoot.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.databinding.ActivityRegisterBinding;
import com.mobcom.troubleshoot.models.Register.Register;

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

    activityRegisterBinding.btnRegister.setOnClickListener(v -> {
      firstName = activityRegisterBinding.etFirstNameReg.getEditText().getText().toString();
      lastname = activityRegisterBinding.etLastNameReg.getEditText().getText().toString();
      email = activityRegisterBinding.etEmailReg.getEditText().getText().toString();
      password = activityRegisterBinding.etPasswordReg.getEditText().getText().toString();
      register(firstName, lastname, email, password);
    });

    activityRegisterBinding.gotoLogin.setOnClickListener(v -> {
      Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
      startActivity(intent);
      finish();
    });

  }

  private void register(String firstName, String lastname, String email, String password) {
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);

    if (!validateFirstName() | !validateLastName() | !validateEmail() | !validatePassword()) {
      return;
    }

    Call<Register> registerCall = ardData.registerResponse(firstName, lastname, email, password);
    registerCall.enqueue(new Callback<Register>() {
      @Override
      public void onResponse(@NonNull Call<Register> call, @NonNull Response<Register> response) {
        assert response.body() != null;
        if (response.body().isStatus() && response.isSuccessful()) {
          Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        } else {
          Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(@NonNull Call<Register> call, @NonNull Throwable t) {
        Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
      }
    });

    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
    finish();
  }

  private Boolean validateFirstName() {
    String val = activityRegisterBinding.etFirstNameReg.getEditText().getText().toString();
    String anyletter = "^" +
            "([a-zA-Z]*)" +      //any letter
//            "(?=\\S+$)" +
//            ".{2,}" +
            "$";

    if (val.isEmpty()) {
      activityRegisterBinding.etFirstNameReg.setError("Tidak boleh kosong");
      return false;
    } else if (val.length() < 2) {
      activityRegisterBinding.etFirstNameReg.setError("Minimal 2 karakter");
      return false;
    } else if (!val.matches(anyletter)) {
      activityRegisterBinding.etFirstNameReg.setError("Selain huruf tidak diperbolehkan, misal spasi, angka");
      return false;
    } else {
      activityRegisterBinding.etFirstNameReg.setError(null);
      activityRegisterBinding.etFirstNameReg.setErrorEnabled(false);
      return true;
    }
  }

  private Boolean validateLastName() {
    String val = activityRegisterBinding.etLastNameReg.getEditText().getText().toString();
    String anyletter = "^" +
            "([a-zA-Z]*)" +      //any letter
            "$";

    if (val.isEmpty()) {
      activityRegisterBinding.etLastNameReg.setError("Tidak boleh kosong");
      return false;
    } else if (val.length() < 2) {
      activityRegisterBinding.etLastNameReg.setError("Minimal 2 karakter");
      return false;
    } else if (!val.matches(anyletter)) {
      activityRegisterBinding.etLastNameReg.setError("Selain huruf tidak diperbolehkan, misal spasi, angka");
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
      activityRegisterBinding.etEmailReg.setError("Tidak boleh kosong");
      return false;
    } else if (!val.matches(emailPattern)) {
      activityRegisterBinding.etEmailReg.setError("Alamat email salah");
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
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=\\S+$)" +
            ".{6,}" +               //at least 6 characters
            "$";

    if (val.isEmpty()) {
      activityRegisterBinding.etPasswordReg.setError("Tidak boleh kosong");
      return false;
    } else if (val.length() > 16) {
      activityRegisterBinding.etPasswordReg.setError("Kata sandi terlalu panjang");
      return false;
    } else if (val.length() < 6) {
      activityRegisterBinding.etPasswordReg.setError("Minimal 6 karakter");
      return false;
    } else if (!val.matches(passwordVal)) {
      activityRegisterBinding.etPasswordReg.setError("Minimal 1 huruf besar, 1 angka, dan tidak boleh ada spasi");
      return false;
    } else {
      activityRegisterBinding.etPasswordReg.setError(null);
      activityRegisterBinding.etPasswordReg.setErrorEnabled(false);
      return true;
    }
  }
}