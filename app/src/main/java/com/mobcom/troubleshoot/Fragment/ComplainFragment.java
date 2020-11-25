package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.Activity.RegisterActivity;
import com.mobcom.troubleshoot.Helper;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.adapters.CartListConfirmAdapter;
import com.mobcom.troubleshoot.databinding.FragmentComplainBinding;
import com.mobcom.troubleshoot.databinding.FragmentOrderConfirmationBinding;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.ResponseHeaderOrder;
import com.mobcom.troubleshoot.models.ResponseOrder;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComplainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private FragmentComplainBinding fragmentComplainBinding;
    private String account_id, laptop_Id, mereklaptop, tipeLaptop, seriLaptop, detail, tanggal, jam, tempat, nama, email, phone, totalHarga, tracking_key;
    private ServiceViewModel serviceViewModel;
    private int cartQuantity = 0;
    private NavController navController;
    private SessionManager sessionManager;
    private APIRequestData ardData;

    public ComplainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentComplainBinding = FragmentComplainBinding.inflate(inflater, container, false);
        return fragmentComplainBinding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ini nampilin item yang dipilih kan?
        // setup recyclerview
        CartListConfirmAdapter cartListConfirmAdaptertAdapter = new CartListConfirmAdapter();
        fragmentComplainBinding.rvItemOrderDetail.setAdapter(cartListConfirmAdaptertAdapter);

        // setup sessionmanager
        sessionManager = new SessionManager(getActivity());
        account_id = sessionManager.getUserDetail().get(SessionManager.ACCOUNT_ID);

        // setup navcontroller
        navController = Navigation.findNavController(view);

        // setup view model
        serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

        // setup helper
        Helper helper = new Helper();

        //gatau gus di gw error jd gw komenn ehe ini keknya yg ngambil data kek yg mau ditampilin ya?
        // get data from previous fragment
        /**if (getArguments() != null){
            ComplainFragmentArgs args = ComplainFragmentArgs.fromBundle(getArguments());
            laptop_Id = args.getIdLaptop();
            mereklaptop = args.getMerkLaptop();
            tipeLaptop = args.getSeriLaptop();
            nama = args.getNama();
            email = args.getEmail();
            phone = args.getPhone();
            seriLaptop = args.getSeriLaptop();
            detail = args.getDetail();
            tanggal = args.getTanggal();
            jam = args.getJam();
            tempat = args.getAlamat();
            tracking_key = args.getTracking_key();

        }**/

        // view data
        fragmentComplainBinding.TxtKode.setText(tracking_key);
        fragmentComplainBinding.TxtNamaLengkap.setText(nama);
        fragmentComplainBinding.TxtEmail.setText(email);
        fragmentComplainBinding.TxtNomorTelepon.setText(phone);
        //fragmentComplainBinding.TxtAlamat.setText(tempat);

        // button back
        fragmentComplainBinding.backButton.setOnClickListener(v -> navController.popBackStack());

        // DatePicker
        fragmentComplainBinding.EdtTanggal.setOnClickListener(v -> openDatePicker());

        // timePicker
        fragmentComplainBinding.EdtJam.setOnClickListener(v -> openTimePicker());

        // tombol lanjut (to complain)
        fragmentComplainBinding.btnComplain.setOnClickListener(v -> {

            if (!validateDetail() | !validateTanggal() | !validateJam()) {
                return;
            }

            //get all data in this fragment
            String detailPermasalahan = fragmentComplainBinding.EdtDetailPemesanan.getText().toString();
            String tanggal = fragmentComplainBinding.EdtTanggal.getText().toString();
            String jam = fragmentComplainBinding.EdtJam.getText().toString();
            //end get all data

        });


    }



    private void openDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR_OF_DAY);
        int Minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog tpd = TimePickerDialog.newInstance((view, hourOfDay, minute, second) -> fragmentComplainBinding.EdtJam.setText(convertTime(hourOfDay) + ":" + convertTime(minute)), HOUR, Minute, true);
        tpd.setMinTime(8, 0, 0);
        tpd.setMaxTime(15, 0, 0);
        tpd.show(getFragmentManager(), "");
    }

    private String convertTime(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

    private void openTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        int Hour = calendar.get(Calendar.HOUR_OF_DAY);

        DatePickerDialog dpd = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
            String date = dayOfMonth + "-" + (++monthOfYear) + "-" + year;
            fragmentComplainBinding.EdtTanggal.setText(date);
        }, YEAR, MONTH, Day);

        // restrict to weekdays only
        ArrayList<Calendar> weekdays = new ArrayList<Calendar>();
        for (int i = 0; i < 365; i++) {
            if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                Calendar d = (Calendar) calendar.clone();
                weekdays.add(d);
            }
            calendar.add(Calendar.DATE, 1);

        }

        Calendar[] weekdayDays = weekdays.toArray(new Calendar[weekdays.size()]);
        dpd.setSelectableDays(weekdayDays);
        dpd.show(getFragmentManager(), "");
    }

    private boolean validateDetail() {
        String val = fragmentComplainBinding.EdtDetailPemesanan.getText().toString();
        if (val.isEmpty()) {
            fragmentComplainBinding.EdtDetailPemesanan.setError("Form tidak boleh kosong");
            return false;
        }
        return true;
    }

    private boolean validateTanggal() {
        String val = fragmentComplainBinding.EdtTanggal.getText().toString();
        if (val.isEmpty()) {
            fragmentComplainBinding.EdtTanggal.setError("Form tidak boleh kosong");
            return false;
        }
        return true;
    }

    private boolean validateJam() {
        String val = fragmentComplainBinding.EdtJam.getText().toString();
        if (val.isEmpty()) {
            fragmentComplainBinding.EdtJam.setError("Form tidak boleh kosong");
            return false;
        }
        return true;
    }


}