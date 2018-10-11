import java.rmi.RemoteException;
import lejos.hardware.Button;

public class CowardlyStraight {
	
	public static void cowardlyStraight() {
		try {
			ColorSensorTest CS = new ColorSensorTest("S4");
				
			while(Button.ESCAPE.isUp()) {
				CS.colors.fetchSample(CS.Color, 0);
				System.out.println(CS.Color[0]);
				if (CS.Color[0] > 0.09) {
					System.out.println("Driving forward!");
					MyEv3Robot3.leftMotor.backward();
					MyEv3Robot3.rightMotor.backward();
				}
				else {
					System.out.println("i see no light");
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
