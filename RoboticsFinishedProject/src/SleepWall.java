import lejos.robotics.subsumption.Behavior;
import java.rmi.RemoteException;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Button;
import lejos.utility.Delay;

public class SleepWall implements Behavior {

	// Initializing suppression boolean to false
	private boolean suppressed = false;
	// Instantiating ultrasonic sensor
	public static EV3UltrasonicSensor sonicSensor;
	// Instantiating sample provider which fetches a measurement
	// taken by a sensor at a single moment in time
	private SampleProvider sampleDistance;
	// Initializing array of floats pulled from the sample provider
	private float[] distanceArray;
	// Initializing float for distance
	private float distance;

	public SleepWall() {
		// Instantiating port for the ultrasonic sensor
		Port sensor = LocalEV3.get().getPort("S2");
		// Setting ultrasonic sensor to the port
		sonicSensor = new EV3UltrasonicSensor(sensor);
		// Setting the mode of the ultrasonic sensor to distance, 
		// returning a float of the distance from any object in front
		sampleDistance = sonicSensor.getDistanceMode();
		// Setting the distance array to the size of the sample (should be 1)
		distanceArray = new float[sampleDistance.sampleSize()];
	}

	public boolean takeControl() {
		// Retrieves sample array at this instance of the distance
		sonicSensor.fetchSample(distanceArray, 0);
		// Sets the distance as the first instance of the sample array
		distance = distanceArray[0];
		// takeControl returns true if the distance is less than 0.15 meters (close to an object)
		return distance < 0.15;
	}

	public void action() {
		// Setting suppression boolean to false
		suppressed = false;
		// Blinking red led
		Button.LEDPattern(5);
		System.out.println("Obstacle detected, going to sleep");
		if (!suppressed)
			sleep();
	}

	public void suppress() {
		suppressed = true;
	}

	public void sleep() {
		try {
			// Tells motors to drive stop
			MyEv3Robot.leftMotor.stop(false);
			MyEv3Robot.rightMotor.stop(false);
			// Holds current function (stop) until "woken up"
			Thread.yield();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Holds any error messages on the LCD for 5 seconds if they occur
			Delay.msDelay(5000);
		}
	}
	
	// Since the sensor are initialized in this class, they can be cleaned and closed in this class
	// when exiting the arbitrator; clean and close are made for the CloseAndExit class
	public static void cleanSonic(EV3UltrasonicSensor sensor) throws RemoteException {		
        System.out.println("Cleanup: Closing sonic sensor");
        closeSonic(sensor);
    }

	// Closes sensor if possible
    public static void closeSonic(EV3UltrasonicSensor sensor) throws RemoteException {
        if (sensor != null) {
            sensor.close();
        }
    }
}