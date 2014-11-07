package main;

import gui.UnRRGUI;

public class UnRR {

	private App app;
	private Recorder recorder;


	public UnRR(App app, UnRRGUI gui) {
		this.app = app;
		recorder = new Recorder(gui);
	}

	public void startRecording(Device device) {


		device.startApp(app);

		recorder.startRecording(device);
	}

	public void stopRecording() {
		recorder.stopRecording();
	}

	public void Replay(Device device) {
		device.startApp(app);

		recorder.replay(device);
	}

}
