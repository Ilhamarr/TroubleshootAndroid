package com.mobcom.troubleshoot.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.troubleshoot.Fragment.CartFragment;
import com.mobcom.troubleshoot.databinding.CartRowBinding;
import com.mobcom.troubleshoot.models.CartItem;

public class CartListAdapter extends ListAdapter<CartItem, CartListAdapter.CartVH> {

  public CartListAdapter() {
    super(CartItem.itemCallback);
  }

  @NonNull
  @Override
  public CartVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    CartRowBinding cartRowBinding = CartRowBinding.inflate(layoutInflater, parent, false);
    return new CartVH(cartRowBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull CartVH holder, int position) {
    holder.cartRowBinding.setCartItem(getItem(position));
    holder.cartRowBinding.executePendingBindings();
  }

  class CartVH extends RecyclerView.ViewHolder {

    CartRowBinding cartRowBinding;
    public CartVH(@NonNull CartRowBinding cartRowBinding) {
      super(cartRowBinding.getRoot());
      this.cartRowBinding = cartRowBinding;
    }
  }

  public interface CartInterface {
    void deleteItem(CartItem cartItem);

    void changeQuantity(CartItem cartItem, int quantity);
  }
}
