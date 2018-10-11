import lejos.ev3.*;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.remote.ev3.*;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class MyEv3Robot2 {

	
	/*EV3ColorSensor color;
		SampleProvider colors;
	       float[] Color;
	       
	       public static void main(String[] args) {
	             new MyEv3Robot2();
	             leftMotor = remoteEv3.createRegulatedMotor("C", 'L'); 
	 	        rightMotor = remoteEv3.createRegulatedMotor("B", 'L');
	 	        
	 	        resetMotor(leftMotor);
	 	        resetMotor(rightMotor);

	       }

	       public MyEv3Robot2() {
	             Port sensor = LocalEV3.get().getPort("S3");
	             color = new EV3ColorSensor(sensor);
	             colors = color.getAmbientMode();
	             Color = new float[colors.sampleSize()];
	             
	             while(Button.ESCAPE.isUp()) {
	                    colors.fetchSample(Color, 0);
	                    if (Color[0] > 0.1) {
	                    	leftMotor.forward();
	                        rightMotor.forward();
	                    }
	                    
	             }
	             
	       }*/

	}


