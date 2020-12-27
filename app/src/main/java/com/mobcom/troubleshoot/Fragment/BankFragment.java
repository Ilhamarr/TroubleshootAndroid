package com.mobcom.troubleshoot.Fragment;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.mobcom.troubleshoot.SessionManager;
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
  private String trackingKey, imgDir, email;
  private APIRequestData ardData;
  private static final int REQUEST_CODE = 123;
  private SessionManager sessionManager;

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

    sessionManager = new SessionManager(getActivity());
    email = sessionManager.getUserDetail().get(SessionManager.EMAIL);

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup view model
    historyViewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
    fragmentBankBinding.setHistoryViewModel(historyViewModel);
    trackingKey = historyViewModel.getHistory().getValue().getTrackingKey();

    // button back
    fragmentBankBinding.backButton.setOnClickListener(v -> navController.popBackStack());

    // copy rekening
    fragmentBankBinding.txtRekeningcopyBNI.setOnClickListener(v -> {
      ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
      if (clipboard != null) {
        clipboard.setText("0870340387");
      }
      Toast.makeText(getContext(), "Nomor berhasil di copy", Toast.LENGTH_SHORT).show();
    });

    fragmentBankBinding.btnUploadbukti.setOnClickListener(v -> openGallery());

    // button konfirmasi
    fragmentBankBinding.btnKonfirmasibayar.setOnClickListener(v -> konfirmasiBayar());
  }

  private void openGallery() {
    if (hasPermissionInManifest(getActivity(), 1, Manifest.permission.READ_EXTERNAL_STORAGE)) {
      Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      startActivityForResult(galleryIntent, 2);
    }
  }

  private boolean hasPermissionInManifest(FragmentActivity activity, int i, String permissionName) {
    if (ContextCompat.checkSelfPermission(activity,
            permissionName)
            != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(activity,
              new String[]{permissionName},
              i);
    } else {
      return true;
    }
    return false;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == 1) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        openGallery();
    }
  }

  // akses izin ambil gambar dari storage
  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
      Uri selectedImage = data.getData();
      String[] filePathColumn = {MediaStore.Images.Media.DATA};
      Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
      cursor.moveToFirst();
      int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
      String picturePath = cursor.getString(columnIndex);
      imgDir = picturePath;
      cursor.close();

      Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
      fragmentBankBinding.viewImage.setImageBitmap(bitmap);
      fragmentBankBinding.btnUploadbukti.setVisibility(View.GONE);
    }
  }

  private void konfirmasiBayar() {
    if (imgDir == null) {
      Toast.makeText(getContext(), "Pilih gambar terlebih dahulu", Toast.LENGTH_SHORT).show();
    } else {
      File file = new File(imgDir);
      RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
      MultipartBody.Part body = MultipartBody.Part.createFormData("image_order", file.getName(), requestFile);
      RequestBody tracking_key = RequestBody.create(MediaType.parse("multipart/form-data"), trackingKey);
      RequestBody email_req = RequestBody.create(MediaType.parse("multipart/form-data"), email);

      ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
      Call<ResponseKonfirmasiBayar> konfirmbayar = ardData.konfirmasiBayar(tracking_key, email_req, body);
      konfirmbayar.enqueue(new Callback<ResponseKonfirmasiBayar>() {
        @Override
        public void onResponse(Call<ResponseKonfirmasiBayar> call, Response<ResponseKonfirmasiBayar> response) {
          if (response.body() != null && response.isSuccessful()) {
            navController.navigate(R.id.action_bankFragment_to_nonTunaiSuccessFragment);
          }
          Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<ResponseKonfirmasiBayar> call, Throwable t) {
          Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
        }
      });
    }
  }
}