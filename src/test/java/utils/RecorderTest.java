/**
 * 
 */
package utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gui.UnRRGUI;
import main.Device;
import main.Recorder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author juan
 *
 */
public class RecorderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link main.Recorder#Recorder(gui.UnRRGUI)}.
	 */
	@Test
	public void testRecorder() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.Recorder#startRecording(main.Device)}.
	 */
	@Test
	public void testStartRecording() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.Recorder#stopRecording()}.
	 */
	@Test
	public void testStopRecording() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link main.Recorder#replay(main.Device)}.
	 */
	@Test
	public void testReplay() {
		UnRRGUI gui = new UnRRGUI();
		
		String serialNumber = "emulator-5554";

		Device sourceDevice = Device.getDevice(serialNumber);
		
		Recorder recorder = new Recorder(gui, sourceDevice);
		recorder.replay(sourceDevice);

		assertTrue(true);
	}

}
