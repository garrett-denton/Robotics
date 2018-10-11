import java.rmi.RemoteException;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;

public class MyEv3Robot {
	private static RemoteEV3 remoteEv3;
	public static RMIRegulatedMotor leftMotor;
    public static RMIRegulatedMotor rightMotor;

    
    public static boolean isRunning = true;
	
	public static void main(String[] args) {		
		try
		{	        
			remoteEv3 = new RemoteEV3("10.0.1.1");
			remoteEv3.setDefault();
			
			String name = remoteEv3.getName();
			System.out.println("The name of my device is " + name);
			
			//Sound.beep();		
			
			//colorSensor = new EV3ColorSensor(remoteEv3.getPort("S4"));
			// TODO: Change to correct port on your robots
	        leftMotor = remoteEv3.createRegulatedMotor("A", 'L'); 
	        rightMotor = remoteEv3.createRegulatedMotor("C", 'L');
	        
	        resetMotor(leftMotor);
	        resetMotor(rightMotor);
	        
	        Button.LEDPattern(3); // flash a green led
	        Sound.beepSequenceUp(); // make sound indicating we are ready
	        
	        TouchSensorTest.buttonInput("S3");	        
            
            Delay.msDelay(10000); // Wait for 10 seconds
            
            System.out.println("Stopping!");            
            leftMotor.stop(true);
            rightMotor.stop(true);
            
            cleanup();			
		}	
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	
	public static void cleanup() throws RemoteException {		
     
        System.out.println("Cleanup: Closing Motors");
        closeMotor(leftMotor);
        closeMotor(rightMotor);
    }

    private static void closeMotor(RMIRegulatedMotor motor) throws RemoteException {
        if (motor != null) {
            motor.stop(true);
            motor.close();
        }
    }
    
    private static void resetMotor(RMIRegulatedMotor motor) throws RemoteException {
        System.out.println("Starting up: Resetting Motors");
        if (motor != null) {
            motor.resetTachoCount();
            motor.rotateTo(0);
            motor.setSpeed(945);
        }
    }	
}

