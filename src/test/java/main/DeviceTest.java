package main;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeviceTest {

	private static final String TABLET_EMULATOR_SERIAL_NUMBER = "emulator-5554";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDevice() {
		assertNotNull(Device.getDevice(TABLET_EMULATOR_SERIAL_NUMBER));
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
		Device device = Device.getDevice(TABLET_EMULATOR_SERIAL_NUMBER);
		App calculator = new App("com.android.calculator2", ".Calculator");

		device.startApp(calculator);
	}

	@Test
	public void testGetSerialNumber() {
		fail("Not yet implemented"); // TODO
	}

}
