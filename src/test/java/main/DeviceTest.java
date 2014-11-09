package main;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeviceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDevice() {
		assertNotNull(Device.getDevice("emulator-5554"));
	}

	@Test
	public void testGetScreenResolution() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetOrientation() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testStartApp() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetSerialNumber() {
		fail("Not yet implemented"); // TODO
	}

}
