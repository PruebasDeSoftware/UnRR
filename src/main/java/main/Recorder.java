package main;

import gui.UnRRGUI;

import java.io.File;
import java.io.IOException;

import utils.ShellUtils;

public class Recorder {

	private UnRRGUI gui;

	private String recordedEvents;
	private ShellUtils shell;

	public Recorder(UnRRGUI gui) {
		super();
		this.gui = gui;
	}

	public void startRecording(Device device) {
		shell = new ShellUtils();
		shell.startExecutingCommand(gui.getPathToADB() + " -s " + device.getSerialNumber()
									+ " shell getevent -tt");

	}

	public void stopRecording() {
		recordedEvents = shell.stopExecutingCommand();

	}

	public void replay(String eventTrace, Device device) {
		String translatorPath = null;
		String scaledEventsPath = null;
		String translatedEventsPath = null;

		// Install Replayer On device
		String replayerPath = null;
		try {
			File file = new File("./lib/RERAN/replay.exe");
			replayerPath = file.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("No se encontro replay.exe");
			e.printStackTrace();
		}

		ShellUtils.executeCommand(gui.getPathToADB() + " -s " + device.getSerialNumber()
									+ " push -p " + replayerPath + " /data/local");
		
		// scale events for target device
		String scaledEvents = "";
		
		// translate events for replayer
		try {
			File file = new File("./lib/RERAN/translate.jar");
			translatorPath = file.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("No se encontro translate.jar");
			e.printStackTrace();
		}

		ShellUtils.executeCommand("java -jar " + translatorPath + " " + scaledEventsPath + " "
									+ translatedEventsPath);

		// send events to device
		// TODO use variables for pahts
		ShellUtils.executeCommand("./adb push translatedEvents.txt /data/local");
		

		// run events on replayer
		// TODO use variables for pahts
		ShellUtils
				.executeCommand("/adb shell /data/local/./replay.exe /data/local/translatedEvents.txt");
	}

	public String getEventTrace() {
		// TODO Auto-generated method stub
		return null;
	}

}
