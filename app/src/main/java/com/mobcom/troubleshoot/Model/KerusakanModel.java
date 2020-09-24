package com.mobcom.troubleshoot.Model;

import com.google.gson.annotations.SerializedName;

public class KerusakanModel {
  @SerializedName("kerusakan_id")
  private int kerusakan_id;

  @SerializedName("biaya")
  private int biaya;

  @SerializedName("jenis")
  private String jenis;

  @SerializedName("nama_kerusakan")
  private String nama_kerusakan;


  public int getKerusakan_id() {
    return kerusakan_id;
  }

  public void setKerusakan_id(int kerusakan_id) {
    this.kerusakan_id = kerusakan_id;
  }

  public int getBiaya() {
    return biaya;
  }

  public void setBiaya(int biaya) {
    this.biaya = biaya;
  }

  public String getJenis() {
    return jenis;
  }

  public void setJenis(String jenis) {
    this.jenis = jenis;
  }

  public String getNama_kerusakan() {
    return nama_kerusakan;
  }

  public void setNama_kerusakan(String nama_kerusakan) {
    this.nama_kerusakan = nama_kerusakan;
  }

  @Override
  public String toString(){
    return nama_kerusakan;
  }
}
