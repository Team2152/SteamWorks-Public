package org.usfirst.frc.team2152.robot.network;

@FunctionalInterface
public interface UDPListener {
	public void packetReceived(byte[] data);
}
