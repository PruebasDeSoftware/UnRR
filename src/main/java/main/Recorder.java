package main;

import gui.UnRRGUI;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import utils.ShellUtils;

public class Recorder {

	private UnRRGUI gui;

	private String recordedEvents;
	private ShellUtils shell;

	private Dimension sourceScreenResolution;
	private int sourceOrientation;

	public Recorder(UnRRGUI gui) {
		super();
		this.gui = gui;
		shell = new ShellUtils();
	}

	/**
	 * Start the recording on the device
	 * 
	 * @param device
	 */
	public void startRecording(Device device) {
		this.sourceScreenResolution = device.getScreenResolution();
		this.sourceOrientation = device.getOrientation();

		shell.startExecutingCommand(gui.getPathToADB() + " -s " + device.getSerialNumber()
									+ " shell getevent -tt");

	}

	public void stopRecording() {
		recordedEvents = shell.stopExecutingCommand();
		// TODO save file
	}

	public void replay(Device device) {
		String replayerPath = "./lib/RERAN/replay.exe";
		String translatorPath = "./lib/RERAN/translate.jar";
		String scaledEventsPath = "./scaledEvents.txt";
		String translatedEventsPath = "./translatedEvent.txt";

		// Install Replayer On device
		try {
			File file = new File("./lib/RERAN/replay.exe");
			replayerPath = file.getCanonicalPath();
		} catch (IOException e) {
			System.err.println("No se encontro replay.exe");
			e.printStackTrace();
		}

		String response = ShellUtils.executeCommand(gui.getPathToADB() + " -s "
													+ device.getSerialNumber()
									+ " push -p " + replayerPath + " /data/local");
		System.out.println(response);
		String recordedEventsFilePath = "./unrr/recordedEvents.txt";
		// scale events for target device
		// TODO
		try {
			List<String> eventList = FileUtils.readLines(new File(recordedEventsFilePath));
			String splitOn = "( )+";
			for (String line : eventList) {
				if ((line.lastIndexOf("event") != -1) && (line.lastIndexOf("device") == -1)
					&& (line.lastIndexOf("name") == -1)) {
					String[] tokens = StringUtils.split(line, splitOn);

					String tokenX = tokens[4];
					String tokenY = tokens[5];
										
					int x = Integer.parseInt(tokenX, 16);
					int y = Integer.parseInt(tokenY, 16);

					int newX = x * device.getScreenResolution().width
								/ sourceScreenResolution.width;
					int newY = y * device.getScreenResolution().height
								/ sourceScreenResolution.height;
					
					String hexX = StringUtils.leftPad(Integer.toHexString(newX), tokenX.length(),"0");
					String hexY = StringUtils.leftPad(Integer.toHexString(newY), tokenY.length(),"0");
					
					// TODO save new line on scaledEvents

				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// translate events for replayer
		try {
			File file = new File("./lib/RERAN/translate.jar");
			translatorPath = file.getCanonicalPath();
		} catch (IOException e) {
			System.err.println("No se encontro translate.jar");
			e.printStackTrace();
		}

		ShellUtils.executeCommand("java -jar " + translatorPath + " " + scaledEventsPath + " "
									+ translatedEventsPath);
		System.out.println(response);
		// send events to device
		ShellUtils.executeCommand(gui.getPathToADB() + " push " + translatedEventsPath
									+ " /data/local");
		System.out.println(response);
		

		// run events on replayer
		ShellUtils
				.executeCommand(gui.getPathToADB()
								+ " shell /data/local/./replay.exe /data/local/translatedEvents.txt");
		System.out.println(response);
	}

	public String getEventTrace() {
		// TODO Auto-generated method stub
		return null;
	}

}
