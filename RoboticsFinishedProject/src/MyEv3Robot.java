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
	// Instantiating behaviors for the arbitrator array
	private static Behavior explore1;
	private static Behavior light2;
	private static Behavior wall3;
	private static Behavior switch4;
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
			explore1 = new Wiggle();
			light2 = new ScaredyBot();
			wall3 = new SleepWall();
			switch4 = new TouchWake();
			close5 = new CloseAndExit();
			// Creating array of behaviors for arbitrator in order of priority, least to greatest
			// so that whatever is in the later position can always suppress lower priority behaviors
			// if its takeControl boolean returns true 
			Behavior[] behaveArray = {explore1, light2, wall3, switch4, close5};
			// Correlating the Arbitrator with the created behavior array
			arb = new Arbitrator(behaveArray);
			// Static green led
			Button.LEDPattern(1);
			// Making sound indicating we are ready
			Sound.beepSequenceUp();
			//Sound.playTone(1000, 2000, 100);
			// Running the infinite loop of the arbitrator
			// Would not be able to be broken if it weren't for my CloseAndExit class
			arb.go();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// Holds any error messages on the LCD for 5 seconds if they occur
			Delay.msDelay(5000);
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