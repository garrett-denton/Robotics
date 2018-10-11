import java.rmi.RemoteException;
import lejos.hardware.Button;
import lejos.utility.Delay;

public class NoBump {
	
	public static void noBump() {
		try {
			SonicSensorTest SS = new SonicSensorTest("S2");
				
			while(Button.ESCAPE.isUp()) {
				SS.distance.fetchSample(SS.Distance, 0);
				System.out.println(SS.Distance[0]);
				Delay.msDelay(5000);
				if (SS.Distance[0] > 0.2 || SS.Distance[0] < 0) {
					MoveOnLight.moveOnLight();
				}
				else {
					break;
				}
			}
			
			System.out.println("Stopping!");            
			MyEv3Robot3.leftMotor.stop(true);
			MyEv3Robot3.rightMotor.stop(true);
            SonicSensorTest.cleansonic(SS.sonic);
			}
			catch (RemoteException e) {
				e.printStackTrace();
				}
	}
}
