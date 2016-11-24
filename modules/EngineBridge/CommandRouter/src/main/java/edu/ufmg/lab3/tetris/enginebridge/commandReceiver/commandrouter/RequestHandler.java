package edu.ufmg.lab3.tetris.enginebridge.commandReceiver.commandrouter;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Message;

public class RequestHandler implements Runnable {

	private static Log log = LogFactoryUtil.getLog(RequestHandler.class);
	private Socket client;

	public RequestHandler(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		
		try (DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));) {
			while (true) {
				Message msg = new Message();

				msg.setApp(in.readByte());
				msg.setPlayer(in.readByte());
				msg.setMove(in.readByte());
				msg.setUnits(in.readByte());

				log.info("msg from " + client.getRemoteSocketAddress() + ":" + msg);
				
			}

		} catch (IOException e) {
			
		} 
	}

}
