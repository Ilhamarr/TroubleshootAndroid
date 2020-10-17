package com.mobcom.troubleshoot.models;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class  ServiceModel {
  @SerializedName("kerusakan_id")
  private String kerusakan_id;

  @SerializedName("biaya")
  private int biaya;

  @SerializedName("jenis")
  private String jenis;

  @SerializedName("nama_kerusakan")
  private String nama_kerusakan;

  @SerializedName("keterangan")
  private String keterangan;

  @SerializedName("image")
  private String image;

  public String getKeterangan() {
    return keterangan;
  }

  public void setKeterangan(String keterangan) {
    this.keterangan = keterangan;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public ServiceModel(String kerusakan_id, int biaya, String jenis, String nama_kerusakan) {
    this.kerusakan_id = kerusakan_id;
    this.biaya = biaya;
    this.jenis = jenis;
    this.nama_kerusakan = nama_kerusakan;
  }

  public String getKerusakan_id() {
    return kerusakan_id;
  }

  public void setKerusakan_id(String kerusakan_id) {
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
  public String toString() {
    return "ServiceModel{" +
            "kerusakan_id='" + kerusakan_id + '\'' +
            ", biaya=" + biaya +
            ", jenis='" + jenis + '\'' +
            ", nama_kerusakan='" + nama_kerusakan + '\'' +
            ", keterangan='" + keterangan + '\'' +
            ", image='" + image + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ServiceModel that = (ServiceModel) o;
    return getBiaya() == that.getBiaya() &&
            getKerusakan_id().equals(that.getKerusakan_id()) &&
            getJenis().equals(that.getJenis()) &&
            getNama_kerusakan().equals(that.getNama_kerusakan()) &&
            getKeterangan().equals(that.getKeterangan()) &&
            getImage().equals(that.getImage());
  }

  public static DiffUtil.ItemCallback<ServiceModel> itemCallback = new DiffUtil.ItemCallback<ServiceModel>() {
    @Override
    public boolean areItemsTheSame(@NonNull ServiceModel oldItem, @NonNull ServiceModel newItem) {
      return oldItem.getKerusakan_id().equals(newItem.getKerusakan_id());
    }

    @Override
    public boolean areContentsTheSame(@NonNull ServiceModel oldItem, @NonNull ServiceModel newItem) {
      return oldItem.equals(newItem);
    }
  };

  @BindingAdapter("android:serviceImage")
  public static void loadImage(ImageView imageView, String image){
    Glide.with(imageView)
            .load(image)
            .fitCenter()
            .into(imageView);
  }
}
