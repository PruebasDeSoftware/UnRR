package main;

import gui.UnRRGUI;

public class UnRR {

	private App app;
	private Recorder recorder;
	private UnRRGUI gui;


	public UnRR(App app, UnRRGUI gui) {
		this.app = app;
		this.gui = gui;
	}

	public void startRecording(Device device) {
		device.startApp(app);

		recorder = new Recorder(gui, device);
		recorder.startRecording();
	}

	public void stopRecording() {
		recorder.stopRecording();
	}

	public void Replay(Device device) {
		device.startApp(app);

		recorder.replay(device);
	}

}
