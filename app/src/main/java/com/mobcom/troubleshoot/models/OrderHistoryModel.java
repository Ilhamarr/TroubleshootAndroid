package com.mobcom.troubleshoot.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class OrderHistoryModel {

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
	private String jamPengembalian;

	@SerializedName("header_order_id")
	private String headerOrderId;

	@SerializedName("tracking_key")
	private String trackingKey;

	@SerializedName("tanggal_pengambilan")
	private String tanggalPengambilan;

	@SerializedName("tanggal_pengembalian")
	private String tanggalPengembalian;

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

	public OrderHistoryModel(String tipeLaptop, String totalItem, String keterangan, String merk, String teknisi, String createdAt, String jamPengembalian, String headerOrderId, String trackingKey, String tanggalPengambilan, String tanggalPengembalian, String biayaTotal, String tempatBertemu, String statusPayment, String accountId, String statusTracking, String nama, String imageOrder, String jamPengambilan, String modifiedAt, String email, String trackingId, String nomorHp, String merkLaptop) {
		this.tipeLaptop = tipeLaptop;
		this.totalItem = totalItem;
		this.keterangan = keterangan;
		this.merk = merk;
		this.teknisi = teknisi;
		this.createdAt = createdAt;
		this.jamPengembalian = jamPengembalian;
		this.headerOrderId = headerOrderId;
		this.trackingKey = trackingKey;
		this.tanggalPengambilan = tanggalPengambilan;
		this.tanggalPengembalian = tanggalPengembalian;
		this.biayaTotal = biayaTotal;
		this.tempatBertemu = tempatBertemu;
		this.statusPayment = statusPayment;
		this.accountId = accountId;
		this.statusTracking = statusTracking;
		this.nama = nama;
		this.imageOrder = imageOrder;
		this.jamPengambilan = jamPengambilan;
		this.modifiedAt = modifiedAt;
		this.email = email;
		this.trackingId = trackingId;
		this.nomorHp = nomorHp;
		this.merkLaptop = merkLaptop;
	}

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

	public void setJamPengembalian(String jamPengembalian){
		this.jamPengembalian = jamPengembalian;
	}

	public String getJamPengembalian(){
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

	public void setTanggalPengembalian(String tanggalPengembalian){
		this.tanggalPengembalian = tanggalPengembalian;
	}

	public String getTanggalPengembalian(){
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderHistoryModel that = (OrderHistoryModel) o;
		return getTipeLaptop().equals(that.getTipeLaptop()) &&
						getTotalItem().equals(that.getTotalItem()) &&
						getKeterangan().equals(that.getKeterangan()) &&
						getMerk().equals(that.getMerk()) &&
						getTeknisi().equals(that.getTeknisi()) &&
						getCreatedAt().equals(that.getCreatedAt()) &&
						getJamPengembalian().equals(that.getJamPengembalian()) &&
						getHeaderOrderId().equals(that.getHeaderOrderId()) &&
						getTrackingKey().equals(that.getTrackingKey()) &&
						getTanggalPengambilan().equals(that.getTanggalPengambilan()) &&
						getTanggalPengembalian().equals(that.getTanggalPengembalian()) &&
						getBiayaTotal().equals(that.getBiayaTotal()) &&
						getTempatBertemu().equals(that.getTempatBertemu()) &&
						getStatusPayment().equals(that.getStatusPayment()) &&
						getAccountId().equals(that.getAccountId()) &&
						getStatusTracking().equals(that.getStatusTracking()) &&
						getNama().equals(that.getNama()) &&
						getImageOrder().equals(that.getImageOrder()) &&
						getJamPengambilan().equals(that.getJamPengambilan()) &&
						getModifiedAt().equals(that.getModifiedAt()) &&
						getEmail().equals(that.getEmail()) &&
						getTrackingId().equals(that.getTrackingId()) &&
						getNomorHp().equals(that.getNomorHp()) &&
						getMerkLaptop().equals(that.getMerkLaptop());
	}

	public static DiffUtil.ItemCallback<OrderHistoryModel> itemCallback = new DiffUtil.ItemCallback<OrderHistoryModel>() {
		@Override
		public boolean areItemsTheSame(@NonNull OrderHistoryModel oldItem, @NonNull OrderHistoryModel newItem) {
			return oldItem.getHeaderOrderId().equals(newItem.getHeaderOrderId());
		}

		@Override
		public boolean areContentsTheSame(@NonNull OrderHistoryModel oldItem, @NonNull OrderHistoryModel newItem) {
			return oldItem.equals(newItem);
		}
	};

}