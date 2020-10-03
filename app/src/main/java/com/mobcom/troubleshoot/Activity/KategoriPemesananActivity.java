package com.mobcom.troubleshoot.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.Model.KerusakanModel;
import com.mobcom.troubleshoot.Model.LaptopModel;
import com.mobcom.troubleshoot.Model.ResponseKerusakanModel;
import com.mobcom.troubleshoot.Model.ResponseLaptopModel;
import com.mobcom.troubleshoot.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KategoriPemesananActivity extends AppCompatActivity {
  APIRequestData ardData;
  private Spinner spinnerKerusakan;
  private Spinner spinnerLaptop;
  List<KerusakanModel> listKerusakan = new ArrayList<>();
  List<LaptopModel> listLaptop = new ArrayList<>();
  Context mContext;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_kategori_pemesanan);

    ButterKnife.bind(this);
    mContext = this;
    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    spinnerKerusakan = (Spinner) findViewById(R.id.spKategoriKerusakan);
    spinnerLaptop = (Spinner) findViewById(R.id.spMerkLaptop);

    initSpinnerKerusakan();
    spinnerKerusakan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        KerusakanModel selectedKerusakan = (KerusakanModel) spinnerKerusakan.getSelectedItem();
        int kerusakan_id = selectedKerusakan.getKerusakan_id();
        String kerusakan_name = selectedKerusakan.getNama_kerusakan();
        Toast.makeText(mContext, "Kamu memilih " + kerusakan_name + " dengan id " + kerusakan_id, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    initSpinnerLaptop();

  }

  private void initSpinnerLaptop() {
    Call<ResponseLaptopModel> callLaptop = ardData.ambilListLaptop();
    callLaptop.enqueue(new Callback<ResponseLaptopModel>() {
      @Override
      public void onResponse(Call<ResponseLaptopModel> call, Response<ResponseLaptopModel> response) {
        if (response.isSuccessful()) {
          String status = response.body().getStatus();
          listLaptop = response.body().getListLaptop();
          ArrayAdapter<LaptopModel> adapterLaptop = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, listLaptop);
          adapterLaptop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spinnerLaptop.setAdapter(adapterLaptop);
        } else {
          Toast.makeText(mContext, "Gagal mengambil list kerusakan", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ResponseLaptopModel> call, Throwable t) {
        Toast.makeText(KategoriPemesananActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initSpinnerKerusakan() {
    Call<ResponseKerusakanModel> callKerusakan = ardData.ambillistKerusakan();
    callKerusakan.enqueue(new Callback<ResponseKerusakanModel>() {
      @Override
      public void onResponse(Call<ResponseKerusakanModel> call, Response<ResponseKerusakanModel> response) {
        if (response.isSuccessful()) {
          String status = response.body().getStatus();
          listKerusakan = response.body().getListKerusakan();
          ArrayAdapter<KerusakanModel> adapterKerusakan = new ArrayAdapter<KerusakanModel>(mContext, android.R.layout.simple_spinner_item, listKerusakan);
          adapterKerusakan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          spinnerKerusakan.setAdapter(adapterKerusakan);
        } else {
          Toast.makeText(mContext, "Gagal mengambil list kerusakan", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ResponseKerusakanModel> call, Throwable t) {
        Toast.makeText(KategoriPemesananActivity.this, "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
      }
    });
  }
}