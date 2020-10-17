package com.mobcom.troubleshoot.models;

import com.google.gson.annotations.SerializedName;

public class HeaderOderModel {

	@SerializedName("tipe_laptop")
	private String tipeLaptop;

	@SerializedName("total_item")
	private String totalItem;

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("merk")
	private String merk;

	@SerializedName("teknisi")
	private String teknisi;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("jam_pengembalian")
	private Object jamPengembalian;

	@SerializedName("header_order_id")
	private String headerOrderId;

	@SerializedName("tracking_key")
	private String trackingKey;

	@SerializedName("tanggal_pengambilan")
	private String tanggalPengambilan;

	@SerializedName("tanggal_pengembalian")
	private Object tanggalPengembalian;

	@SerializedName("biaya_total")
	private String biayaTotal;

	@SerializedName("tempat_bertemu")
	private String tempatBertemu;

	@SerializedName("status_payment")
	private String statusPayment;

	@SerializedName("account_id")
	private String accountId;

	@SerializedName("status_tracking")
	private String statusTracking;

	@SerializedName("nama")
	private String nama;

	@SerializedName("image_order")
	private String imageOrder;

	@SerializedName("jam_pengambilan")
	private String jamPengambilan;

	@SerializedName("modified_at")
	private String modifiedAt;

	@SerializedName("email")
	private String email;

	@SerializedName("tracking_id")
	private String trackingId;

	@SerializedName("nomor_hp")
	private String nomorHp;

	@SerializedName("merk_laptop")
	private String merkLaptop;

	public void setTipeLaptop(String tipeLaptop){
		this.tipeLaptop = tipeLaptop;
	}

	public String getTipeLaptop(){
		return tipeLaptop;
	}

	public void setTotalItem(String totalItem){
		this.totalItem = totalItem;
	}

	public String getTotalItem(){
		return totalItem;
	}

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setMerk(String merk){
		this.merk = merk;
	}

	public String getMerk(){
		return merk;
	}

	public void setTeknisi(String teknisi){
		this.teknisi = teknisi;
	}

	public String getTeknisi(){
		return teknisi;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setJamPengembalian(Object jamPengembalian){
		this.jamPengembalian = jamPengembalian;
	}

	public Object getJamPengembalian(){
		return jamPengembalian;
	}

	public void setHeaderOrderId(String headerOrderId){
		this.headerOrderId = headerOrderId;
	}

	public String getHeaderOrderId(){
		return headerOrderId;
	}

	public void setTrackingKey(String trackingKey){
		this.trackingKey = trackingKey;
	}

	public String getTrackingKey(){
		return trackingKey;
	}

	public void setTanggalPengambilan(String tanggalPengambilan){
		this.tanggalPengambilan = tanggalPengambilan;
	}

	public String getTanggalPengambilan(){
		return tanggalPengambilan;
	}

	public void setTanggalPengembalian(Object tanggalPengembalian){
		this.tanggalPengembalian = tanggalPengembalian;
	}

	public Object getTanggalPengembalian(){
		return tanggalPengembalian;
	}

	public void setBiayaTotal(String biayaTotal){
		this.biayaTotal = biayaTotal;
	}

	public String getBiayaTotal(){
		return biayaTotal;
	}

	public void setTempatBertemu(String tempatBertemu){
		this.tempatBertemu = tempatBertemu;
	}

	public String getTempatBertemu(){
		return tempatBertemu;
	}

	public void setStatusPayment(String statusPayment){
		this.statusPayment = statusPayment;
	}

	public String getStatusPayment(){
		return statusPayment;
	}

	public void setAccountId(String accountId){
		this.accountId = accountId;
	}

	public String getAccountId(){
		return accountId;
	}

	public void setStatusTracking(String statusTracking){
		this.statusTracking = statusTracking;
	}

	public String getStatusTracking(){
		return statusTracking;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setImageOrder(String imageOrder){
		this.imageOrder = imageOrder;
	}

	public String getImageOrder(){
		return imageOrder;
	}

	public void setJamPengambilan(String jamPengambilan){
		this.jamPengambilan = jamPengambilan;
	}

	public String getJamPengambilan(){
		return jamPengambilan;
	}

	public void setModifiedAt(String modifiedAt){
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedAt(){
		return modifiedAt;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setTrackingId(String trackingId){
		this.trackingId = trackingId;
	}

	public String getTrackingId(){
		return trackingId;
	}

	public void setNomorHp(String nomorHp){
		this.nomorHp = nomorHp;
	}

	public String getNomorHp(){
		return nomorHp;
	}

	public void setMerkLaptop(String merkLaptop){
		this.merkLaptop = merkLaptop;
	}

	public String getMerkLaptop(){
		return merkLaptop;
	}

	@Override
 	public String toString(){
		return 
			"DataItem{" + 
			"tipe_laptop = '" + tipeLaptop + '\'' + 
			",total_item = '" + totalItem + '\'' + 
			",keterangan = '" + keterangan + '\'' + 
			",merk = '" + merk + '\'' + 
			",teknisi = '" + teknisi + '\'' + 
			",created_at = '" + createdAt + '\'' + 
			",jam_pengembalian = '" + jamPengembalian + '\'' + 
			",header_order_id = '" + headerOrderId + '\'' + 
			",tracking_key = '" + trackingKey + '\'' + 
			",tanggal_pengambilan = '" + tanggalPengambilan + '\'' + 
			",tanggal_pengembalian = '" + tanggalPengembalian + '\'' + 
			",biaya_total = '" + biayaTotal + '\'' + 
			",tempat_bertemu = '" + tempatBertemu + '\'' + 
			",status_payment = '" + statusPayment + '\'' + 
			",account_id = '" + accountId + '\'' + 
			",status_tracking = '" + statusTracking + '\'' + 
			",nama = '" + nama + '\'' + 
			",image_order = '" + imageOrder + '\'' + 
			",jam_pengambilan = '" + jamPengambilan + '\'' + 
			",modified_at = '" + modifiedAt + '\'' + 
			",email = '" + email + '\'' + 
			",tracking_id = '" + trackingId + '\'' + 
			",nomor_hp = '" + nomorHp + '\'' + 
			",merk_laptop = '" + merkLaptop + '\'' + 
			"}";
		}
}