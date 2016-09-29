package edu.ufmg.lab3.tetris.commandrouter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RequestHandler implements Runnable {
	
	private Socket client;

	public RequestHandler(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		try (DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));) {
			Message msg = new Message();
			
			msg.setApp(in.readByte());
			msg.setPlayer(in.readByte());
			msg.setMove(in.readByte());
			msg.setUnits(in.readByte());
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
