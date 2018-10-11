import lejos.robotics.subsumption.Behavior;
import lejos.hardware.Button;
import lejos.utility.Delay;

public class DriveInCircle implements Behavior {

	// Initializing suppression boolean to false
	private boolean suppressed = false;
	// Initializing left and right motor speed integers
	private int leftSpeed = 200;
	private int rightSpeed = 600;

	public boolean takeControl() {
		// Always returns true so the arbitrator will default to this behavior
		// if there are no higher priority behaviors
		return true;
	}

	public void action() {
		try {
			// Setting suppression boolean to false
			suppressed = false;
			// Blinking green led
			Button.LEDPattern(4);
			System.out.println("Searching for light (Driving in circles)");
			circle();
			// Continues running while driving until suppressed by higher priority behavior
			while (MyEv3Robot.leftMotor.isMoving() && !suppressed)
				Thread.yield();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Holds any error messages on the LCD for 5 seconds if they occur
			Delay.msDelay(5000);
		}
	}

	public void suppress() {
		suppressed = true;
	}

	public void circle() {
		try {
			// Sets the left and right motor speed to drive in a relatively wide circle
			MyEv3Robot.leftMotor.setSpeed(leftSpeed);
			MyEv3Robot.rightMotor.setSpeed(rightSpeed);
			// Tells motors to drive forward
			MyEv3Robot.leftMotor.forward();
			MyEv3Robot.rightMotor.forward();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}