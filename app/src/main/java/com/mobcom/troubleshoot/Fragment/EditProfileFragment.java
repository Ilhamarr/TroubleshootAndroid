package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobcom.troubleshoot.Helper;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentEditProfileBinding;
import com.mobcom.troubleshoot.databinding.FragmentOrderBinding;
import com.mobcom.troubleshoot.models.LaptopModel;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String TAG = "EditProfileFragment";
    private SessionManager sessionManager;
    private NavController navController;
    private FragmentEditProfileBinding fragmentEditProfileBinding;
    private String account_id, firstname, lastname, email, phone, fullname, alamat;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
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

        // setup helper
        Helper helper = new Helper();

        // setup sessionmanager
        sessionManager = new SessionManager(getActivity());
        account_id = sessionManager.getUserDetail().get(SessionManager.ACCOUNT_ID);
        firstname = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
        lastname = sessionManager.getUserDetail().get(SessionManager.LAST_NAME);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
        phone = sessionManager.getUserDetail().get(SessionManager.NOMOR_HP);
        alamat = sessionManager.getUserDetail().get(SessionManager.ALAMAT);

        // form contact
        fullname = firstname + " " + lastname;
        fragmentEditProfileBinding.EdtNama.setText(fullname);
        fragmentEditProfileBinding.EdtEmail.setText(email);
        fragmentEditProfileBinding.EdtNomorTelepon.setText(phone);
        fragmentEditProfileBinding.EdtAlamat.setText(alamat);

        // back button
        fragmentEditProfileBinding.backButton.setOnClickListener(v -> navController.popBackStack());

        // tombol lanjut (simpan perubahan)
        fragmentEditProfileBinding.SimpanPerubahan.setOnClickListener(v -> {

            if (!validateNama() | !validateEmail() | !validatePhone() | !validateAlamat()) {
                return;
            }

            //get all data in this fragment
            String nama = fragmentEditProfileBinding.EdtNama.getText().toString();
            String email = fragmentEditProfileBinding.EdtEmail.getText().toString();
            String phone = fragmentEditProfileBinding.EdtNomorTelepon.getText().toString();
            String alamat = fragmentEditProfileBinding.EdtAlamat.getText().toString();
            //end get all data


            //pass all data to next fragment
            //OrderFragmentDirections.ActionOrderFragmentToOrderConfirmationFragment action = OrderFragmentDirections.actionOrderFragmentToOrderConfirmationFragment();
            //action.setNama(nama);
            //action.setEmail(email);
            //action.setPhone(phone);
            //action.setAlamat(alamat);

            //end pass
            //navController.navigate(action);
        });
    }


    private boolean validateNama() {
        String val = fragmentEditProfileBinding.EdtNama.getText().toString();
        String anyletter = "(^[a-zA-Z\\s]+$)";
        if (val.isEmpty()) {
            fragmentEditProfileBinding.EdtNama.setError("Form tidak boleh kosong");
            return false;
        } else if (!val.matches(anyletter)) {
            fragmentEditProfileBinding.EdtNama.setError("Hanya huruf yang diperbolehkan");
            return false;
        } else {
            fragmentEditProfileBinding.EdtNama.setError(null);
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