package com.mobcom.troubleshoot.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseItemOrderDetail {

	@SerializedName("data")
	private List<ItemOrderModel> data;

	@SerializedName("status")
	private boolean status;

	public void setData(List<ItemOrderModel> data){
		this.data = data;
	}

	public List<ItemOrderModel> getData(){
		return data;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public boolean isStatus(){
		return status;
	}
}