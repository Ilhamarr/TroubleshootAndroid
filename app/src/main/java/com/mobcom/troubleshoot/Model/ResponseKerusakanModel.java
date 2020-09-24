package com.mobcom.troubleshoot.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseKerusakanModel {
  @SerializedName("status")
  private String Status;

  @SerializedName("data")
  private List<KerusakanModel> dataKerusakan;

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  public List<KerusakanModel> getDataKerusakan() {
    return dataKerusakan;
  }

  public void setDataKerusakan(List<KerusakanModel> dataKerusakan) {
    this.dataKerusakan = dataKerusakan;
  }
}
