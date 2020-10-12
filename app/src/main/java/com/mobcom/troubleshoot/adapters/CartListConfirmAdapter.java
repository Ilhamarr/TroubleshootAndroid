package com.mobcom.troubleshoot.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.troubleshoot.databinding.ItemListpesananBinding;
import com.mobcom.troubleshoot.models.CartItem;

public class CartListConfirmAdapter extends ListAdapter<CartItem, CartListConfirmAdapter.CartvH > {

  public CartListConfirmAdapter() {
    super(CartItem.itemCallback);
  }

  @NonNull
  @Override
  public CartvH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    ItemListpesananBinding itemListpesananBinding = ItemListpesananBinding.inflate(layoutInflater,parent,false);
    return new CartvH(itemListpesananBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull CartvH holder, int position) {
    holder.itemListpesananBinding.setCartItem(getItem(position));
    holder.itemListpesananBinding.executePendingBindings();
  }

  class CartvH extends RecyclerView.ViewHolder {

    ItemListpesananBinding itemListpesananBinding;
    public CartvH(@NonNull ItemListpesananBinding itemListpesananBinding) {
      super(itemListpesananBinding.getRoot());
      this.itemListpesananBinding = itemListpesananBinding;
    }
  }
}
