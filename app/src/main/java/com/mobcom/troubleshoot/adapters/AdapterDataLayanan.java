package com.mobcom.troubleshoot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobcom.troubleshoot.models.ServiceModel;
import com.mobcom.troubleshoot.R;

import java.util.List;

public class AdapterDataLayanan extends RecyclerView.Adapter<AdapterDataLayanan.HolderData> {
  private Context ctx;
  // declar list data layanan
  private List<ServiceModel> listData;
  // id data layanan
  private int idLayanan;

  public AdapterDataLayanan(Context ctx, List<ServiceModel> listData) {
    this.ctx = ctx;
    this.listData = listData;
  }


  @NonNull
  @Override
  public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layanan, parent, false);
    HolderData holder = new HolderData(layout);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull HolderData holder, int position) {
    ServiceModel km = listData.get(position);
    holder.tvId.setText(String.valueOf(km.getKerusakan_id()));
    holder.tvNamaLayanan.setText(km.getNama_kerusakan());
    holder.tvDeskripsiLayanan.setText(km.getJenis());
    holder.tvHarga.setText(String.valueOf(km.getBiaya()));
  }

  @Override
  public int getItemCount() {
    return listData.size();
  }

  public class HolderData extends RecyclerView.ViewHolder {
    TextView tvId, tvNamaLayanan, tvDeskripsiLayanan, tvHarga;

    public HolderData(@NonNull View itemView) {
      super(itemView);

      tvId = itemView.findViewById(R.id.tv_IdLayanan);
      tvNamaLayanan = itemView.findViewById(R.id.tv_NamaLayanan);
      tvDeskripsiLayanan = itemView.findViewById(R.id.tv_deskripsiLayanan);
      tvHarga = itemView.findViewById(R.id.tv_HargaLayanan);
    }
  }
}
