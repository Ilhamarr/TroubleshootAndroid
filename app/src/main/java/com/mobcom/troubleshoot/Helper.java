package com.mobcom.troubleshoot;

import java.text.NumberFormat;
import java.util.Locale;

public class Helper {

  public String formatRp(int number){
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    return formatRupiah.format(number);
  }
}
