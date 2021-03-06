package edu.ufmg.lab3.tetris.enginebridge.commandReceiver.commandrouter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Connector;
import edu.ufmg.lab3.tetris.enginebridge.connector.baseConnector.Message;

@Component(immediate = true, service = EngineCommander.class)
public class EngineCommander extends Thread implements Connector {

	private static Log log = LogFactoryUtil.getLog(EngineCommander.class);
	private int PORT_NUMBER = 8090;

	private volatile ServerSocket serverSocket;
	private Socket client;
	DataOutputStream clientWriter;
	ScheduledExecutorService scheduler;

	@Override
	public void process(Message msg) {

		try {

			clientWriter.write(msg.getPlayer());
			clientWriter.write(msg.getMove());
			clientWriter.write(msg.getUnits());
			clientWriter.flush(); 

			log.info("-> msg for " + client.getRemoteSocketAddress() + ":" + msg);

		} catch (IOException e) {

		}
	}

	@Activate
	public void activate() {
		setDaemon(true);
		start();
		log.info("EngineCommander loaded");
	}

	@Deactivate
	public void deactive() throws InterruptedException {
		interrupt();
		try {

			serverSocket.close();
			clientWriter.close();
			client.close();

		} catch (Exception e) {
			log.error(e);
		}
		join(5000);
		log.info("EngineCommander stopped");
	}

	@Override
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
			
			clientWriter = new DataOutputStream(client.getOutputStream());
			
			scheduler = Executors.newScheduledThreadPool(1);    
			scheduler.scheduleAtFixedRate(()->{process(Message.NULL_MESSAGE);} ,0, 300, TimeUnit.MILLISECONDS);
			
		} catch (Exception e) {
		}
	}
	

}
