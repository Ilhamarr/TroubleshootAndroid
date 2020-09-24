package com.mobcom.troubleshoot.API;

import com.mobcom.troubleshoot.Model.ResponseKerusakanModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRequestData {
  @GET("kerusakan")
  Call<ResponseKerusakanModel> ambilDataKerusakan();
}
