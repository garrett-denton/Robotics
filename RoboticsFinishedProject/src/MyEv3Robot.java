import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.remote.ev3.RemoteEV3;
import lejos.remote.ev3.RMIRegulatedMotor;
import java.rmi.RemoteException;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.utility.Delay;

public class MyEv3Robot {

	// Instantiating remoteEv3
	private static RemoteEV3 remoteEv3;
	// Instanitating left and right motors
	public static RMIRegulatedMotor leftMotor;
	public static RMIRegulatedMotor rightMotor;
	// Initializing boolean to switch between light tracking modes with button press
	public static boolean switchMode = true;
	// Instantiating behaviors for the arbitrator array
	private static Behavior circle1;
	private static Behavior light2;
	private static Behavior switch3;
	private static Behavior wall4;
	private static Behavior close5;
	// Instantiating arbitrator
	private static Arbitrator arb;

	public static void main(String[] args) {
		try {
			// Connecting to EV3 Brick over Bluetooth PAN
			remoteEv3 = new RemoteEV3("10.0.1.1");
			// Setting the connected EV3 as the EV3 for static methods
			remoteEv3.setDefault();
			// Creating left and right regulated motors for the EV3
			leftMotor = remoteEv3.createRegulatedMotor("D", 'L');
			rightMotor = remoteEv3.createRegulatedMotor("A", 'L');
			// Correlating behavior names to their implementing classes
			circle1 = new DriveInCircle();
			light2 = new LightTracker();
			switch3 = new SwitchMode();
			wall4 = new NoBumpWall();
			close5 = new CloseAndExit();
			// Creating array of behaviors for arbitrator in order of priority, least to greatest
			// so that whatever is in the later position can always suppress lower priority behaviors
			// if its takeControl boolean returns true 
			Behavior[] behaveArray = {circle1, light2, switch3, wall4, close5};
			// Correlating the Arbitrator with the created behavior array
			arb = new Arbitrator(behaveArray);
			// Static green led
			Button.LEDPattern(1);
			// Making sound indicating we are ready
			Sound.beepSequenceUp();
			// Running the infinite loop of the arbitrator
			arb.go();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Holds any error messages on the LCD for 5 seconds if they occur
			Delay.msDelay(5000);
		}
	}
	
	// Returns the boolean value of the switch mode for LightTracker class to access
	public static boolean getSwitchMode() {
		return switchMode;
	}

	// Switches switchMode from false to true or vice versa depending on button press in 
	// SwitchMode class
	public static void flipSwitchMode() {
		if (switchMode) {
			switchMode = false;
		} else {
			switchMode = true;
		}
	}
	
	// Since motors are initialized in this class, they can be cleaned and closed in this class
	// when exiting the arbitrator, clean and close are made for the CloseAndExit class
	public static void cleanMotor(RMIRegulatedMotor motor) throws RemoteException {		
        System.out.println("Cleanup: Closing Motors");
        closeMotor(motor);
    }

    public static void closeMotor(RMIRegulatedMotor motor) throws RemoteException {
        if (motor != null) {
            motor.stop(true);
            motor.close();
        }
    }
}