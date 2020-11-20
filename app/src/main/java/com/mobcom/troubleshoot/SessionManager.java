package com.mobcom.troubleshoot;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mobcom.troubleshoot.models.Login.LoginData;

import java.util.HashMap;

public class SessionManager {

  private Context context;
  private SharedPreferences sharedPreferences;
  private SharedPreferences.Editor editor;

  public static final String IS_LOGGED_IN = "isLoggedIn";
  public static final String ACCOUNT_ID = "account_id";
  public static final String FIRST_NAME = "firstname";
  public static final String LAST_NAME = "lastname";
  public static final String EMAIL = "email";
  public static final String ALAMAT = "alamat";
  public static final String NOMOR_HP = "nomor_hp";
  public static final String PICTURE = "picture";
  public static final String PROVIDER = "provider";

  public SessionManager(Context context) {
    this.context = context;
    sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    editor = sharedPreferences.edit();
  }

  public void createLoginSession(LoginData user) {
    editor.putBoolean(IS_LOGGED_IN, true);
    editor.putString(ACCOUNT_ID, user.getAccountsId());
    editor.putString(FIRST_NAME, user.getFirstName());
    editor.putString(LAST_NAME, user.getLastName());
    editor.putString(EMAIL, user.getEmail());
    editor.putString(ALAMAT, user.getAlamat());
    editor.putString(NOMOR_HP, user.getNomor_hp());
    editor.putString(PICTURE, user.getPicture());
    editor.putString(PROVIDER, user.getOauthProvider());
    editor.commit();
  }

  public HashMap<String, String> getUserDetail() {
    HashMap<String, String> user = new HashMap<>();
    user.put(ACCOUNT_ID, sharedPreferences.getString(ACCOUNT_ID, null));
    user.put(FIRST_NAME, sharedPreferences.getString(FIRST_NAME, null));
    user.put(LAST_NAME, sharedPreferences.getString(LAST_NAME, null));
    user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
    user.put(ALAMAT, sharedPreferences.getString(ALAMAT, null));
    user.put(NOMOR_HP, sharedPreferences.getString(NOMOR_HP, null));
    user.put(PICTURE, sharedPreferences.getString(PICTURE, null));
    user.put(PROVIDER, sharedPreferences.getString(PROVIDER, null));
    return user;
  }

  public void logoutSession() {
    editor.clear();
    editor.commit();
  }

  public boolean isLoggedin() {
    return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
  }

}
