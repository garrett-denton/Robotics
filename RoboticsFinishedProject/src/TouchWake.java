import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

import java.rmi.RemoteException;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Button;

public class TouchWake implements Behavior {

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
	// Initializing integer for motor speed
	private int motorSpeed;

	public TouchWake() {
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
		if(!suppressed)
			wakeUp();
	}

	public void suppress() {
		suppressed = true;
	}
	
	public void wakeUp() {
		try {
			System.out.println("Waking up");
			// Setting motor speed integer to medium range value
			motorSpeed = 600;
			// Setting motor speeds to the motors
			MyEv3Robot.leftMotor.setSpeed(motorSpeed);
			MyEv3Robot.rightMotor.setSpeed(motorSpeed);
			// Tells motors to drive backwards
			MyEv3Robot.leftMotor.backward();
			MyEv3Robot.rightMotor.backward();
			// Holds current function (backwards) for 2 seconds
			Delay.msDelay(2000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Holds any error messages on the LCD for 5 seconds if they occur
			Delay.msDelay(5000);
		}
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