package com.jiale.netty.client.model;

import com.jiale.netty.core.model.Serializer;

public class ResponseModel extends Serializer {

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

	@Override
	protected void read() {
		this.message = this.readString();
		this.model = this.readObject(DeviceModel.class);
	}

	@Override
	protected void write() {
		this.writeString(this.message);
		this.writeObject(this.model,DeviceModel.class);
	}
}
