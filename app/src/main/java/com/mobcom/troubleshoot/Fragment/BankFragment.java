package com.mobcom.troubleshoot.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.databinding.FragmentBankBinding;
import com.mobcom.troubleshoot.models.ResponseKonfirmasiBayar;
import com.mobcom.troubleshoot.viewmodels.HistoryViewModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class BankFragment extends Fragment {

  private static final String TAG = "BankFragment";
  private FragmentBankBinding fragmentBankBinding;
  private HistoryViewModel historyViewModel;
  private NavController navController;
  private String trackingKey, imgDir;
  private APIRequestData ardData;

  public BankFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentBankBinding = FragmentBankBinding.inflate(inflater, container, false);
    return fragmentBankBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup view model
    historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
    fragmentBankBinding.setHistoryViewModel(historyViewModel);
    trackingKey = historyViewModel.getHistory().getValue().getTrackingKey();

    // tombol back
    fragmentBankBinding.backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        navController.popBackStack();
      }
    });

    // tombol uploadbukti
    fragmentBankBinding.btnUploadbukti.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 2);
      }
    });

    // button konfirmasi
    fragmentBankBinding.btnKonfirmasibayar.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        konfirmasiBayar();
      }
    });
  }

  // akses izin ambil gambar dari storage
  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 2 && resultCode == RESULT_OK && null != data){
      Uri selectedImage = data.getData();
      String[] filePathColumn = { MediaStore.Images.Media.DATA };
      Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
      cursor.moveToFirst();
      int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
      String picturePath = cursor.getString(columnIndex);
      imgDir = picturePath;
      cursor.close();
    }
  }

  private void konfirmasiBayar() {
    if(imgDir==null){
      Toast.makeText(getContext(), "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
    }
    else {
      File file = new File(imgDir);
      RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
      MultipartBody.Part body = MultipartBody.Part.createFormData("image_order", file.getName(), requestFile);
      RequestBody tracking_key = RequestBody.create(MediaType.parse("multipart/form-data"), trackingKey);


      ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
      Call<ResponseKonfirmasiBayar> konfirmbayar = ardData.konfirmasiBayar(tracking_key, body);
      konfirmbayar.enqueue(new Callback<ResponseKonfirmasiBayar>() {
        @Override
        public void onResponse(Call<ResponseKonfirmasiBayar> call, Response<ResponseKonfirmasiBayar> response) {
          if (response.body() != null && response.isSuccessful()){
            //Log.d(TAG, "onResponse: "+response.body().getMessage());
            navController.navigate(R.id.action_bankFragment_to_nonTunaiSuccessFragment);
            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
          }
          else{
            //Log.d(TAG, "onResponse: "+response.body().getMessage());
            Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
          }
        }

        @Override
        public void onFailure(Call<ResponseKonfirmasiBayar> call, Throwable t) {
          Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
        }
      });
    }
  }
}