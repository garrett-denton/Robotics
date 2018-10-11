import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;

import java.rmi.RemoteException;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;

public class ColorSensorTest {
	
	EV3ColorSensor color;
	SampleProvider colors;
	float[] Color;
	
	public ColorSensorTest(String port) {
		Port sensor = LocalEV3.get().getPort(port);
        color = new EV3ColorSensor(sensor);
        colors = color.getAmbientMode();
        Color = new float[colors.sampleSize()];
	}
	
	public static void cleancolor(EV3ColorSensor sensor) throws RemoteException {		
        System.out.println("Cleanup: Closing color sensor");
        closeColor(sensor);
    }

    public static void closeColor(EV3ColorSensor sensor) throws RemoteException {
        if (sensor != null) {
            sensor.close();
        }
    }
}
