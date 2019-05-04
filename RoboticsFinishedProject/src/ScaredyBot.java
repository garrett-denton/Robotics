import lejos.robotics.subsumption.Behavior;
import java.rmi.RemoteException;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.utility.Delay;

public class ScaredyBot implements Behavior {

	// Initializing suppression boolean to false
	private boolean suppressed = false;
	// Instantiating both color sensors
	public static EV3ColorSensor colorSensor1;
	public static EV3ColorSensor colorSensor2;
	// Instantiating sample providers which fetch a measurement
	// taken by the sensors at a single moment in time
	private SampleProvider sampleLightLevel1;
	private SampleProvider sampleLightLevel2;
	// Initializing arrays of floats pulled from the sample providers
	private float[] lightLevelArray1;
	private float[] lightLevelArray2;
	// Initializing floats for light levels
	private float lightLevel1;
	private float lightLevel2;
	// Initializing integers for left and right motor speed
	private int leftMotorSpeed = 900;
	private int rightMotorSpeed = 700;
	private int toneFrequency = 800;
	private int duration = 2000;
	private int toneVolume = 100;

	public ScaredyBot() {
		// Instantiating ports for the color sensors
		Port sensor1 = LocalEV3.get().getPort("S1");
		Port sensor2 = LocalEV3.get().getPort("S4");
		// Setting color sensors to the ports
		colorSensor1 = new EV3ColorSensor(sensor1);
		colorSensor2 = new EV3ColorSensor(sensor2);
		// Setting the mode of the color sensors to ambient,
		// returning a float of the light level
		sampleLightLevel1 = colorSensor1.getAmbientMode();
		sampleLightLevel2 = colorSensor2.getAmbientMode();
		// Setting the light level array to the size of the sample (should be 1)
		lightLevelArray1 = new float[sampleLightLevel1.sampleSize()];
		lightLevelArray2 = new float[sampleLightLevel2.sampleSize()];
	}

	public boolean takeControl() {
		// Retrieves sample arrays at this instance of the ambient light levels
		sampleLightLevel1.fetchSample(lightLevelArray1, 0);
		sampleLightLevel2.fetchSample(lightLevelArray2, 0);
		// Sets the light levels as the first instances of the sample arrays
		lightLevel1 = lightLevelArray1[0];
		lightLevel2 = lightLevelArray2[0];
		// takeControl returns true if either of the light levels are greater than float
		// 0.08
		// (minimum level 0.08 was determined by testing ambient light level in room vs.
		// flashlight)
		return lightLevel1 > 0.08 || lightLevel2 > 0.08;
	}

	public void action() {
		// Setting suppression boolean to false
		suppressed = false;
		if (Button.ENTER.isDown()) {
			leftMotorSpeed = 900;
			rightMotorSpeed = 700;
			toneFrequency = 800;
			duration = 2000;
			toneVolume = 100;
		} else {
			duration -= 200;
			toneVolume -= 15;
			toneFrequency -= 120;
			Sound.setVolume(toneVolume);
			// Blinking yellow led
			Button.LEDPattern(6);
			System.out.println("Light detected, tracking");
		// When action begins, suppression is false until higher priority takes control
		// or both light levels are too low and the arbitrator defaults to the lowest
		// priority behavior
		if (!suppressed) {
			// Retrieves sample arrays at this instance of the ambient light levels
			sampleLightLevel1.fetchSample(lightLevelArray1, 0);
			sampleLightLevel2.fetchSample(lightLevelArray2, 0);
			// Sets the light levels as the first instances of the sample arrays
			lightLevel1 = lightLevelArray1[0];
			lightLevel2 = lightLevelArray2[0];
			drive();
		}
		}
	}

	public void suppress() {
		suppressed = true;
	}

	public void drive() {
		try {
			// Using light levels and tested calculations, setting the left and right
			// motor speed integers to decelerate as light dims
			leftMotorSpeed -= 100;
			rightMotorSpeed -= 100;
			// Setting left and right motor speeds
			MyEv3Robot.leftMotor.setSpeed(leftMotorSpeed);
			MyEv3Robot.rightMotor.setSpeed(rightMotorSpeed);
			// Sending motors backward at set speeds
			MyEv3Robot.leftMotor.backward();
			MyEv3Robot.rightMotor.backward();
			Sound.playTone(toneFrequency, duration);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Holds any error messages on the LCD for 5 seconds if they occur
			Delay.msDelay(5000);
		}
	}

	// Since sensors are initialized in this class, they can be cleaned and closed
	// in this class
	// when exiting the arbitrator; clean and close are made for the CloseAndExit
	// class
	public static void cleanColor(EV3ColorSensor sensor) throws RemoteException {
		System.out.println("Cleanup: Closing color sensor");
		closeColor(sensor);
	}

	// Closes sensor if possible
	public static void closeColor(EV3ColorSensor sensor) throws RemoteException {
		if (sensor != null) {
			sensor.close();
		}
	}
}
