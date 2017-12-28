package org.usfirst.frc.team2152.robot.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.usfirst.frc.team2152.robot.Robot;


public class UDPReceiver extends Thread {

	public static final int UDP_PORT = 5800;

	private final static int RECIEVE_TIMEOUT = 1000; // in ms
	private final static String threadName = "UDPReceiver";

	private int listenPort;
	private DatagramSocket socket;
	private static final int bufferSize = 64;

	private UDPListener listener;

	private Object lock = new Object();

	
	public UDPReceiver(int port) {
		super(threadName);
		this.listenPort = port;
		
		try {
			socket = new DatagramSocket(listenPort);
			
			// create timeout in case there is a dead connection
			socket.setSoTimeout(RECIEVE_TIMEOUT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		byte[] buffer = new byte[bufferSize];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		// Don't start listening for data until a listener is added
		synchronized(lock) {
			if(listener == null) {
				System.err.println("UDPReceiver has no listener; waiting until one is added");
				try {
					lock.wait(); // Blocks until lock.notify() is called on another thread
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		while(!Thread.interrupted()) {
			clear(buffer);
			try {
				socket.receive(packet);
			} catch (SocketTimeoutException e) {
				// Continue receive loop to ignore buffer data
				continue;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Notify listener of new data
			listener.packetReceived(buffer);
			Robot.steamworksDashboard.putUDPInterrupted(false);
		}
		cleanUp();
		Robot.steamworksDashboard.putUDPInterrupted(true);
	}

	private void cleanUp() {
		if(socket != null) {
			socket.close();
			socket = null;
		}
	}

	public void setListener(UDPListener listener) {
		if(listener != null) {
			this.listener = listener;
			synchronized(lock) {
				lock.notify();
			}
		} else {
			System.err.println("Attempted to add null listener to UDP Receiver");
		}
	}

	public boolean isRunning() {
		return !isInterrupted();
	}

	// Disables running flag
	public void end() {
		waitToClose();
		interrupt();
	}
	
	// Waits for the thread to stop for safety
	public void waitToClose() {
		try {
			// Join with the thread running this method
			join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// Clear specified buffer bytes
	private void clear(byte[] bs) {
		for(int i = 0; i < bs.length; i++) {
			bs[i] = 0;
		}
	}
}
