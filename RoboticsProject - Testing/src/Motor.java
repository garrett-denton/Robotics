import java.rmi.RemoteException;
import lejos.remote.ev3.RMIRegulatedMotor;

public class Motor {

	public static void cleanup(RMIRegulatedMotor motor) throws RemoteException {		
        System.out.println("Cleanup: Closing Motors");
        closeMotor(motor);
    }

    public static void closeMotor(RMIRegulatedMotor motor) throws RemoteException {
        if (motor != null) {
            motor.stop(true);
            motor.close();
        }
    }
    
    public static void resetMotor(RMIRegulatedMotor motor) throws RemoteException {
        System.out.println("Starting up: Resetting Motors");
        if (motor != null) {
            motor.resetTachoCount();
            motor.rotateTo(0);
            motor.setSpeed(945);
        }
    }
    
}
