package main;

import gui.UnRRGUI;

import java.awt.Dimension;

public class UnRR {

	private App app;
	private Recorder recorder;
	private Dimension sourceScreenResolution;
	private int sourceOrientation;

	public UnRR(App app, UnRRGUI gui) {
		this.app = app;
		recorder = new Recorder(gui);
	}

	public void startRecording(Device device) {
		this.sourceScreenResolution = device.getScreenResolution();
		this.sourceOrientation = device.getOrientation();

		device.startApp(app);

		recorder.startRecording(device);
	}

	public void stopRecording() {
		recorder.stopRecording();
	}

	public void Replay(Device device) {
		String eventTrace = recorder.getEventTrace();

		String newEventTrance = this.translateEventTrace(eventTrace, device);

		device.startApp(app);

		recorder.replay(newEventTrance, device);
	}

	private String translateEventTrace(String eventTrace, Device device) {
		// TODO Auto-generated method stub
		return null;
	}

}
