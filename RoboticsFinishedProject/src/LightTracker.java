import lejos.robotics.subsumption.Behavior;
import java.rmi.RemoteException;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Button;
import lejos.utility.Delay;

public class LightTracker implements Behavior {

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
	private int leftMotorSpeed;
	private int rightMotorSpeed;
	

	public LightTracker() {
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
		// takeControl returns true if either of the light levels are greater than float 0.08
		// (minimum level 0.08 was determined by testing ambient light level in room vs. flashlight)
		return lightLevel1 > 0.08 || lightLevel2 > 0.08;
	}
	
	public void action() {
		// Setting suppression boolean to false
		suppressed = false;
		// Blinking yellow led
		Button.LEDPattern(6);
		System.out.println("Light detected, tracking");
		// When action begins, suppression is false until higher priority takes control
		// or both light levels are too low and the arbitrator defaults to the lowest priority behavior
		while ((lightLevel1 > 0.08 && lightLevel2 > 0.08) && !suppressed) {
			// Retrieves sample arrays at this instance of the ambient light levels
			sampleLightLevel1.fetchSample(lightLevelArray1, 0);
			sampleLightLevel2.fetchSample(lightLevelArray2, 0);
			// Sets the light levels as the first instances of the sample arrays
			lightLevel1 = lightLevelArray1[0];
			lightLevel2 = lightLevelArray2[0];
			drive();
		}
	}
	
	public void suppress() {
		suppressed = true;
	}
	
	public void drive() {
		try {
			if (MyEv3Robot.getSwitchMode()) {
				// Using light levels and tested calculations, setting the left and right
				// motor speed integers to decelerate as light brightens
				leftMotorSpeed = (int) (960 - (lightLevel1 * 1800));
				rightMotorSpeed = (int) (960 - (lightLevel2 * 1800));
				// Setting left and right motor speeds
				MyEv3Robot.leftMotor.setSpeed(leftMotorSpeed);
				MyEv3Robot.rightMotor.setSpeed(rightMotorSpeed);
				// Sending motors forward at set speeds
				MyEv3Robot.leftMotor.forward();
				MyEv3Robot.rightMotor.forward();
			} else {
				// Using light levels and tested calculations, setting the left and right
				// motor speed integers to decelerate as light dims
				leftMotorSpeed = (int) (lightLevel1 * 2000);
				rightMotorSpeed = (int) (lightLevel2 * 2000);
				// Setting left and right motor speeds
				MyEv3Robot.leftMotor.setSpeed(leftMotorSpeed);
				MyEv3Robot.rightMotor.setSpeed(rightMotorSpeed);
				// Sending motors backward at set speeds
				MyEv3Robot.leftMotor.backward();
				MyEv3Robot.rightMotor.backward();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Holds any error messages on the LCD for 5 seconds if they occur
			Delay.msDelay(5000);
		}
	}
	
	// Since sensors are initialized in this class, they can be cleaned and closed in this class
	// when exiting the arbitrator; clean and close are made for the CloseAndExit class
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