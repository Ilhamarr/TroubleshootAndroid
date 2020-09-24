package com.mobcom.troubleshoot.API;

import com.mobcom.troubleshoot.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {
  private static final String baseURL = Config.BASE_URL;
  private static Retrofit retro;

  public static Retrofit konekRetrofit() {
    if (retro == null) {
      retro = new Retrofit.Builder()
              .baseUrl(baseURL)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
    }
    return retro;
  }
}
