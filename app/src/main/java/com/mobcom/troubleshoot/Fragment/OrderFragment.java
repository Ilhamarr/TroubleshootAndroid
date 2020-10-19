package com.mobcom.troubleshoot.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentOrderBinding;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.LaptopModel;
import com.mobcom.troubleshoot.models.ResponseLaptopModel;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.date.YearPickerView;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
  private static final String TAG = "OrderFragment";
  private SessionManager sessionManager;
  private NavController navController;
  FragmentOrderBinding fragmentOrderBinding;
  ServiceViewModel serviceViewModel;
  private int cartQuantity = 0;
  private String account_id, firstname, lastname, email, phone, fullname, laptop_Id, laptop_Merk;
  List<LaptopModel> listLaptop = new ArrayList<>();

  public OrderFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false);
    return fragmentOrderBinding.getRoot();
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    sessionManager = new SessionManager(getActivity());
    navController = Navigation.findNavController(view);
    account_id = sessionManager.getUserDetail().get(SessionManager.ACCOUNT_ID);
    firstname = sessionManager.getUserDetail().get(SessionManager.FIRST_NAME);
    lastname = sessionManager.getUserDetail().get(SessionManager.LAST_NAME);
    email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
    phone = sessionManager.getUserDetail().get(SessionManager.NOMOR_HP);
    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

    //get banyaknya item di cart dan total harga
    serviceViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
      @Override
      public void onChanged(List<CartItem> cartItems) {
        int quantity = 0;
        for (CartItem cartItem : cartItems) {
          quantity += cartItem.getQuantity();
        }
        cartQuantity = quantity;
        fragmentOrderBinding.TxtTotalProdukSeluruh.setText(String.valueOf(cartQuantity));
      }
    });
    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
      @Override
      public void onChanged(Integer integer) {
        fragmentOrderBinding.orderTotalTextView.setText(integer.toString());
      }
    });
    //end

    //spinner merk laptop
    initSpinnerLaptop();
    fragmentOrderBinding.SpinnerMerkLaptop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LaptopModel selectedLaptop = (LaptopModel) fragmentOrderBinding.SpinnerMerkLaptop.getSelectedItem();
        String laptop_id = String.valueOf(selectedLaptop.getLaptop_id());
        String laptop_merk = selectedLaptop.getMerk();
        String laptop_tipe = selectedLaptop.getTipe();
        laptop_Id = laptop_id;
        laptop_Merk = laptop_merk;
        //Toast.makeText(getContext(), laptop_id + ". " + laptop_merk + " " + laptop_tipe, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    //end spinner merk laptop

    //DatetimePicker
    fragmentOrderBinding.EdtTanggal.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openDatePicker();
      }
    });
    fragmentOrderBinding.EdtJam.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openTimePicker();
      }
    });
    //end DatetimePicker

    //radio button tempat bertemu
    fragmentOrderBinding.rgTempatBertemu.clearCheck();
    fragmentOrderBinding.rgTempatBertemu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == fragmentOrderBinding.btnAntarJemput.getId()) {
          fragmentOrderBinding.alamatTempatBertemu.getText().clear();
          fragmentOrderBinding.alamatTempatBertemu.setHint("Masukan alamat anda");
          fragmentOrderBinding.alamatTempatBertemu.setEnabled(true);
        } else {
          fragmentOrderBinding.alamatTempatBertemu.setText("Kampus A UNJ, Jl. Rawamangun Muka, Gedung Dewi Sartika Lt.5");
          fragmentOrderBinding.alamatTempatBertemu.setEnabled(false);
        }
      }
    });
    //end radio button tempat bertemu

    //form contact
    fullname = firstname + " " + lastname;
    fragmentOrderBinding.EdtNama.setText(fullname);
    fragmentOrderBinding.EdtEmail.setText(email);
    fragmentOrderBinding.EdtNomorTelepon.setText(phone);
    //end form contact

    //tombol lanjut
    fragmentOrderBinding.LanjutPembayaran.setOnClickListener(v -> {

      if(!validateSeriLaptop() | !validateDetail() | !validateTanggal() | !validateJam() | !validateTempat() | !validateNama() | !validateEmail() | !validatePhone()){
        return;
      }

      //get all data in this fragment
      String seriLaptop = fragmentOrderBinding.EdtSeriLaptop.getText().toString();
      String detailPermasalahan = fragmentOrderBinding.EdtDetailPemesanan.getText().toString();
      String tanggal = fragmentOrderBinding.EdtTanggal.getText().toString();
      String jam = fragmentOrderBinding.EdtJam.getText().toString();
      String tempat = fragmentOrderBinding.alamatTempatBertemu.getText().toString();
      String nama = fragmentOrderBinding.EdtNama.getText().toString();
      String email = fragmentOrderBinding.EdtEmail.getText().toString();
      String phone = fragmentOrderBinding.EdtNomorTelepon.getText().toString();
      //end get all data



      //pass all data to next fragment
      OrderFragmentDirections.ActionOrderFragmentToOrderConfirmationFragment action = OrderFragmentDirections.actionOrderFragmentToOrderConfirmationFragment();
      action.setIdLaptop(laptop_Id);
      action.setMerkLaptop(laptop_Merk);
      action.setSeriLaptop(seriLaptop);
      action.setDetail(detailPermasalahan);
      action.setTanggal(tanggal);
      action.setJam(jam);
      action.setAlamat(tempat);
      action.setNama(nama);
      action.setEmail(email);
      action.setPhone(phone);

      //end pass
      navController.navigate(action);
    });
    //end tombol lanjut
  }

  private void initSpinnerLaptop() {
    APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
    Call<ResponseLaptopModel> callLaptop = ardData.ambilListLaptop();
    callLaptop.enqueue(new Callback<ResponseLaptopModel>() {
      @Override
      public void onResponse(Call<ResponseLaptopModel> call, Response<ResponseLaptopModel> response) {
        if (response.isSuccessful()) {
          String status = response.body().getStatus();
          listLaptop = response.body().getListLaptop();
          ArrayAdapter<LaptopModel> adapterLaptop = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listLaptop);
          adapterLaptop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          fragmentOrderBinding.SpinnerMerkLaptop.setAdapter(adapterLaptop);
        } else {
          Toast.makeText(getContext(), "Gagal mengambil list kerusakan", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ResponseLaptopModel> call, Throwable t) {
        Toast.makeText(getContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
      }
    });
  }

  private String convertTime(int input){
    if(input >= 10){
      return String.valueOf(input);
    } else {
      return "0" + String.valueOf(input);
    }
  }

  private void openTimePicker() {
    Calendar calendar = Calendar.getInstance();
    int HOUR = calendar.get(Calendar.HOUR_OF_DAY);
    int Minute = calendar.get(Calendar.MINUTE);

    TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        fragmentOrderBinding.EdtJam.setText(convertTime(hourOfDay) + ":" + convertTime(minute));
      }
    },HOUR,Minute,true);
    tpd.setMinTime(8,0,0);
    tpd.setMaxTime(15,0,0);
    tpd.show(getFragmentManager(),"");
  }

  private void openDatePicker() {
    Calendar calendar = Calendar.getInstance();
    int YEAR = calendar.get(Calendar.YEAR);
    int MONTH = calendar.get(Calendar.MONTH);
    int Day = calendar.get(Calendar.DAY_OF_MONTH);
    int Hour = calendar.get(Calendar.HOUR_OF_DAY);

    DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"-"+(++monthOfYear)+"-"+year;
        fragmentOrderBinding.EdtTanggal.setText(date);
      }
    },YEAR,MONTH,Day);

    // restrict to weekdays only
    ArrayList<Calendar> weekdays = new ArrayList<Calendar>();
    for (int i=0; i < 365; i++) {
      if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY ) {
        Calendar d = (Calendar) calendar.clone();
        weekdays.add(d);
      }
      calendar.add(Calendar.DATE, 1);

    }

    Calendar[] weekdayDays = weekdays.toArray(new Calendar[weekdays.size()]);
    dpd.setSelectableDays(weekdayDays);
    dpd.show(getFragmentManager(), "");
  }

  private Boolean validateSeriLaptop (){
    String val = fragmentOrderBinding.EdtSeriLaptop.getText().toString();
    if (val.isEmpty()) {
      fragmentOrderBinding.EdtSeriLaptop.setError("Field cannot be empty");
      return false;
    }
    return true;
  }
  private Boolean validateDetail (){
    String val = fragmentOrderBinding.EdtDetailPemesanan.getText().toString();
    if(val.isEmpty()){
      fragmentOrderBinding.EdtDetailPemesanan.setError("Field cannot be empty");
      return false;
    }
    return true;
  }
  private Boolean validateTanggal(){
    String val = fragmentOrderBinding.EdtTanggal.getText().toString();
    if(val.isEmpty()){
      fragmentOrderBinding.EdtTanggal.setError("Field cannot be empty");
      return false;
    }
    return true;
  }
  private Boolean validateJam (){
    String val = fragmentOrderBinding.EdtJam.getText().toString();
    if(val.isEmpty()){
      fragmentOrderBinding.EdtJam.setError("Field cannot be empty");
      return false;
    }
    return true;
  }
  private Boolean validateTempat (){
    String val = fragmentOrderBinding.alamatTempatBertemu.getText().toString();
    if(val.isEmpty()){
      fragmentOrderBinding.alamatTempatBertemu.setError("Field cannot be empty");
      return false;
    }
    return true;
  }
  private Boolean validateNama (){
    String val = fragmentOrderBinding.EdtNama.getText().toString();
    if(val.isEmpty()){
      fragmentOrderBinding.EdtNama.setError("Field cannot be empty");
      return false;
    }
    return true;
  }
  private Boolean validateEmail (){
    String val = fragmentOrderBinding.EdtEmail.getText().toString();
    if(val.isEmpty()){
      fragmentOrderBinding.EdtEmail.setError("Field cannot be empty");
      return false;
    }
    return true;
  }
  private Boolean validatePhone (){
    String val = fragmentOrderBinding.EdtNomorTelepon.getText().toString();
    if(val.isEmpty()){
      fragmentOrderBinding.EdtNomorTelepon.setError("Field cannot be empty");
      return false;
    }
    return true;
  }

}