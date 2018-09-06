import lejos.ev3.*;
import lejos.remote.ev3.*;

public class MyEv3Robot {

	public static void main(String[] args) {
		
		try
		{
		
		RemoteEV3 remoteDevice = new RemoteEV3("10.0.1.1");
		String name = remoteDevice.getName();
		
		System.out.println(name);
		
		}

		catch(Exception e)
		{
			
		}
	}

}
