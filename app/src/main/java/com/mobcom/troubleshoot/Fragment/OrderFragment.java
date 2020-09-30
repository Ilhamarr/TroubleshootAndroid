package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.Adapter.AdapterDataLayanan;
import com.mobcom.troubleshoot.Model.KerusakanModel;
import com.mobcom.troubleshoot.Model.ResponseKerusakanModel;
import com.mobcom.troubleshoot.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    private RecyclerView rvDataLayanan;
    private RecyclerView.Adapter adDataLayanan;
    private RecyclerView.LayoutManager lmDatalayanan;
    private List<KerusakanModel> listData = new ArrayList<>();
    private SwipeRefreshLayout srlData;
    private ProgressBar pbData;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_order, container, false);

        rvDataLayanan = (RecyclerView) rootView.findViewById(R.id.rv_data_layanan);
        //srlData = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_data_layanan);
        pbData = (ProgressBar) rootView.findViewById(R.id.pb_data_layanan);

        lmDatalayanan = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvDataLayanan.setLayoutManager(lmDatalayanan);
        retrieveDataLayanan();

        return rootView;
    }

    public void retrieveDataLayanan(){
        pbData.setVisibility(View.VISIBLE);

        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseKerusakanModel> tampilDataLayanan = ardData.ambillistKerusakan();

        tampilDataLayanan.enqueue(new Callback<ResponseKerusakanModel>() {
            @Override
            public void onResponse(Call<ResponseKerusakanModel> call, Response<ResponseKerusakanModel> response) {
                String status = response.body().getStatus();
                listData = response.body().getListKerusakan();

                adDataLayanan = new AdapterDataLayanan(getContext(),listData);
                rvDataLayanan.setAdapter(adDataLayanan);
                adDataLayanan.notifyDataSetChanged();
                pbData.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseKerusakanModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
                pbData.setVisibility(View.INVISIBLE);
            }
        });
    }
}