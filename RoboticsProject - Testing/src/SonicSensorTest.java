import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

import java.rmi.RemoteException;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;

public class SonicSensorTest {


	EV3UltrasonicSensor sonic;
	SampleProvider distance;
	float[] Distance;
	
	public SonicSensorTest(String port) {
		Port sensor = LocalEV3.get().getPort(port);
		sonic = new EV3UltrasonicSensor(sensor);
		distance = sonic.getDistanceMode();
		Distance = new float[distance.sampleSize()];
	}
	
	public static void cleansonic(EV3UltrasonicSensor sensor) throws RemoteException {		
        System.out.println("Cleanup: Closing sonic sensor");
        closeSonic(sensor);
    }

    public static void closeSonic(EV3UltrasonicSensor sensor) throws RemoteException {
        if (sensor != null) {
            sensor.close();
        }
    }
}

