import lejos.hardware.motor.Motor;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.*;

public class Forward {

	public static void main(String[] args) {
		
		Port sensor = LocalEV3.get().getPort("S4");
		EV3ColorSensor color = new EV3ColorSensor(sensor);
	    SampleProvider colors = color.getRGBMode();
	    float[] Color= new float[colors.sampleSize()];

	    colors.fetchSample(Color, 0);
	    System.out.println("Red: " + Color[0]);
	    System.out.println("Green: " + Color[1]);
	    System.out.println("Blue: " + Color[2]);
	    Delay.msDelay(20000);
	    
		/*Motor.A.forward();
		Motor.C.forward();
		Delay.msDelay(3000);
		Motor.C.stop();
		Motor.C.stop();*/
		
	}

}
