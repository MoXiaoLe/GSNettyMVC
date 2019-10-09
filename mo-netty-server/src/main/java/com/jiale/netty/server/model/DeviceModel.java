package com.jiale.netty.server.model;

import com.jiale.netty.core.model.Serializer;

public class DeviceModel extends Serializer {
	
	private String deviceId;
	private String deviceName;
	private String deviceProducer;
	private int deviceType;
	private double longitude;
	private double latitude;
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public int getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getDeviceProducer() {
		return deviceProducer;
	}
	public void setDeviceProducer(String deviceProducer) {
		this.deviceProducer = deviceProducer;
	}

	@Override
	protected void read() {
		this.deviceId = this.readString();
		this.deviceName = this.readString();
		this.deviceProducer = this.readString();
		this.deviceType = this.readInt();
		this.latitude = this.readDouble();
		this.longitude = this.readDouble();
	}

	@Override
	protected void write() {
		this.writeString(this.deviceId);
		this.writeString(this.deviceName);
		this.writeString(this.deviceProducer);
		this.writeInt(this.deviceType);
		this.writeDouble(this.latitude);
		this.writeDouble(this.longitude);
	}
}
