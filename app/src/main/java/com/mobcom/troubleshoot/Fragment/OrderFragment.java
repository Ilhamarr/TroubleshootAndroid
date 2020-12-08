package com.mobcom.troubleshoot.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.essam.simpleplacepicker.MapActivity;
import com.essam.simpleplacepicker.utils.SimplePlacePicker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.mobcom.troubleshoot.API.APIRequestData;
import com.mobcom.troubleshoot.API.RetroServer;
import com.mobcom.troubleshoot.Config;
import com.mobcom.troubleshoot.Helper;
import com.mobcom.troubleshoot.R;
import com.mobcom.troubleshoot.SessionManager;
import com.mobcom.troubleshoot.databinding.FragmentOrderBinding;
import com.mobcom.troubleshoot.models.CartItem;
import com.mobcom.troubleshoot.models.LaptopModel;
import com.mobcom.troubleshoot.models.ResponseLaptopModel;
import com.mobcom.troubleshoot.viewmodels.ServiceViewModel;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class OrderFragment extends Fragment {
  private static final String TAG = "OrderFragment";
  private SessionManager sessionManager;
  private NavController navController;
  private FragmentOrderBinding fragmentOrderBinding;
  private ServiceViewModel serviceViewModel;
  private int cartQuantity = 0;
  private String account_id, firstname, lastname, email, phone, fullname, laptop_Id, laptop_Merk;
  private List<LaptopModel> listLaptop = new ArrayList<>();
  private int PLACE_PICKER_REQUEST = 1;
  private Double endlatitude, endlongtitude;
  private int HargaOngkir = 0;
  private Double Jarak = 0.0;

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

    // setup view model
    serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

    // get banyaknya item di cart
    serviceViewModel.getCart().observe(getViewLifecycleOwner(), cartItems -> {
      int quantity = 0;
      for (CartItem cartItem : cartItems) {
        quantity += cartItem.getQuantity();
      }
      cartQuantity = quantity;
      fragmentOrderBinding.TxtTotalProdukSeluruh.setText(String.valueOf(cartQuantity));
    });

    // get total harga di cart
    serviceViewModel.getTotalPrice().observe(getViewLifecycleOwner(), integer -> {
      fragmentOrderBinding.orderTotalTextView.setText(helper.formatRp(integer + HargaOngkir));
      Log.d(TAG, "onViewCreated: " + String.valueOf(integer + HargaOngkir));
    });

    // spinner merk laptop
    initSpinnerLaptop();
    fragmentOrderBinding.SpinnerMerkLaptop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        LaptopModel selectedLaptop = (LaptopModel) fragmentOrderBinding.SpinnerMerkLaptop.getSelectedItem();
        String laptop_id = String.valueOf(selectedLaptop.getLaptop_id());
        String laptop_merk = selectedLaptop.getMerk();
        laptop_Id = laptop_id;
        laptop_Merk = laptop_merk;
        //Toast.makeText(getContext(), laptop_id + ". " + laptop_merk + " " + laptop_tipe, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    // DatePicker
    fragmentOrderBinding.EdtTanggal.setOnClickListener(v -> openDatePicker());

    // timePicker
    fragmentOrderBinding.EdtJam.setOnClickListener(v -> openTimePicker());

    // radio button tempat bertemu
    fragmentOrderBinding.rgTempatBertemu.clearCheck();
    fragmentOrderBinding.rgTempatBertemu.setOnCheckedChangeListener((group, checkedId) -> {
      if (checkedId == fragmentOrderBinding.btnAntarJemput.getId()) {
//        fragmentOrderBinding.alamatTempatBertemu.getText().clear();
//        fragmentOrderBinding.alamatTempatBertemu.setHint("Masukan alamat anda");
        fragmentOrderBinding.locationButton.setEnabled(true);
        fragmentOrderBinding.alamatTempatBertemu.setEnabled(true);
        fragmentOrderBinding.alamatTempatBertemu.setText("");
        openPlacePicker();
      } else {
        fragmentOrderBinding.locationButton.setEnabled(false);
        fragmentOrderBinding.alamatTempatBertemu.setText("Kampus A UNJ, Jl. Rawamangun Muka, Gedung Dewi Sartika Lt.5");
        fragmentOrderBinding.alamatTempatBertemu.setEnabled(false);
      }
    });

    fragmentOrderBinding.locationButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        fragmentOrderBinding.alamatTempatBertemu.setEnabled(true);
        fragmentOrderBinding.alamatTempatBertemu.setText("");
        openPlacePicker();
      }
    });

    // form contact
    fullname = firstname + " " + lastname;
    fragmentOrderBinding.EdtNama.setText(fullname);
    fragmentOrderBinding.EdtEmail.setText(email);
    fragmentOrderBinding.EdtNomorTelepon.setText(phone);

    // back button
    fragmentOrderBinding.backButton.setOnClickListener(v -> navController.popBackStack());

    // tombol lanjut (to order confirmation)
    fragmentOrderBinding.LanjutPembayaran.setOnClickListener(v -> {

      if (!validateSeriLaptop() | !validateDetail() | !validateTanggal() | !validateJam() | !validateTempat() | !validateNama() | !validateEmail() | !validatePhone()) {
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
  }

  private void openPlacePicker() {
    if (hasPermissionInManifest(getActivity(), 1, Manifest.permission.ACCESS_FINE_LOCATION)) {
      Intent intent = new Intent(getContext(), MapActivity.class);
      Bundle bundle = new Bundle();

      bundle.putString(SimplePlacePicker.API_KEY, getString(R.string.maps_api_key));
      String country = "idn";
      String language = "en";
      bundle.putString(SimplePlacePicker.COUNTRY, country);
      bundle.putString(SimplePlacePicker.LANGUAGE, language);

      intent.putExtras(bundle);
      startActivityForResult(intent, SimplePlacePicker.SELECT_LOCATION_REQUEST_CODE);
    }

  }

  private String getAddress(LatLng latLng) {

    Geocoder geocoder;
    List<Address> addresses;
    geocoder = new Geocoder(getActivity(), Locale.getDefault());

    try {
      addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
      String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
      String city = addresses.get(0).getLocality();
      String state = addresses.get(0).getAdminArea();
      String country = addresses.get(0).getCountryName();
      String postalCode = addresses.get(0).getPostalCode();
      String knownName = addresses.get(0).getFeatureName();
      return address;

    } catch (IOException e) {
      e.printStackTrace();
      return "No Address Found";

    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == SimplePlacePicker.SELECT_LOCATION_REQUEST_CODE && resultCode == RESULT_OK) {
      if (data != null) {
        String toastMsg = data.getStringExtra(SimplePlacePicker.SELECTED_ADDRESS);
        endlatitude = data.getDoubleExtra(SimplePlacePicker.LOCATION_LAT_EXTRA, -1);
        endlongtitude = data.getDoubleExtra(SimplePlacePicker.LOCATION_LNG_EXTRA,-1);
        Ongkir();
        fragmentOrderBinding.alamatTempatBertemu.setText(toastMsg);
      }
    }
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
          Toast.makeText(getContext(), "Gagal mengambil list laptop", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<ResponseLaptopModel> call, Throwable t) {
        Toast.makeText(getContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
      }
    });
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
    int HOUR = calendar.get(Calendar.HOUR_OF_DAY);
    int Minute = calendar.get(Calendar.MINUTE);

    TimePickerDialog tpd = TimePickerDialog.newInstance((view, hourOfDay, minute, second) -> fragmentOrderBinding.EdtJam.setText(convertTime(hourOfDay) + ":" + convertTime(minute)), HOUR, Minute, true);
    tpd.setMinTime(8, 0, 0);
    tpd.setMaxTime(15, 0, 0);
    tpd.show(getFragmentManager(), "");
  }

  private void openDatePicker() {
    Calendar calendar = Calendar.getInstance();
    int YEAR = calendar.get(Calendar.YEAR);
    int MONTH = calendar.get(Calendar.MONTH);
    int Day = calendar.get(Calendar.DAY_OF_MONTH);
    int Hour = calendar.get(Calendar.HOUR_OF_DAY);

    DatePickerDialog dpd = DatePickerDialog.newInstance((view, year, monthOfYear, dayOfMonth) -> {
      String date = dayOfMonth + "-" + (++monthOfYear) + "-" + year;
      fragmentOrderBinding.EdtTanggal.setText(date);
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

  private Boolean validateSeriLaptop() {
    String val = fragmentOrderBinding.EdtSeriLaptop.getText().toString();
    if (val.isEmpty()) {
      fragmentOrderBinding.EdtSeriLaptop.setError("Tidak boleh kosong");
      return false;
    }
    return true;
  }

  private Boolean validateDetail() {
    String val = fragmentOrderBinding.EdtDetailPemesanan.getText().toString();
    if (val.isEmpty()) {
      fragmentOrderBinding.EdtDetailPemesanan.setError("Tidak boleh kosong");
      return false;
    }
    return true;
  }

  private Boolean validateTanggal() {
    String val = fragmentOrderBinding.EdtTanggal.getText().toString();
    if (val.isEmpty()) {
      fragmentOrderBinding.EdtTanggal.setError("Tidak boleh kosong");
      return false;
    }
    return true;
  }

  private Boolean validateJam() {
    String val = fragmentOrderBinding.EdtJam.getText().toString();
    if (val.isEmpty()) {
      fragmentOrderBinding.EdtJam.setError("Tidak boleh kosong");
      return false;
    }
    return true;
  }

  private Boolean validateTempat() {
    String val = fragmentOrderBinding.alamatTempatBertemu.getText().toString();
    if (val.isEmpty()) {
      fragmentOrderBinding.alamatTempatBertemu.setError("Tidak boleh kosong");
      return false;
    }
    return true;
  }

  private Boolean validateNama() {
    String val = fragmentOrderBinding.EdtNama.getText().toString();
    String anyletter = "(^[a-zA-Z\\s]+$)";
    if (val.isEmpty()) {
      fragmentOrderBinding.EdtNama.setError("Tidak boleh kosong");
      return false;
    } else if (!val.matches(anyletter)) {
      fragmentOrderBinding.EdtNama.setError("Hanya huruf yang diperbolehkan");
      return false;
    } else {
      fragmentOrderBinding.EdtNama.setError(null);
      return true;
    }

  }

  private Boolean validateEmail() {
    String val = fragmentOrderBinding.EdtEmail.getText().toString();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    if (val.isEmpty()) {
      fragmentOrderBinding.EdtEmail.setError("Tidak boleh kosong");
      return false;
    } else if (!val.matches(emailPattern)) {
      fragmentOrderBinding.EdtEmail.setError("Alamat email salah");
      return false;
    } else {
      fragmentOrderBinding.EdtEmail.setError(null);
      return true;
    }
  }

  private Boolean validatePhone() {
    String val = fragmentOrderBinding.EdtNomorTelepon.getText().toString();
    String phoneformat = "^[0-9]{11,13}$";
    if (val.isEmpty()) {
      fragmentOrderBinding.EdtNomorTelepon.setError("Tidak boleh kosong");
      return false;
    } else if (!val.matches(phoneformat)) {
      fragmentOrderBinding.EdtNomorTelepon.setError("Nomor telepon tidak valid");
      return false;
    } else {
      fragmentOrderBinding.EdtNomorTelepon.setError(null);
      return true;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    if (requestCode == 1) {
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        openPlacePicker();
    }
  }

  //check for location permission
  public static boolean hasPermissionInManifest(Activity activity, int requestCode, String permissionName) {
    if (ContextCompat.checkSelfPermission(activity,
            permissionName)
            != PackageManager.PERMISSION_GRANTED) {
      // No explanation needed, we can request the permission.
      ActivityCompat.requestPermissions(activity,
              new String[]{permissionName},
              requestCode);
    } else {
      return true;
    }
    return false;
  }

  //menentukan harga ongkos kirim
 private void Ongkir(){
    double lat_unj = -6.1944545 ;
    double long_unj= 106.8765061;
    int hargadasar = 3500;
    double R = 6371;

   if (endlatitude != null && endlongtitude != null ){
     //rumus
     double latrad1 = endlatitude * (Math.PI/180);
     double latrad2 = lat_unj * (Math.PI/180);
     double deltalatRad = (lat_unj - endlatitude) * (Math.PI/180);
     double deltalongRad = (long_unj - endlongtitude) * (Math.PI/180);

     // menghitung jarak
     Double a = (Math.sin(deltalatRad/2)* Math.sin(deltalatRad/2)) + Math.cos(latrad1)*Math.cos(latrad2) * (Math.sin(deltalongRad/2) * Math.sin(deltalongRad/2));
     Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
     Double J = R * c; // hasil jarak dalam meter
     Jarak = Double.valueOf(Math.round(J));
     //hitung ongkir
     if (Jarak <= 3){
       HargaOngkir = 0;

     } else if (Jarak > 3 ){
       HargaOngkir = (int) Math.ceil((int) (hargadasar * Jarak * 3/2));
     }
     else{
       HargaOngkir =0;
       Jarak = 0.0;
     }
   }else{
     HargaOngkir =0;
     Jarak = 0.0;

   }

  }

}