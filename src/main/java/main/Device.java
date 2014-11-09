package main;

import gui.UnRRGUI;

import java.awt.Dimension;

import org.apache.commons.lang3.StringUtils;

import utils.ShellUtils;

public class Device {

	private Dimension screenResolution;
	private String serialNumber;
	private int orientation;

	public static Device getDevice(String serialNumber) {
		Device device = new Device();
		device.serialNumber = serialNumber;
		device.findScreenResolution();
		device.findOrientation();

		return device;
	}

	private Device() {
	}

	private void findOrientation() {
		String response = ShellUtils.executeCommand(UnRRGUI.getPathToADB() + " -s " + serialNumber
													+ " shell dumpsys input");

		String[] lines = StringUtils.split(response, System.lineSeparator());
		for (String line : lines) {
			if (line.contains("SurfaceOrientation")) {
				String valueToken = StringUtils.split(line)[1];

				orientation = Integer.parseInt(valueToken);
				break;
			}
		}
	}

	private void findScreenResolution() {
		String response = ShellUtils.executeCommand(UnRRGUI.getPathToADB() + " -s " + serialNumber
													+ " shell dumpsys window");
		String[] lines = StringUtils.split(response, System.lineSeparator());
		for (String line : lines) {
			if (line.contains("mUnrestrictedScreen")) {
				String dimsValueToken = StringUtils.split(line)[1];

				String[] dims = StringUtils.split(dimsValueToken, "x");

				int width = Integer.parseInt(dims[0].trim());
				int height = Integer.parseInt(dims[1]);

				screenResolution = new Dimension(width, height);
				break;
			}
		}

	}

	public Dimension getScreenResolution() {
		return screenResolution;
	}

	public int getOrientation() {
		return orientation;
	}

	public void startApp(App app) {
		// TODO Auto-generated method stub

	}

	public String getSerialNumber() {
		return serialNumber;
	}

}
