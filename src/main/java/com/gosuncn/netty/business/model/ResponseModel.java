package com.gosuncn.netty.business.model;

public class ResponseModel {

	private String message;
	
	private DeviceModel model;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DeviceModel getModel() {
		return model;
	}

	public void setModel(DeviceModel model) {
		this.model = model;
	}
	
}
