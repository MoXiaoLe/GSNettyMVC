package com.gosuncn.netty.business.model;

public class DeviceModel {
	
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
	
}
