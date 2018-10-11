import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.utility.Delay;

public class MyEv3Robot3 {
	private static RemoteEV3 remoteEv3;
	public static RMIRegulatedMotor leftMotor;
    public static RMIRegulatedMotor rightMotor;
	
	public static void main(String[] args) {		
		try
		{	        
			remoteEv3 = new RemoteEV3("10.0.1.1");
			
			String name = remoteEv3.getName();
			System.out.println("The name of my device is " + name);
			
	        leftMotor = remoteEv3.createRegulatedMotor("C", 'L'); 
	        rightMotor = remoteEv3.createRegulatedMotor("A", 'L');
	        
	        Motor.resetMotor(leftMotor);
	        Motor.resetMotor(rightMotor);
	        
	        Button.LEDPattern(3); // solid orange led
	        Sound.beepSequenceUp(); // make sound indicating we are ready
	        
	        MoveOnLight.moveOnLight();
	        
	        //TailButton.tailButton();
	        
	        //NoBump.noBump();
	        
	        //CowardlyStraight.cowardlyStraight();
	        
	        //AggressiveStraight.aggressiveStraight();
            
            Delay.msDelay(5000); // Wait for 5 seconds
            
            Motor.cleanup(leftMotor);
            Motor.cleanup(rightMotor);
            
		}	
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}	
}