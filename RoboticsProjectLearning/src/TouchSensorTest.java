import java.rmi.RemoteException;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;

public class TouchSensorTest {

	EV3TouchSensor touch;
	SampleProvider pressed;
	float[] Pressed;
	
	public TouchSensorTest(String port) {
		Port sensor = LocalEV3.get().getPort(port);
		touch = new EV3TouchSensor(sensor);
		pressed = touch.getTouchMode();
		Pressed = new float[pressed.sampleSize()];
	}
	
	public static void cleantouch(EV3TouchSensor sensor) throws RemoteException {		
        System.out.println("Cleanup: Closing touch sensor");
        closeTouch(sensor);
    }

    public static void closeTouch(EV3TouchSensor sensor) throws RemoteException {
        if (sensor != null) {
            sensor.close();
        }
    }
	
}
