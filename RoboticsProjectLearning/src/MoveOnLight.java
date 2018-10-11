import lejos.hardware.Button;
import lejos.utility.Delay;

public class MoveOnLight {

	public static void moveOnLight() {
		try {
			// initializing left and right motor speed integer values
			int L;
			int R;
			// initializing button press boolean
			boolean nopress = true;
			// initializing sensors
			TouchSensorTest TS = new TouchSensorTest("S3");
			ColorSensorTest CS1 = new ColorSensorTest("S1");
			ColorSensorTest CS2 = new ColorSensorTest("S4");
			SonicSensorTest SS = new SonicSensorTest("S2");

			// if the physical escape button on the EV3 has not been pressed, it will continue to run
			while (Button.ESCAPE.isUp()) {
				// if the touch sensor (tail button) and the physical escape button on the EV3 
				// has not been pressed, it will run this function
				while (nopress == true && Button.ESCAPE.isUp()) {
					// fetching sensor values
					CS1.colors.fetchSample(CS1.Color, 0);
					CS2.colors.fetchSample(CS2.Color, 0);
					TS.pressed.fetchSample(TS.Pressed, 0);
					SS.distance.fetchSample(SS.Distance, 0);
					// if the tail button was pressed, the button boolean is set to false and the
					// first nested (this) while loop breaks, allowing the second while loop to start
					if (TS.Pressed[0] == 1) {
						nopress = false;
					}
					// if the button has not been pressed, check to see if the sonic sensor detects
					// anything less than .15 meters away
					else if (SS.Distance[0] < 0.15) {
						// if something is detected, set the R integer value to 300
						R = (int) (300);
						// set the left and right motor speeds to the R value (300)
						MyEv3Robot3.leftMotor.setSpeed(R);
						MyEv3Robot3.rightMotor.setSpeed(R);
						// run the left and right motors in reverse
						MyEv3Robot3.leftMotor.backward();
						MyEv3Robot3.rightMotor.backward();
						// reverse for 2000 milliseconds and continue with the loop
						Delay.msDelay(2000);
					} 
					// if there is nothing in front of the sonic sensor or after it has reversed
					else {
						// if the light value fron the first or second light sensor is greater than 0.1
						if (CS1.Color[0] > 0.08 || CS2.Color[0] > 0.08) {
							System.out.println("Driving forward!");
							// set the left motor value to 900 minus the first light sensor value times 1700
							L = (int) (900 - CS1.Color[0] * 1700);
							System.out.println(CS1.Color[0]);
							// set the right motor value to 900 minus the second light sensor value times 1700
							R = (int) (900 - CS2.Color[0] * 1700);
							System.out.println(CS2.Color[0]);
							// set the left and right motor speeds to L and R values
							MyEv3Robot3.leftMotor.setSpeed(L);
							MyEv3Robot3.rightMotor.setSpeed(R);
							// send the motors forward
							MyEv3Robot3.leftMotor.forward();
							MyEv3Robot3.rightMotor.forward();
						} 
						//if the light values in the left and right color sensors are less than 0.1
						else {
							System.out.println("i see no light");
							// set the L value to 600 and the R value to 200
							L = (int) (600);
							R = (int) (200);
							// set the left and right motor speeds to L and R
							MyEv3Robot3.leftMotor.setSpeed(L);
							MyEv3Robot3.rightMotor.setSpeed(R);
							// send the motors forward (in a circle)
							// "looking for light"
							MyEv3Robot3.leftMotor.forward();
							MyEv3Robot3.rightMotor.forward();
						}
					}
				}

				while (nopress == false && Button.ESCAPE.isUp()) {
					CS1.colors.fetchSample(CS2.Color, 0);
					CS2.colors.fetchSample(CS1.Color, 0);
					TS.pressed.fetchSample(TS.Pressed, 0);
					if (TS.Pressed[0] == 1) {
						nopress = true;
					} else if (SS.Distance[0] < 0.15) {
						R = (int) (300);
						MyEv3Robot3.leftMotor.setSpeed(R);
						MyEv3Robot3.rightMotor.setSpeed(R);
						MyEv3Robot3.leftMotor.backward();
						MyEv3Robot3.rightMotor.backward();
						Delay.msDelay(2000);
					} else {

						if (CS1.Color[0] > 0.08 || CS2.Color[0] > 0.08) {
							System.out.println("Driving Away!");
							L = (int) (900 - CS2.Color[0] * 1700);
							R = (int) (900 - CS1.Color[0] * 1700);
							MyEv3Robot3.leftMotor.setSpeed(L);
							MyEv3Robot3.rightMotor.setSpeed(R);
							MyEv3Robot3.leftMotor.backward();
							MyEv3Robot3.rightMotor.backward();
						} else {
							System.out.println("i see no light");
							L = (int) (600);
							R = (int) (200);
							MyEv3Robot3.leftMotor.setSpeed(L);
							MyEv3Robot3.rightMotor.setSpeed(R);
							MyEv3Robot3.leftMotor.forward();
							MyEv3Robot3.rightMotor.forward();
						}

					}
				}
			}
			ColorSensorTest.cleancolor(CS1.color);
			ColorSensorTest.cleancolor(CS2.color);
			TouchSensorTest.cleantouch(TS.touch);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			Delay.msDelay(10000);
		}
	}
}
