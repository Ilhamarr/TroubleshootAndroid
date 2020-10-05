package com.mobcom.troubleshoot.models;

import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

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

  @BindingAdapter("android:setVal")
  public static void getSelectedSpinnerValue(Spinner spinner, int quantity){
    spinner.setSelection(quantity - 1, true);
  }

  public static DiffUtil.ItemCallback<CartItem> itemCallback = new DiffUtil.ItemCallback<CartItem>() {
    @Override
    public boolean areItemsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
      return oldItem.getService().equals(newItem.getService());
    }

    @Override
    public boolean areContentsTheSame(@NonNull CartItem oldItem, @NonNull CartItem newItem) {
      return oldItem.equals(newItem);
    }
  };

}
