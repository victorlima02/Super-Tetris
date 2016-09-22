package edu.ufmg.lab3.tetris.commandrouter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RequestHandler implements Runnable{

	private Socket client;
	
	public RequestHandler(Socket client) {
		this.client=client;
	}

	@Override
	public void run() {
		try(BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));){
			
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
