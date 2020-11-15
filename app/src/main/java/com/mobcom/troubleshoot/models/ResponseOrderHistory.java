package com.mobcom.troubleshoot.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseOrderHistory {

	@SerializedName("data")
	private List<OrderHistoryModel> data;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private boolean status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setData(List<OrderHistoryModel> data){
		this.data = data;
	}

	public List<OrderHistoryModel> getData(){
		return data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}