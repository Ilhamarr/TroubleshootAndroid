package com.mobcom.troubleshoot.models;

import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class CartItem {

  private ServiceModel service;
  private int quantity;

  public CartItem(ServiceModel service, int quantity) {
    this.service = service;
    this.quantity = quantity;
  }

  public ServiceModel getService() {
    return service;
  }

  public void setService(ServiceModel service) {
    this.service = service;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String formatRp(int number){
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    return formatRupiah.format(number);
  }

  @Override
  public String toString() {
    return "CartItem{" +
            "service=" + service +
            ", quantity=" + quantity +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartItem cartItem = (CartItem) o;
    return getQuantity() == cartItem.getQuantity() &&
            getService().equals(cartItem.getService());
  }

//  @BindingAdapter("android:setVal")
////  public int getQuantityTextViewValue() {
////    return quantity;
////  }

  public static DiffUtil.ItemCallback<CartItem> itemCallback = new DiffUtil.ItemCallback<CartItem>() {
    @Override
    public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
      //return oldItem.getService().equals(newItem.getService());
      return oldItem.getQuantity() == newItem.getQuantity();
    }

    @Override
    public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
      return oldItem.equals(newItem);
    }
  };

}
