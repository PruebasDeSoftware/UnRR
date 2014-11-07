package main;

import gui.UnRRGUI;

public class Main_UnRR {

	public static void main(String[] args) {

		UnRRGUI gui = new UnRRGUI();

		Recorder recorder = new Recorder(gui);
		recorder.replay(new Device());

	}

}
