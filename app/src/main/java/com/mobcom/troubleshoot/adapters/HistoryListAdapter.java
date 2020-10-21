package com.mobcom.troubleshoot.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.troubleshoot.databinding.CardItemOrderHistoryBinding;
import com.mobcom.troubleshoot.models.OrderHistoryModel;

public class HistoryListAdapter extends ListAdapter<OrderHistoryModel, HistoryListAdapter.HistoryViewHolder> {

  HistoryInterface historyInterface;
  public HistoryListAdapter(HistoryInterface historyInterface) {
    super(OrderHistoryModel.itemCallback);
    this.historyInterface = historyInterface;
  }

  @NonNull
  @Override
  public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    CardItemOrderHistoryBinding cardItemOrderHistoryBinding = CardItemOrderHistoryBinding.inflate(layoutInflater, parent, false);
    cardItemOrderHistoryBinding.setHistoryInterface(historyInterface);
    return new HistoryViewHolder(cardItemOrderHistoryBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
    OrderHistoryModel history = getItem(position);
    holder.cardItemOrderHistoryBinding.setHistory(history);
  }

  class HistoryViewHolder extends RecyclerView.ViewHolder{

    CardItemOrderHistoryBinding cardItemOrderHistoryBinding;

    public HistoryViewHolder(CardItemOrderHistoryBinding binding) {
      super(binding.getRoot());
      this.cardItemOrderHistoryBinding = binding;
    }
  }

  public interface HistoryInterface {
    void onItemClick(OrderHistoryModel history);
  }
}
