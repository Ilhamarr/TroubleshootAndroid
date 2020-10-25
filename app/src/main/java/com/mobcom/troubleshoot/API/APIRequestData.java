package com.mobcom.troubleshoot.API;

import com.mobcom.troubleshoot.models.Login.Login;
import com.mobcom.troubleshoot.models.ResponseItemOrderDetail;
import com.mobcom.troubleshoot.models.ResponseKonfirmasiBayar;
import com.mobcom.troubleshoot.models.ResponseOrderHistory;
import com.mobcom.troubleshoot.models.Register.Register;
import com.mobcom.troubleshoot.models.ResponseHeaderOrder;
import com.mobcom.troubleshoot.models.ResponseOrder;
import com.mobcom.troubleshoot.models.ResponseServiceModel;

import com.mobcom.troubleshoot.models.ResponseLaptopModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

  @FormUrlEncoded
  @POST("headerorder")
  Call<ResponseHeaderOrder> HeaderOrderResponse(
          @Field("account_id") String account_id,
          @Field("nama") String nama,
          @Field("email") String email,
          @Field("merk_laptop") String merk_laptop,
          @Field("keterangan") String keterangan,
          @Field("nomor_hp") String nomor_hp,
          @Field("tanggal_pengambilan") String tanggal_pengambilan,
          @Field("jam_pengambilan") String jam_pengambilan,
          @Field("tempat_bertemu") String tempat_bertemu,
          @Field("tipe_laptop") String tipe_laptop,
          @Field("biaya_total") String biaya_total,
          @Field("tracking_key") String tracking_key
  );

  @FormUrlEncoded
  @POST("order")
  Call<ResponseOrder> OrderResponse(
          @Field("account_id") String account_id,
          @Field("tracking_key") String tracking_key,
          @Field("kerusakan_id") String kerusakan_id,
          @Field("harga") String harga,
          @Field("jumlah") String jumlah,
          @Field("total_harga") String total_harga
  );

  @FormUrlEncoded
  @POST("orderhistory")
  Call<ResponseOrderHistory> getListOrderHistory(
          @Field("account_id") String accountId
  );

  @FormUrlEncoded
  @POST("orderhistory")
  Call<ResponseItemOrderDetail> getListOrderLayanan(
          @Field("tracking_key") String tracking_key
  );

  @Multipart
  @POST("konfirmasibayar")
  Call<ResponseKonfirmasiBayar> konfirmasiBayar(
          @Part("tracking_key") RequestBody tracking_key,
          @Part MultipartBody.Part image_order
          );

}
