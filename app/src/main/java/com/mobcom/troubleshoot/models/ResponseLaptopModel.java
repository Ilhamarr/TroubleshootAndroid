package com.mobcom.troubleshoot.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLaptopModel {
  @SerializedName("status")
  private String Status;

  @SerializedName("data")
  private List<LaptopModel> listLaptop;

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  public List<LaptopModel> getListLaptop() {
    return listLaptop;
  }

  public void setListLaptop(List<LaptopModel> listLaptop) {
    this.listLaptop = listLaptop;
  }
}
