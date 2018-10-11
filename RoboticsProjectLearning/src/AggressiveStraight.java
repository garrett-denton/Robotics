import java.rmi.RemoteException;

import lejos.hardware.Button;

public class AggressiveStraight {
	
	public static void aggressiveStraight() {
		try {
			ColorSensorTest CS = new ColorSensorTest("S4");
				
			while(Button.ESCAPE.isUp()) {
				CS.colors.fetchSample(CS.Color, 0);
				System.out.println(CS.Color[0]);
				if (CS.Color[0] > 0.09) {
					System.out.println("Driving forward!");
					MyEv3Robot3.leftMotor.forward();
					MyEv3Robot3.rightMotor.forward();
				}
				else {
					System.out.println("i see no light");
					MyEv3Robot3.leftMotor.stop(true);
					MyEv3Robot3.rightMotor.stop(true);
				}
			}
			
			CS.color.close();
			System.out.println("Stopping!");            
			MyEv3Robot3.leftMotor.stop(true);
			MyEv3Robot3.rightMotor.stop(true);
			}
			catch (RemoteException e) {
				e.printStackTrace();
				}
	}
}
