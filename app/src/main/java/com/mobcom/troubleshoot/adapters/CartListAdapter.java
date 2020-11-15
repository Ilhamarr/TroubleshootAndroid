package com.mobcom.troubleshoot.adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.troubleshoot.Fragment.CartFragment;
import com.mobcom.troubleshoot.databinding.CartRowBinding;
import com.mobcom.troubleshoot.models.CartItem;

public class CartListAdapter extends ListAdapter<CartItem, CartListAdapter.CartVH> {

  private CartInterface cartInterface;

  public CartListAdapter(CartInterface cartInterface) {
    super(CartItem.itemCallback);
    this.cartInterface = cartInterface;
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

      cartRowBinding.deleteProductButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
          builder.setMessage("Anda akan menghapus item layanan ini");
          builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              cartInterface.deleteItem(getItem(getAdapterPosition()));
            }
          });
          builder.setNegativeButton("Cancel", null);
          AlertDialog alertDialog = builder.create();
          alertDialog.show();

          //cartInterface.deleteItem(getItem(getAdapterPosition()));

        }
      });

    }
  }

  public interface CartInterface {
    void deleteItem(CartItem cartItem);

    void changeQuantity(CartItem cartItem, int quantity);

    void addQuantity(CartItem cartItem);

    void decreaseQuantity(CartItem cartItem);
  }
}
