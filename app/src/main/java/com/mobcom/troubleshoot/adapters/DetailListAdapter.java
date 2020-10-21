package com.mobcom.troubleshoot.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.troubleshoot.databinding.ItemPesananDetailOrderBinding;
import com.mobcom.troubleshoot.models.ItemOrderModel;
import com.mobcom.troubleshoot.models.OrderHistoryModel;

public class DetailListAdapter extends ListAdapter<ItemOrderModel, DetailListAdapter.DetailListViewHolder> {

  public DetailListAdapter() {
    super(ItemOrderModel.itemCallback);
  }

  @NonNull
  @Override
  public DetailListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    ItemPesananDetailOrderBinding itemPesananDetailOrderBinding = ItemPesananDetailOrderBinding.inflate(layoutInflater, parent, false);
    return new DetailListViewHolder(itemPesananDetailOrderBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull DetailListViewHolder holder, int position) {
    ItemOrderModel itemOrder = getItem(position);
    holder.itemPesananDetailOrderBinding.setItemOrder(itemOrder);
    holder.itemPesananDetailOrderBinding.executePendingBindings();
  }

  class DetailListViewHolder extends RecyclerView.ViewHolder {

    ItemPesananDetailOrderBinding itemPesananDetailOrderBinding;
    public DetailListViewHolder(ItemPesananDetailOrderBinding binding) {
      super(binding.getRoot());
      this.itemPesananDetailOrderBinding = binding;
    }
  }
}
