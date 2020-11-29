package com.mobcom.troubleshoot.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.Activity.ImagePickerActivity;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentEditProfileBinding;
import com.mobcom.troubleshoot.models.Login.LoginData;
import com.mobcom.troubleshoot.models.ResponseEditProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
  private static final String TAG = "EditProfileFragment";
  private SessionManager sessionManager;
  private NavController navController;
  private FragmentEditProfileBinding fragmentEditProfileBinding;
  private String account_id, firstname, lastname, email, phone, fullname, alamat, picture, provider, imgDir;
  private File fileImage;
  private APIRequestData ardData;
  private static final int REQUEST_IMAGE = 100;

  public EditProfileFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentEditProfileBinding = FragmentEditProfileBinding.inflate(inflater, container, false);
    return fragmentEditProfileBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // setup navcontroller
    navController = Navigation.findNavController(view);

    // setup sessionmanager
    sessionManager = new SessionManager(getActivity());
    account_id = sessionManager.getUserDetail().get(SessionManager.ACCOUNT_ID);
    firstname = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    lastname = sessionManager.getUserDetail().get(SessionManager.LAST_NAME);
    email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
    phone = sessionManager.getUserDetail().get(SessionManager.NOMOR_HP);
    alamat = sessionManager.getUserDetail().get(SessionManager.ALAMAT);
    picture = sessionManager.getUserDetail().get(SessionManager.PICTURE);
    provider = sessionManager.getUserDetail().get(SessionManager.PROVIDER);

    if (provider.equals("google")) {
      fragmentEditProfileBinding.EdtEmail.setEnabled(false);
    }

    if (picture != null) {
      try {
        Glide.with(getContext()).load(picture).into(fragmentEditProfileBinding.edtFotoProfile);
      } catch (NullPointerException e) {
        Toast.makeText(getContext(), "image not found", Toast.LENGTH_SHORT).show();
      }
    }

    // form contact
    fullname = firstname + " " + lastname;
    fragmentEditProfileBinding.EdtFirstName.setText(firstname);
    fragmentEditProfileBinding.EdtLastName.setText(lastname);
    fragmentEditProfileBinding.EdtEmail.setText(email);
    fragmentEditProfileBinding.EdtNomorTelepon.setText(phone);
    fragmentEditProfileBinding.EdtAlamat.setText(alamat);

    // back button
    fragmentEditProfileBinding.backButton.setOnClickListener(v -> navController.popBackStack());

    // tombol lanjut (simpan perubahan)
    fragmentEditProfileBinding.SimpanPerubahan.setOnClickListener(v -> {

      if (!validateFirstName() | !validateLastName() | !validateEmail() | !validatePhone() | !validateAlamat()) {
        return;
      }

      if (fileImage == null) {
        editProfile();
      } else {
        editProfileWithPicture();
      }

    });

    // image picker
    fragmentEditProfileBinding.ubahgambar.setOnClickListener(v -> openImagePicker());
  }

  private void openImagePicker() {
    Dexter.withContext(getContext())
            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(new MultiplePermissionsListener() {
              @Override
              public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                  showImagePickerOptions();
                }

                if (report.isAnyPermissionPermanentlyDenied()) {
                  showSettingsDialog();
                }
              }

              @Override
              public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
              }
            }).check();
  }

  private void loadProfile(String url) {
    //Log.d(TAG, "Image cache path: " + url);

    Glide.with(getContext()).load(url).into(fragmentEditProfileBinding.edtFotoProfile);
  }

  private void showImagePickerOptions() {
    ImagePickerActivity.showImagePickerOptions(getContext(), new ImagePickerActivity.PickerOptionListener() {
      @Override
      public void onTakeCameraSelected() {
        launchCameraIntent();
      }

      @Override
      public void onChooseGallerySelected() {
        launchGalleryIntent();
      }
    });
  }

  private void launchCameraIntent() {
    Intent intent = new Intent(getContext(), ImagePickerActivity.class);
    intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

    // setting aspect ratio
    intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
    intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
    intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

    // setting maximum bitmap width and height
    intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
    intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
    intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

    startActivityForResult(intent, REQUEST_IMAGE);
  }

  private void launchGalleryIntent() {
    Intent intent = new Intent(getContext(), ImagePickerActivity.class);
    intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

    // setting aspect ratio
    intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
    intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
    intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
    startActivityForResult(intent, REQUEST_IMAGE);
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_IMAGE) {
      if (resultCode == Activity.RESULT_OK) {
        Uri uri = data.getParcelableExtra("path");
        imgDir = uri.toString();
        String filename = imgDir.substring(imgDir.lastIndexOf("/") + 1);
        //Log.d(TAG, "onActivityResult: "+imgDir);

        try {
          // You can update this bitmap to your server
          Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
          //fileImage = bitmapToFile(bitmap, filename);
          //Log.d(TAG, "onActivityResult: " + fileImage.toURI());
          bitmapToFile(bitmap, filename);
          Log.d(TAG, "onActivityResult: " + fileImage);

          // loading profile image from local cache
          loadProfile(uri.toString());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private void showSettingsDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setTitle("Grant Permissions");
    builder.setMessage("This app needs permission to use this feature.");
    builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
      dialog.cancel();
      openSettings();
    });
    builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
    builder.show();

  }

  private void openSettings() {
    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
    intent.setData(uri);
    startActivityForResult(intent, 101);
  }

  public void bitmapToFile(Bitmap bitmap, String fileNameToSave) throws IOException {
    //create a file to write bitmap data
    fileImage = new File(getContext().getCacheDir(), fileNameToSave);
    fileImage.createNewFile();

    //Convert bitmap to byte array
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
    byte[] bitmapdata = bos.toByteArray();

    //write the bytes in file
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(fileImage);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      fos.write(bitmapdata);
      fos.flush();
      fos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void editProfile() {
    //get all data in this fragment
    String firstName = fragmentEditProfileBinding.EdtFirstName.getText().toString();
    String lastName = fragmentEditProfileBinding.EdtLastName.getText().toString();
    String email = fragmentEditProfileBinding.EdtEmail.getText().toString();
    String phone = fragmentEditProfileBinding.EdtNomorTelepon.getText().toString();
    String alamat = fragmentEditProfileBinding.EdtAlamat.getText().toString();
    //end get all data

    //pass all data
    RequestBody accountId_field = RequestBody.create(MediaType.parse("multipart/form-data"), account_id);
    RequestBody first_name_field = RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
    RequestBody last_name_field = RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
    RequestBody email_field = RequestBody.create(MediaType.parse("multipart/form-data"), email);
    RequestBody phone_field = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
    RequestBody address_field = RequestBody.create(MediaType.parse("multipart/form-data"), alamat);

    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseEditProfile> editProfileCall = ardData.editProfile(accountId_field, first_name_field, last_name_field, email_field, phone_field, address_field);
    editProfileCall.enqueue(new Callback<ResponseEditProfile>() {
      @Override
      public void onResponse(Call<ResponseEditProfile> call, Response<ResponseEditProfile> response) {
        if (response.body() != null && response.isSuccessful()) {
          LoginData loginData = response.body().getLoginData();
          sessionManager.createLoginSession(loginData);
          navController.popBackStack();
        }
        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onFailure(Call<ResponseEditProfile> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
      }
    });
  }

  private void editProfileWithPicture() {

    File file = new File(imgDir);
    //get all data in this fragment
    String firstName = fragmentEditProfileBinding.EdtFirstName.getText().toString();
    String lastName = fragmentEditProfileBinding.EdtLastName.getText().toString();
    String email = fragmentEditProfileBinding.EdtEmail.getText().toString();
    String phone = fragmentEditProfileBinding.EdtNomorTelepon.getText().toString();
    String alamat = fragmentEditProfileBinding.EdtAlamat.getText().toString();
    //end get all data

    //pass all data
    RequestBody accountId_field = RequestBody.create(MediaType.parse("multipart/form-data"), account_id);
    RequestBody first_name_field = RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
    RequestBody last_name_field = RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
    RequestBody email_field = RequestBody.create(MediaType.parse("multipart/form-data"), email);
    RequestBody phone_field = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
    RequestBody address_field = RequestBody.create(MediaType.parse("multipart/form-data"), alamat);

    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
    MultipartBody.Part body = MultipartBody.Part.createFormData("picture", fileImage.getName(), requestFile);

    ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseEditProfile> editProfileCall = ardData.editProfileWithPicture(accountId_field, first_name_field, last_name_field, email_field, phone_field, address_field, body);
    editProfileCall.enqueue(new Callback<ResponseEditProfile>() {
      @Override
      public void onResponse(Call<ResponseEditProfile> call, Response<ResponseEditProfile> response) {
        if (response.body() != null && response.isSuccessful()) {
          //Log.d(TAG, "onResponse: "+response.body().getMessage());
          LoginData loginData = response.body().getLoginData();
          sessionManager.createLoginSession(loginData);
          navController.popBackStack();
          Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
        } else {
          //Log.d(TAG, "onResponse: "+response.body().getMessage());
          Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ResponseEditProfile> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
      }
    });
  }

  private boolean validateFirstName() {
    String val = fragmentEditProfileBinding.EdtFirstName.getText().toString();
    String anyletter = "(^[a-zA-Z\\s]+$)";
    if (val.isEmpty()) {
      fragmentEditProfileBinding.EdtFirstName.setError("Form tidak boleh kosong");
      return false;
    } else if (!val.matches(anyletter)) {
      fragmentEditProfileBinding.EdtFirstName.setError("Hanya huruf yang diperbolehkan");
      return false;
    } else {
      fragmentEditProfileBinding.EdtFirstName.setError(null);
      return true;
    }
  }

  private boolean validateLastName() {
    String val = fragmentEditProfileBinding.EdtLastName.getText().toString();
    String anyletter = "(^[a-zA-Z\\s]+$)";
    if (val.isEmpty()) {
      fragmentEditProfileBinding.EdtLastName.setError("Form tidak boleh kosong");
      return false;
    } else if (!val.matches(anyletter)) {
      fragmentEditProfileBinding.EdtLastName.setError("Hanya huruf yang diperbolehkan");
      return false;
    } else {
      fragmentEditProfileBinding.EdtLastName.setError(null);
      return true;
    }
  }

  private boolean validateEmail() {

    String val = fragmentEditProfileBinding.EdtEmail.getText().toString();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    if (val.isEmpty()) {
      fragmentEditProfileBinding.EdtEmail.setError("Form tidak boleh kosong");
      return false;
    } else if (!val.matches(emailPattern)) {
      fragmentEditProfileBinding.EdtEmail.setError("Alamat email tidak valid");
      return false;
    } else {
      fragmentEditProfileBinding.EdtEmail.setError(null);
      return true;
    }
  }

  private boolean validatePhone() {
    String val = fragmentEditProfileBinding.EdtNomorTelepon.getText().toString();
    String phoneformat = "^[0-9]{12,13}$";
    if (val.isEmpty()) {
      fragmentEditProfileBinding.EdtNomorTelepon.setError("Form tidak boleh kosong");
      return false;
    } else if (!val.matches(phoneformat)) {
      fragmentEditProfileBinding.EdtNomorTelepon.setError("Nomor telepon tidak valid");
      return false;
    } else {
      fragmentEditProfileBinding.EdtNomorTelepon.setError(null);
      return true;
    }
  }

  private boolean validateAlamat() {
    String val = fragmentEditProfileBinding.EdtAlamat.getText().toString();
    if (val.isEmpty()) {
      fragmentEditProfileBinding.EdtAlamat.setError("Form tidak boleh kosong");
      return false;
    }
    return true;
  }

}