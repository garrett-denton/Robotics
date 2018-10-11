import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.utility.Delay;

public class CloseAndExit implements Behavior {

	// Initializing suppression boolean to false
	private boolean suppressed = false;

	public boolean takeControl() {
		// returns true if the escape button is pressed on the EV3 Brick
		return Button.ESCAPE.isDown();
	}

	public void action() {
		// Setting suppression boolean to false
		suppressed = false;
		if (!suppressed) {
			// Static red led
			Button.LEDPattern(2);
			System.out.println("Closing motors and sensors and exiting program");
			closeUp();
			// Keeps closing messages on the lcd for 5 seconds before exiting arbitrator
			Delay.msDelay(5000);
			System.exit(0);
		}
	}

	public void suppress() {
		suppressed = true;
	}

	public void closeUp() {
		try {
			// Calls all of the motors and sensors clean and close functions
			MyEv3Robot.cleanMotor(MyEv3Robot.leftMotor);
			MyEv3Robot.cleanMotor(MyEv3Robot.rightMotor);
			LightTracker.cleanColor(LightTracker.colorSensor1);
			LightTracker.cleanColor(LightTracker.colorSensor2);
			SwitchMode.cleanTouch(SwitchMode.touchSensor);
			NoBumpWall.cleanSonic(NoBumpWall.sonicSensor);
			System.out.println("Closing complete, exiting program");
			// Making sound indicating shutting down program
			Sound.beepSequence();
			// Shutting off led
			Button.LEDPattern(0);
			Delay.msDelay(3000);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Holds any error messages on the LCD for 5 seconds if they occur
			Delay.msDelay(5000);
		}
	}
}