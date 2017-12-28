package org.usfirst.frc.team2152.robot.network;

public class UDPExample {

	public static void main(String[] args) {
		// Receiver handles all networking
		UDPReceiver receiver = new UDPReceiver(6000); // illegal port

		// Handler listens for messages from receiver, parses data, and stores it
		// This will be used to retrieve data
		UDPHandler handler = new UDPHandler();
		receiver.setListener(handler);
		receiver.start();
		double x;
		
		try {
			for(int i = 0; i < 10; i++) {
				// Retrieve received/stored data. See UDPHandler.java for more details
				x = handler.getValue(Vars.Peg.Double.Distance);
//				System.out.println(x);
				Thread.sleep(1*1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		receiver.end();
	}
}
