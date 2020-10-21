package com.mobcom.troubleshoot.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class ItemOrderModel {

	@SerializedName("account_id")
	private String accountId;

	@SerializedName("harga")
	private String harga;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("kerusakan_id")
	private String kerusakanId;

	@SerializedName("jenis")
	private String jenis;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("nama_kerusakan")
	private String namaKerusakan;

	@SerializedName("order_id")
	private String orderId;

	@SerializedName("tracking_key")
	private String trackingKey;

	public ItemOrderModel(String accountId, String harga, String jumlah, String kerusakanId, String jenis, String createdAt, String totalHarga, String namaKerusakan, String orderId, String trackingKey) {
		this.accountId = accountId;
		this.harga = harga;
		this.jumlah = jumlah;
		this.kerusakanId = kerusakanId;
		this.jenis = jenis;
		this.createdAt = createdAt;
		this.totalHarga = totalHarga;
		this.namaKerusakan = namaKerusakan;
		this.orderId = orderId;
		this.trackingKey = trackingKey;
	}

	public void setAccountId(String accountId){
		this.accountId = accountId;
	}

	public String getAccountId(){
		return accountId;
	}

	public void setHarga(String harga){
		this.harga = harga;
	}

	public String getHarga(){
		return harga;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setKerusakanId(String kerusakanId){
		this.kerusakanId = kerusakanId;
	}

	public String getKerusakanId(){
		return kerusakanId;
	}

	public void setJenis(String jenis){
		this.jenis = jenis;
	}

	public String getJenis(){
		return jenis;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setTotalHarga(String totalHarga){
		this.totalHarga = totalHarga;
	}

	public String getTotalHarga(){
		return totalHarga;
	}

	public void setNamaKerusakan(String namaKerusakan){
		this.namaKerusakan = namaKerusakan;
	}

	public String getNamaKerusakan(){
		return namaKerusakan;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setTrackingKey(String trackingKey){
		this.trackingKey = trackingKey;
	}

	public String getTrackingKey(){
		return trackingKey;
	}

	@Override
	public String toString() {
		return "ItemOrderModel{" +
						"accountId='" + accountId + '\'' +
						", harga='" + harga + '\'' +
						", jumlah='" + jumlah + '\'' +
						", kerusakanId='" + kerusakanId + '\'' +
						", jenis='" + jenis + '\'' +
						", createdAt='" + createdAt + '\'' +
						", totalHarga='" + totalHarga + '\'' +
						", namaKerusakan='" + namaKerusakan + '\'' +
						", orderId='" + orderId + '\'' +
						", trackingKey='" + trackingKey + '\'' +
						'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ItemOrderModel that = (ItemOrderModel) o;
		return getAccountId().equals(that.getAccountId()) &&
						getHarga().equals(that.getHarga()) &&
						getJumlah().equals(that.getJumlah()) &&
						getKerusakanId().equals(that.getKerusakanId()) &&
						getJenis().equals(that.getJenis()) &&
						getCreatedAt().equals(that.getCreatedAt()) &&
						getTotalHarga().equals(that.getTotalHarga()) &&
						getNamaKerusakan().equals(that.getNamaKerusakan()) &&
						getOrderId().equals(that.getOrderId()) &&
						getTrackingKey().equals(that.getTrackingKey());
	}

	public static DiffUtil.ItemCallback<ItemOrderModel> itemCallback = new DiffUtil.ItemCallback<ItemOrderModel>() {
		@Override
		public boolean areItemsTheSame(@NonNull ItemOrderModel oldItem, @NonNull ItemOrderModel newItem) {
			return oldItem.getOrderId().equals(newItem.getOrderId());
		}

		@Override
		public boolean areContentsTheSame(@NonNull ItemOrderModel oldItem, @NonNull ItemOrderModel newItem) {
			return oldItem.equals(newItem);
		}
	};
}