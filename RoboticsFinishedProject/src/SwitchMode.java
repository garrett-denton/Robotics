import lejos.robotics.subsumption.Behavior;
import java.rmi.RemoteException;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Button;

public class SwitchMode implements Behavior {

	// Initializing suppression boolean to false
	private boolean suppressed = false;
	// Instantiating touch sensor
	public static EV3TouchSensor touchSensor;
	// Instantiating sample provider which fetches a measurement
	// taken by a sensor at a single moment in time
	private SampleProvider sampleTouch;
	// Initializing array of floats pulled from the sample provider
	private float[] pressArray;
	// Initializing float for button press
	private float press;
	// Initializing mode string for print statement
	private String mode;
	

	public SwitchMode() {
		// Instantiating port for the color sensor
		Port sensor = LocalEV3.get().getPort("S3");
		// Setting touch sensor to the port
		touchSensor = new EV3TouchSensor(sensor);
		// Setting the mode of the color sensors to ambient, 
		// returning a float of the touch status
		sampleTouch = touchSensor.getTouchMode();
		// Setting the touch status array to the size of the sample (should be 1)
		pressArray = new float[sampleTouch.sampleSize()];
	}

	public boolean takeControl() {
		// Retrieves the sample array at this instance of the touch status
		touchSensor.fetchSample(pressArray, 0);
		// Sets the touch status as the first instance of the sample array
		press = pressArray[0];
		// takeControl returns true if the touch status is 1 (pressed)
		return press == 1;
	}

	public void action() {
		// Setting suppression boolean to false
		suppressed = false;
		// Static yellow led
		Button.LEDPattern(3);
		// Setting mode string for print statement
		if (MyEv3Robot.getSwitchMode()) {
			setMode("run from light");
		}
		else {
			setMode("follow light");
		}
		System.out.println("Mode switched to " + getMode());
		if(!suppressed)
			MyEv3Robot.flipSwitchMode();
	}

	public void suppress() {
		suppressed = true;
	}
	
	// Returns the light tracking mode
	public String getMode() {
		return mode;
	}

	// Sets the string for the light trackinf mode
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	// Since the sensor are initialized in this class, they can be cleaned and closed in this class
	// when exiting the arbitrator; clean and close are made for the CloseAndExit class
	public static void cleanTouch(EV3TouchSensor sensor) throws RemoteException {		
        System.out.println("Cleanup: Closing touch sensor");
        closeTouch(sensor);
    }

	// Closes sensor if possible
    public static void closeTouch(EV3TouchSensor sensor) throws RemoteException {
        if (sensor != null) {
            sensor.close();
        }
    }
}