package main;

import utils.ShellUtils;


public class Recorder {

	private String recordedEvents;
	private ShellUtils shell;

	public void startRecording(Device device) {
		shell = new ShellUtils();
		shell.startExecutingCommand("adb shell getevent -tt");

	}

	public void stopRecording() {
		recordedEvents = shell.stopExecutingCommand();

	}

	public void replay(String eventTrace, Device device) {
		// TODO Auto-generated method stub

	}

	public String getEventTrace() {
		// TODO Auto-generated method stub
		return null;
	}

}
