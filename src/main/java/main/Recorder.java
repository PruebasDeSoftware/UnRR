package main;

import gui.UnRRGUI;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import utils.ShellUtils;

public class Recorder {

	private UnRRGUI gui;

	private ShellUtils shell;

	private Dimension sourceScreenResolution;
	private int sourceOrientation;

	private Device sourceDevice;

	private String recordedEventsFilePath = "./unrr/recordedEvents.txt";

	public Recorder(UnRRGUI gui, Device device) {
		super();
		this.gui = gui;
		shell = new ShellUtils();

		this.sourceDevice = device;
		this.sourceScreenResolution = device.getScreenResolution();
		this.sourceOrientation = device.getOrientation();
	}

	/**
	 * Start the recording on the device
	 * 
	 * @param device
	 */
	public void startRecording() {

		File file = new File(recordedEventsFilePath);
		shell.startExecutingCommand(gui.getPathToADB() + " -s " + sourceDevice.getSerialNumber()
									+ " shell getevent -tt", file);

	}

	public void stopRecording() {
		shell.stopExecutingCommand();
	}

	public void replay(Device device) {
		String replayerPath = "./lib/RERAN/replay.exe";
		String translatorPath = "./lib/RERAN/translate.jar";
		String scaledEventsPath = "./unrr/scaledEvents.txt";
		String translatedEventsPath = "./unrr/translatedEvents.txt";

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
		response = ShellUtils.executeCommand(gui.getPathToADB() + " -s "
													+ device.getSerialNumber()
												+ " shell chmod 766 /data/local/replay.exe");
		System.out.println(response);

		// scale events for target device
		int indexOfX = 4;
		int indexOfY = 5;
		ArrayList<String> scaledEventsLines = new ArrayList<String>();
		try {
			List<String> eventList = FileUtils.readLines(new File(recordedEventsFilePath));
			String splitOn = "( )+";
			for (String line : eventList) {
				String scaledEventLine = line;
				if ((line.lastIndexOf("event") != -1) && (line.lastIndexOf("device") == -1)
					&& (line.lastIndexOf("name") == -1)) {
					String[] tokens = StringUtils.split(line, splitOn);

					String tokenX = tokens[indexOfX];
					String tokenY = tokens[indexOfY];
										
					int x = Integer.parseInt(tokenX, 16);
					int y = Integer.parseInt(tokenY, 16);

					int newX = x * device.getScreenResolution().width
								/ sourceScreenResolution.width;
					int newY = y * device.getScreenResolution().height
								/ sourceScreenResolution.height;
					System.out.println("(" + x + ", " + y + ") => (" + newX + ", " + newY + ")");
					
					String hexX = StringUtils.leftPad(Integer.toHexString(newX), tokenX.length(),"0");
					String hexY = StringUtils.leftPad(Integer.toHexString(newY), tokenY.length(),"0");
					
					tokens[indexOfX] = hexX;
					tokens[indexOfY] = hexY;

					scaledEventLine = StringUtils.join(tokens, " ");
				}
				scaledEventsLines.add(scaledEventLine);

			}
			FileUtils.writeLines(new File(scaledEventsPath), scaledEventsLines);
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

		response = ShellUtils.executeCommand("java -jar " + translatorPath + " " + scaledEventsPath
												+ " "
									+ translatedEventsPath);
		System.out.println(response);
		// send events to device
		response = ShellUtils.executeCommand(gui.getPathToADB() + " -s " + device.getSerialNumber()
												+ " push " + translatedEventsPath
									+ " /data/local");
		System.out.println(response);
		

		// run events on replayer
		response = ShellUtils
				.executeCommand(gui.getPathToADB() + " -s " + device.getSerialNumber()
								+ " shell /data/local/replay.exe /data/local/translatedEvents.txt");
		System.out.println(response);
	}

}
