import lejos.hardware.Sound;
import lejos.hardware.motor.Motor;
import lejos.utility.Delay;

public class Darth {

	public static void main(String[] args) {
		
		Motor.A.forward();
		Motor.B.forward();
		Delay.msDelay(3000);
		Motor.A.stop();
		Motor.B.stop();
		
		Delay.msDelay(500);
		
		Sound.setVolume(70);
		
		Sound.playTone(440, 500);
		Sound.playTone(440, 500);
		Sound.playTone(440, 500);
		Sound.playTone(349, 350);
		Sound.playTone(523, 150);
		Sound.playTone(440, 500);
		Sound.playTone(349, 350);
		Sound.playTone(523, 150);
		Sound.playTone(440, 650);
		
		Delay.msDelay(500);
		
		Sound.playTone(659, 500);
		Sound.playTone(659, 500);
		Sound.playTone(659, 500);
		Sound.playTone(698, 350);
		Sound.playTone(523, 150);
		Sound.playTone(415, 500);
		Sound.playTone(349, 350);
		Sound.playTone(523, 150);
		Sound.playTone(440, 650);
		
		Sound.setVolume(0);

	}

}
