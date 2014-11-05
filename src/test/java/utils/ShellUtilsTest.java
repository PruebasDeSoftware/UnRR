package utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ShellUtilsTest {

	@Test
	public void testExecuteCommand() {

		String domainName = "google.com";

		// in mac oxs
		String command = "ping -c 3 " + domainName;

		// in windows
		// String command = "ping -n 3 " + domainName;

		String output = ShellUtils.executeCommand(command);
		assertNotNull(output);
		System.out.println(output);
	}

}
