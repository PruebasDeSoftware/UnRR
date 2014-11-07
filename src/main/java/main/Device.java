package main;

import java.awt.Dimension;

public class Device {

	private Dimension screenResolution;
	private String serialNumber;

	public Dimension getScreenResolution() {
		return screenResolution;
	}

	public int getOrientation() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void startApp(App app) {
		// TODO Auto-generated method stub

	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public static Device getDummyDevice(String serialNumber, Dimension screenResolution) {
		Device device = new Device();
		device.serialNumber = serialNumber;
		device.screenResolution = screenResolution;

		return device;
	}

}
