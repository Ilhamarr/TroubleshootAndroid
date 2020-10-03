package com.mobcom.troubleshoot.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKerusakanModel {
  @SerializedName("status")
  private String Status;

  @SerializedName("data")
  private List<KerusakanModel> listKerusakan;

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  public List<KerusakanModel> getListKerusakan() {
    return listKerusakan;
  }

  public void setListKerusakan(List<KerusakanModel> listKerusakan) {
    this.listKerusakan = listKerusakan;
  }
}

