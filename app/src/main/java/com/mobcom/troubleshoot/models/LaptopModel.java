package com.mobcom.troubleshoot.models;

import com.google.gson.annotations.SerializedName;

public class LaptopModel {
  @SerializedName("laptop_id")
  private int laptop_id;

  @SerializedName("merk")
  private String merk;

  public int getLaptop_id() {
    return laptop_id;
  }

  public void setLaptop_id(int laptop_id) {
    this.laptop_id = laptop_id;
  }

  public String getMerk() {
    return merk;
  }

  public void setMerk(String merk) {
    this.merk = merk;
  }

  @Override
  public String toString() {
    return merk;
  }
}
