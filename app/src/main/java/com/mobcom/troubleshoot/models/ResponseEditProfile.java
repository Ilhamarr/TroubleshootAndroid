package com.mobcom.troubleshoot.models;

import com.google.gson.annotations.SerializedName;
import com.mobcom.troubleshoot.models.Login.LoginData;

public class ResponseEditProfile {
  @SerializedName("data")
  private LoginData loginData;

  @SerializedName("status")
  private String status;

  @SerializedName("message")
  private String message;

  public ResponseEditProfile(String status, String message) {
    this.status = status;
    this.message = message;
  }

  public void setLoginData(LoginData loginData){
    this.loginData = loginData;
  }

  public LoginData getLoginData(){
    return loginData;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "ResponseKonfirmasiBayar{" +
            "status=" + status +
            ", message='" + message + '\'' +
            '}';
  }
}
