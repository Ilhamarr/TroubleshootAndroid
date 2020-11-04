package com.mobcom.troubleshoot.models;

import com.google.gson.annotations.SerializedName;

public class ResponseKonfirmasiBayar {
  @SerializedName("status")
  private boolean status;

  @SerializedName("message")
  private String message;

  public ResponseKonfirmasiBayar(boolean status, String message) {
    this.status = status;
    this.message = message;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
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
