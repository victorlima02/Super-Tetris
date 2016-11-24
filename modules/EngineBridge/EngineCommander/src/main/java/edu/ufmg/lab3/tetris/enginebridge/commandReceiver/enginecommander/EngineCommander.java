package edu.ufmg.lab3.tetris.enginebridge.commandReceiver.enginecommander;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;
import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Message;

@Component(immediate = true, service = Object.class)
public class EngineCommander extends Thread implements Connector {

	private static Log log = LogFactoryUtil.getLog(EngineCommander.class);
	private int PORT_NUMBER = 8090;

	private volatile ServerSocket serverSocket;
	private Socket client;
	BufferedWriter clientWriter;

	@Override
	public void process(Message msg) {

		try {

			clientWriter.write(msg.getPlayer());
			clientWriter.write(msg.getMove());
			clientWriter.write(msg.getUnits());

			log.info("-> msg for " + client.getRemoteSocketAddress() + ":" + msg);

		} catch (IOException e) {

		}
	}

	@Activate
	public void activate() {
		setDaemon(true);
		run();
		log.info("CommandRouter loaded");
	}

	@Deactivate
	public void deactive() throws InterruptedException {
		interrupt();
		try {

			serverSocket.close();
			client.close();

		} catch (Exception e) {
			log.error(e);
		}
		join(5000);
		log.info("CommandRouter stopped");
	}

	public void run() {
		while (!isInterrupted())
			try {

				doServer();

			} catch (Exception e) {
				log.error(e);
				if (!isInterrupted())
					try {
						Thread.sleep(5000);
					} catch (Exception ex) {
					}
			}
	}

	private void doServer() throws Exception {
		serverSocket = new ServerSocket(PORT_NUMBER);
		try {
			while (!isInterrupted())
				doConnection(serverSocket);
		} finally {
			serverSocket.close();
		}
	}

	private void doConnection(ServerSocket server) {
		try {
			client = serverSocket.accept();
			clientWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			log.info("Tetris engine connected: " + client.getRemoteSocketAddress());

		} catch (Exception e) {
			log.info("client disconnected: " + client.getRemoteSocketAddress());
		}
	}

}
