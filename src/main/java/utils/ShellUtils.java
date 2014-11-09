package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;

import org.apache.commons.lang3.StringUtils;

public class ShellUtils {

	public static String executeCommand(String command) {
		System.out.println("EXECUTING: " + command);

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	private Process process;

	public void startExecutingCommand(String command, File file) {
		System.out.println("start EXECUTING: " + command);
		ProcessBuilder pb = new ProcessBuilder(StringUtils.split(command));

		pb.redirectOutput(file);
		System.out.println(pb.redirectOutput());
		try {
			process = pb.start();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		assert pb.redirectInput() == Redirect.PIPE;
		assert pb.redirectOutput().file() == file;
		try {
			assert process.getInputStream().read() == -1;
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	public void stopExecutingCommand() {
		if (process != null) {
		process.destroy();
			System.out.println("stoped process");
			return;
		}
		System.out.println("null process");
	}
}
