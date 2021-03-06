package com.chatto.demo.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

public class ClientInter {
	
	private Socket socket;
	private String username;
	private InetAddress address;
	private int port;
	private InputStream in;
	private OutputStream out;
	private BufferedReader inFromServer;
	private PrintWriter pw;
	private boolean connection;
	
	// 用于传递参数的constructor
	public ClientInter (Socket socket, String username) { 
		this.socket = socket; 	// 传递登录时所用的socket
		this.username = username; 	// 传递登录时的用户名
		address = socket.getInetAddress();
		port = socket.getPort();
		System.out.println("LOL!!! I'm back... The client is still running: ");
		System.out.println("Username: " + username + "\tIP address: " + socket.getInetAddress() + "\tPort: " + socket.getLocalPort() + "\tServer port: " + socket.getPort());
		openResources();
	}
	
	public void setConnection(boolean connection) {
		this.connection = connection;
	}
	
	public void openResources() {
		try {
			out = socket.getOutputStream();
			pw = new PrintWriter(out);
			in = socket.getInputStream();
			inFromServer = new BufferedReader(new InputStreamReader(in));
			System.out.println("Resources have been opened again...");
			connection = true;
		} catch (IOException e) {
			System.out.println("I'm dead. I can only ruin everything.");
			e.printStackTrace();
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	public void sendCloseInfo() {
		pw.write("/q/" + username +"\n");
		pw.flush();
	}
	
	public String getUsersUpdate() {
		String onlineUsers = "";
		try {
			pw.write("/u/" + username + '\n');
			pw.flush();
			onlineUsers = inFromServer.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return onlineUsers;
	}
	
	public void closeClient() {
		sendCloseInfo();
		System.out.println("I've send close information to server in order to tell it to close this socket.");
		try {
			inFromServer.close();
			in.close();
			pw.close();
			out.close();
			socket.close();
			System.out.println("Now I close myself.");
		} catch (IOException e) {
			System.out.println("OMG! Something wrong happened! And I have no idea how to handle it... That's aweful.");
			e.printStackTrace();
		}
	}
	
	
}
