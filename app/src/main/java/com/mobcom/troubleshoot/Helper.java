package com.mobcom.troubleshoot;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Helper {
  public String formatRupiah(int jumlah){
    String hasil;

    DecimalFormat kursIndo = (DecimalFormat) DecimalFormat.getCurrencyInstance();
    DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

    formatRp.setCurrencySymbol("Rp. ");
    formatRp.setMonetaryDecimalSeparator(',');
    formatRp.setGroupingSeparator('.');
    kursIndo.setDecimalFormatSymbols(formatRp);
    hasil = kursIndo.format(jumlah);

    return hasil;
  }

  public String formatRp(int number){
    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    return formatRupiah.format(number);
  }
}
