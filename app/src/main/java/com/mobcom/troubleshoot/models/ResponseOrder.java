package com.mobcom.troubleshoot.models;

import com.google.gson.annotations.SerializedName;

public class ResponseOrder {
  @SerializedName("status")
  private String Status;

  @SerializedName("message")
  private String message;

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
