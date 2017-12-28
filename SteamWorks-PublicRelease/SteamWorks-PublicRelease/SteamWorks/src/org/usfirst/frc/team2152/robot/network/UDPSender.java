package org.usfirst.frc.team2152.robot.network;

import java.io.*;
import java.net.*;

public class UDPSender {
    DatagramSocket send;
    
    public UDPSender() {
    	try {
			send = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
    }

    public void sendMsg(String msg, InetAddress addr, int port) {
        sendMsg(msg.getBytes(), addr, port);
    }

    public void sendMsg(byte[] sendData, InetAddress addr, int port) {
//    	System.out.println("Sending msg");
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, addr, port);
        try {
            send.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
