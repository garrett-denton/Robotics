import java.rmi.RemoteException;
import lejos.hardware.Button;

public class TailButton {
	
	public static void tailButton() {
		try {
			boolean nopress = true;
		
			TouchSensorTest TS = new TouchSensorTest("S3");
				
			while(Button.ESCAPE.isUp()) {
				TS.pressed.fetchSample(TS.Pressed, 0);
				System.out.println(TS.Pressed[0]);
				if (TS.Pressed[0] == 1) {
					nopress = false;
				}
					
				if (nopress) {
					System.out.println("Driving forward!");
					MyEv3Robot3.leftMotor.forward();
					MyEv3Robot3.rightMotor.forward();
				}
				else {
					System.out.println("Stopping!");
					MyEv3Robot3.leftMotor.stop(true);
					MyEv3Robot3.rightMotor.stop(true);
				}		
			}
			
			System.out.println("Stopping!");
			MyEv3Robot3.leftMotor.stop(true);
			MyEv3Robot3.rightMotor.stop(true);
			}
			catch (RemoteException e) {
				e.printStackTrace();
				}
	}
}