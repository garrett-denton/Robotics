import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;

public class TouchSensorTest {

	static EV3TouchSensor button;
	public static boolean pressed = false;
	
	public static void buttonInput(String port) {
		Port sensor = LocalEV3.get().getPort(port);
		button = new EV3TouchSensor(sensor);
		while(Button.ESCAPE.isUp()) {
		System.out.println(button.getTouchMode());
		}
		/*if (button.getTouchMode().equals(true)) {
			pressed = true;
		}*/
		
	}
	
}
