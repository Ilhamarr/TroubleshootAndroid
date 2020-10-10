package com.mobcom.troubleshoot.API;

import com.mobcom.troubleshoot.models.Login.Login;
import com.mobcom.troubleshoot.models.Register.Register;
import com.mobcom.troubleshoot.models.ResponseServiceModel;

import com.mobcom.troubleshoot.models.ResponseLaptopModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
  @GET("kerusakan")
  Call<ResponseServiceModel> ambillistLayanan();

  @GET("laptop")
  Call<ResponseLaptopModel> ambilListLaptop();

  @FormUrlEncoded
  @POST("login")
  Call<Login> loginResponse(
          @Field("email") String email,
          @Field("password") String password
  );

  @FormUrlEncoded
  @POST("account")
  Call<Register> registerResponse(
          @Field("first_name") String firstName,
          @Field("last_name") String lastName,
          @Field("email") String email,
          @Field("password") String password
  );

}
