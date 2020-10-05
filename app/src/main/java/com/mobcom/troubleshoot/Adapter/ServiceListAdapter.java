package com.mobcom.troubleshoot.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.troubleshoot.models.ServiceModel;
import com.mobcom.troubleshoot.databinding.CardItemLayananBinding;

public class ServiceListAdapter extends ListAdapter<ServiceModel, ServiceListAdapter.ServiceViewHolder> {

  public ServiceListAdapter() {
    super(ServiceModel.itemCallback);
  }

  @NonNull
  @Override
  public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    CardItemLayananBinding cardItemLayananBinding = CardItemLayananBinding.inflate(layoutInflater, parent, false);
    return new ServiceViewHolder(cardItemLayananBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
    ServiceModel service = getItem(position);
    holder.cardItemLayananBinding.setService(service);
  }

  class ServiceViewHolder extends RecyclerView.ViewHolder {

    CardItemLayananBinding cardItemLayananBinding;

    public ServiceViewHolder(CardItemLayananBinding binding) {
      super(binding.getRoot());
      this.cardItemLayananBinding = binding;
    }
  }

  public interface ServiceInterface {
    void addItem(ServiceModel service);
    void onItemClick(ServiceModel service);
  }
}
