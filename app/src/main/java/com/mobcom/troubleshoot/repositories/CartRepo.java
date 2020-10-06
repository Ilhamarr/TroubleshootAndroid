package com.mobcom.troubleshoot.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.ServiceModel;

import java.util.ArrayList;
import java.util.List;

public class CartRepo {

  private MutableLiveData<List<CartItem>> mutableCart = new MutableLiveData<>();

  public LiveData<List<CartItem>> getCart() {
    if (mutableCart.getValue() == null) {
      initCart();
    }
    return mutableCart;
  }

  public void initCart() {
    mutableCart.setValue(new ArrayList<CartItem>());
  }

  public boolean addItemToCart(ServiceModel service) {
    if (mutableCart.getValue() == null) {
      initCart();
    }
    List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
    for (CartItem cartItem : cartItemList) {
      if (cartItem.getService().getKerusakan_id().equals(service.getKerusakan_id())) {
        if (cartItem.getQuantity() == 5) {
          return false;
        }
        int index = cartItemList.indexOf(cartItem);
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemList.set(index, cartItem);

        mutableCart.setValue(cartItemList);

        return true;
      }
    }
    CartItem cartItem = new CartItem(service, 1);
    cartItemList.add(cartItem);
    mutableCart.setValue(cartItemList);
    return true;
  }

  public void removeItemFromCart(CartItem cartItem) {
    if (mutableCart.getValue() == null) {
      return;
    }
    List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());
    cartItemList.remove(cartItem);
    mutableCart.setValue(cartItemList);
  }

  public void changeQuantity(CartItem cartItem, int quantity) {
    if (mutableCart.getValue() == null) return;

    List<CartItem> cartItemList = new ArrayList<>(mutableCart.getValue());

    CartItem updatedItem = new CartItem(cartItem.getService(), quantity);
    cartItemList.set(cartItemList.indexOf(cartItem), updatedItem);

    mutableCart.setValue(cartItemList);
  }
}
