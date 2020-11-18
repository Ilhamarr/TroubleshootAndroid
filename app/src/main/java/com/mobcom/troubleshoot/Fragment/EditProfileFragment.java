package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.Helper;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentEditProfileBinding;
import com.mobcom.troubleshoot.databinding.FragmentOrderBinding;
import com.mobcom.troubleshoot.models.LaptopModel;
import com.mobcom.troubleshoot.models.Login.LoginData;
import com.mobcom.troubleshoot.models.ResponseEditProfile;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;

import java.io.File;
import java.util.ArrayList;
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
    private APIRequestData ardData;

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

        if (picture != null){
            try {
                Glide.with(getContext()).load(picture).into(fragmentEditProfileBinding.edtFotoProfile);
            } catch (NullPointerException e){
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

            //get all data in this fragment
            String firstName = fragmentEditProfileBinding.EdtFirstName.getText().toString();
            String lastName = fragmentEditProfileBinding.EdtLastName.getText().toString();
            String email = fragmentEditProfileBinding.EdtEmail.getText().toString();
            String phone = fragmentEditProfileBinding.EdtNomorTelepon.getText().toString();
            String alamat = fragmentEditProfileBinding.EdtAlamat.getText().toString();
            //end get all data

            //pass all data
//            File file = new File(imgDir);
//            if (imgDir==null){
//                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
//            } else{
//                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                MultipartBody.Part body = MultipartBody.Part.createFormData("picture", file.getName(), requestFile);
//            }

            RequestBody accountId_field  = RequestBody.create(MediaType.parse("multipart/form-data"), account_id);
            RequestBody first_name_field  = RequestBody.create(MediaType.parse("multipart/form-data"), firstName);
            RequestBody last_name_field  = RequestBody.create(MediaType.parse("multipart/form-data"), lastName);
            RequestBody email_field  = RequestBody.create(MediaType.parse("multipart/form-data"), email);
            RequestBody phone_field  = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
            RequestBody address_field  = RequestBody.create(MediaType.parse("multipart/form-data"), alamat);

            ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseEditProfile> editProfileCall = ardData.editProfile(accountId_field, first_name_field, last_name_field, email_field, phone_field, address_field);
            editProfileCall.enqueue(new Callback<ResponseEditProfile>() {
                @Override
                public void onResponse(Call<ResponseEditProfile> call, Response<ResponseEditProfile> response) {
                    if (response.body() != null && response.isSuccessful()){
                        //Log.d(TAG, "onResponse: "+response.body().getMessage());
                        LoginData loginData = response.body().getLoginData();
                        sessionManager.createLoginSession(loginData);
                        navController.popBackStack();
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //Log.d(TAG, "onResponse: "+response.body().getMessage());
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseEditProfile> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
            //end pass
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