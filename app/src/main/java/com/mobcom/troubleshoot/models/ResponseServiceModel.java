package com.mobcom.troubleshoot.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseServiceModel {
  @SerializedName("status")
  private String Status;

  @SerializedName("data")
  private List<ServiceModel> listService;

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  public List<ServiceModel> getListService() {
    return listService;
  }

  public void setListService(List<ServiceModel> listService) {
    this.listService = listService;
  }
}

